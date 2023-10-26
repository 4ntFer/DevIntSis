package utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Data {
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


        return vector;
    }
}
