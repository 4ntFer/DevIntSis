import CGNE.CGNE;
import com.opencsv.exceptions.CsvException;
import org.jblas.DoubleMatrix;

import java.io.IOException;

import static utils.Data.getMatrixData;
import static utils.Data.getVectorData;

public class main {
    public static void main(String args[]){
        try {
            DoubleMatrix H = new DoubleMatrix(getMatrixData("src/main/res/test1/H-1.csv"));
            DoubleMatrix g = new DoubleMatrix(getVectorData("src/main/res/test1/g-30x30-1.csv"));

            System.out.println("CGNE is running!");
            CGNE.getImage(H,g);
            System.out.println("CGNE finalized!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
