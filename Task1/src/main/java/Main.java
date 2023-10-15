import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import org.jblas.DoubleMatrix;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String argv[]){
        DoubleMatrix M, N, a;

        DoubleMatrix result;

        try {
            M = new DoubleMatrix(getMatrixData("src/res/M.csv"));
            N = new DoubleMatrix(getMatrixData("src/res/N.csv"));
            a = new DoubleMatrix(getVectorData("src/res/a.csv"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }

        System.out.println("N is " + N.columns + "x" + N.rows);
        System.out.println("M is " + M.columns + "x" + M.rows);
        System.out.println("a is " + a.length);

        result = M.mmul(Nawf);

        System.out.println("MN = \n");

        for(int i = 0 ; i < result.rows ; i++){
            for(int j = 0 ; j < result.columns ;  j++){
                System.out.print("" + result.get(i,j) + " ");
            }
            System.out.println("");
        }

    }

    public static double[][] getMatrixData(String path) throws IOException, CsvException {
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVReader csvReader = new CSVReaderBuilder(reader).build();

        List<String[]> datas = csvReader.readAll();

        double[][] matrix = new double[datas.size()][datas.get(0).length];


        for(int i = 0 ; i < datas.size() ; i++){
            for(int j = 0 ; j < datas.get(i).length ; j++){
                matrix[i][j] = Double.valueOf(datas.get(i)[j]);
            }
        }
        return matrix;
    }

    public static double[] getVectorData(String path) throws IOException, CsvException {
        Reader reader = Files.newBufferedReader(Paths.get(path));
        CSVReader csvReader = new CSVReaderBuilder(reader).build();

        List<String[]> datas = csvReader.readAll();

        double[] vector = new double[datas.size()];

        for (int i = 0 ; i < datas.size() ; i++){
            vector[i] = Double.valueOf(datas.get(0)[i]);
        }

        System.out.println(vector.length);

        return vector;
    }
}
