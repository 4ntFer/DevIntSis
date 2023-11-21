package Server;

import ProcessImg.Algorithms.CGNE;
import ProcessImg.Algorithms.CGNR;
import ProcessImg.Image;
import com.opencsv.exceptions.CsvException;
import org.jblas.DoubleMatrix;

import java.io.IOException;

import static utils.Data.getMatrixData;
import static utils.Data.getVectorData;

public class main {
    public static void main(String args[]){
        try {
            DoubleMatrix H = new DoubleMatrix(getMatrixData("src/Server.main/res/test1/H-1.csv"));
            DoubleMatrix g = new DoubleMatrix(getVectorData("src/Server.main/res/test1/G-1.csv"));
            DoubleMatrix f;

            f = CGNE.getImage(H,g);

            Image img = new Image(f);
            img.saveImage();

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }
}
