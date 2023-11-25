package Server;

import com.sun.management.OperatingSystemMXBean;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.ServerSocket;

public class main {
    public static void main(String args[]){
        ServerSocket serverSocket;
        OperatingSystemMXBean osMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        try {
            serverSocket = new ServerSocket(4242);

            while(true){
                //TODO: Gerenciamento de recursos da máquina

                new HandlerClient(serverSocket.accept(), osMXBean);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*try {
            DoubleMatrix H = new DoubleMatrix(getMatrixData("src/Server.main/res/test1/H1.csv"));
            DoubleMatrix g = new DoubleMatrix(getVectorData("src/Server.main/res/test1/G-1.csv"));
            DoubleMatrix f;

            f = CGNE.getImage(H,g);

            Image img = new Image(f);
            img.saveImage();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }*/
    }
}
