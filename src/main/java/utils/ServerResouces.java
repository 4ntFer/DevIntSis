package utils;

import java.io.*;
import java.util.ArrayList;

public class ServerResouces {
    public static double[][] getMatrixData(String path) throws IOException {
        /*Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVReader csvReader = new CSVReaderBuilder(reader).build();

        Scanner scanner = new Scanner(new File(path));

        List<String[]> datas = csvReader.readAll();

        double[][] matrix = new double[datas.size()][datas.get(0).length];


        for(int i = 0 ; i < datas.size() ; i++){
            for(int j = 0 ; j < datas.get(i).length ; j++){
                matrix[i][j] = Double.valueOf(datas.get(i)[j]);
            }
        }
        return matrix;*/

        FileReader fr = null;
        BufferedReader br = null;
        ArrayList<String> list;
        double[][] matrix;


            fr = new FileReader(new File(path));
            br = new BufferedReader(fr);

            String line = br.readLine();

            list = new ArrayList<String>();
            while (line != null){
                list.add(line);
                line = br.readLine();
            }

            matrix = new double[list.size()][list.get(0).split(",").length];

            for (int i = 0; i < matrix.length; i++) {

                String[] lineList = list.get(i).split(",");
                for (int j = 0; j < matrix[0].length; j++) {
                    matrix[i][j] = Double.valueOf(lineList[j]);
                }
            }


        /*for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + ",");
            }
            System.out.println("");
        }*/
        if(br != null){
            br.close();
        }

        if(fr!=null){
            fr.close();
        }

        return matrix;
    }

    public static double[] getVectorData(String path) throws IOException {
        FileReader fr = null;
        BufferedReader br = null;
        ArrayList<String> list;
        double[][] matrix;


        fr = new FileReader(new File(path));
        br = new BufferedReader(fr);

        String line = br.readLine();

        list = new ArrayList<String>();
        while (line != null){
            list.add(line);
            line = br.readLine();
        }

        double[] vector = new double[list.size()];

        for (int i = 0 ; i < list.size() ; i++){
            vector[i] = Double.valueOf(list.get(i));
        }

        if(br != null){
            br.close();
        }

        if(fr!=null){
            fr.close();
        }

        return vector;
    }
}
