package Server;

import ProcessImg.Algorithms.CGNE;
import ProcessImg.Algorithms.CGNR;
import ProcessImg.Image;
import Server.Integration.HttpRequest;
import Server.Integration.ImageProcessRequest;
import Server.Integration.ServerResponse;
import Server.MachineMonitor.MachineResoucesMonitor;
import Server.MachineMonitor.MonitorStillActiveExcpetion;
import com.google.gson.Gson;
import com.sun.management.OperatingSystemMXBean;
import org.jblas.DoubleMatrix;
import utils.ServerResouces;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HandlerClient extends Thread{
    private Socket clientSocket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private OperatingSystemMXBean OSMXbean;

    /**
     * @param clientSocket Socket do cliente.
     * @param operatingSystemMXBean Monitor de recursos do sistema.
     * @throws IOException
     */
    public HandlerClient(Socket clientSocket, OperatingSystemMXBean operatingSystemMXBean) throws IOException {
        this.clientSocket = clientSocket;
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = clientSocket.getOutputStream();
        OSMXbean = operatingSystemMXBean;
        System.out.println("New connection accepted.");

        start();
    }

    @Override
    public void run(){
        HttpRequest request = null;
        try {
            request = readRequest();

            //TODO: Responder requisições invalidas com 404

            if(request != null){

                //System.out.println(new String(request.getBody()));
                String json = new String(request.getBody());
                byte[] image;
                if(!json.equals("")) {
                    ImageProcessRequest solicitation =
                            new Gson().fromJson(
                                    json,
                                    ImageProcessRequest.class
                            );

                    /**
                     * Processando a imagem
                     */
                    getResponse(solicitation);

                    //TODO: bloqueio caso maquina não tenha recurso disponiveis para o processamento
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private ServerResponse getResponse(ImageProcessRequest solicitation){

        /**
         * Atributos para a incialização do model da resposta.
         */
        String user = solicitation.getUser();
        String algorithm = solicitation.getAlgorithm();
        String pixels = solicitation.getDimensions();
        String starts;
        String ends;
        double averageCpuUsage;
        double averageMemoryUsage;
        byte[] image;

        ServerResponse response = new ServerResponse();

        /**
         * Demais variaveis.
         */
        MachineResoucesMonitor machineResoucesMonitor = new MachineResoucesMonitor(OSMXbean);

        try {
            String[] aux = LocalDateTime.now().toString().split("T");
            starts = aux[0] + " " + aux[1];
            machineResoucesMonitor.start();
            image = getImage(solicitation, response);
            machineResoucesMonitor.stopRunning();
            aux = LocalDateTime.now().toString().split("T");
            ends = aux[0] + " " + aux[1];

            averageCpuUsage = machineResoucesMonitor.getAvarageCpuUsage();
            averageMemoryUsage = machineResoucesMonitor.getAvarageMemoryUsage();

            System.out.println("Starts: " + starts + ", ends: "+ ends);
            System.out.println("Avarage CPU usage: " + averageCpuUsage*100 +
                    ", Avarage Memory Usage: " + averageMemoryUsage*100);
            System.out.println("Iteractions number: " + response.getIteractions());

            response.setStarts(starts);
            response.setEnds(ends);
            response.setAverageMemoryUsage(averageMemoryUsage);
            response.setAverageCpuUsage(averageCpuUsage);
            response.setImage(image);

            return response;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (MonitorStillActiveExcpetion e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @param solicitation Solicitação de processamento de imagem enviada pelo cliente
     * @param response A resposta que deverá ser retornada ao servidor (Irá preencher o campo de numero de iterações)
     * @return
     */
    private byte[] getImage(ImageProcessRequest solicitation, ServerResponse response) throws IOException {
        String matrixPath = "res/MatrixesModels/" + solicitation.getMatrixModel() + ".csv";

        double[][] matrix = ServerResouces.getMatrixData(matrixPath);

        DoubleMatrix H = new DoubleMatrix(matrix);
        DoubleMatrix g = new DoubleMatrix(solicitation.getSignal());

        DoubleMatrix result = new DoubleMatrix();

        int dimensionX = Integer.valueOf(solicitation.getDimensions().split("x")[0]);
        int dimensionY = Integer.valueOf(solicitation.getDimensions().split("x")[1]);

        int iteractions = 0;

        Image image;

        //TODO: GAIN!

        switch (solicitation.getAlgorithm()){
            case "CGNE":
                iteractions =  CGNE.algorithm(H,g, result);
                break;

            case "CGNR":
                iteractions = CGNR.algorithm(H, g, result);
                break;
        }

        result = result.reshape(dimensionX, dimensionY);

        image = new Image(result);
        image.saveImage();

        response.setIteractions(iteractions);

        return image.toByteArray();
    }

    /**
     * @return Requisição. null caso a mensagem http seja vazia.
     * @throws IOException
     */
    private HttpRequest readRequest() throws IOException {
        /**
         * Lendo mensagem HTTP.
         */

        StringBuilder result = new StringBuilder();

        do {
            result.append((char) inputStream.read());
        } while (inputStream.available() > 0);

        //System.out.println(result.toString());
        return parseData(new ByteArrayInputStream(
                result.toString().getBytes(StandardCharsets.UTF_8)
        ));
    }

    /**
     * Desserealiza a mensasagem HTTP.
     *
     * Body só pode conter um json! (REVISITAR E CORRIGIR)
     *
     * @param data um ByteArrayInputStream iniciado com os bytes
     *             da string da mensagem.
     * @return Retorna a mensagem contida no data
     *         encapsualda em um objeto HttpRequest.
     * @throws IOException
     */
    private HttpRequest parseData(InputStream data) {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(data)
        );

        /**
         * Lendo Headers.
         *
         * No momento que encontra \n, para para ler o body.
         */

        try {
            String firstLine = null;
            firstLine = bufferedReader.readLine();

            String method = ""; // Será sempre GET
            String url = ""; // Sempre um PNG

            if(firstLine != null){
                String[] lineValues = firstLine.split("\\s+");

                method = lineValues[0];
                if(lineValues.length > 1){
                    url = lineValues[1];
                }


                // Coletando cabeçalhos
                Map<String, String> headers = new HashMap<String,String>();
                String headerLine;
                boolean foundBody = false;
                while((headerLine = bufferedReader.readLine()) != null && !foundBody){

                    if(!headerLine.trim().isEmpty()){
                        lineValues = headerLine.split(":\\s");

                        if(lineValues.length >= 2) {
                            String key = lineValues[0];
                            String value = lineValues[1];

                            headers.put(key, value);
                        }
                    }else{
                        //Pula uma linha
                        foundBody = true;
                    }

                }

                /**
                 * Lendo body.
                 */

                String body =  "{";
                String bodyLine = "";
                while((bodyLine = bufferedReader.readLine()) != null){

                    if(!bodyLine.trim().isEmpty())
                        body = body + bodyLine;

                }

                if(body == "{"){
                    body = "";
                }

                return  new HttpRequest(method, url, headers, body);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
