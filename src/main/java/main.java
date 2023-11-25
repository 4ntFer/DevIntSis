
import ProcessImg.Algorithms.CGNE;
import ProcessImg.Image;
import org.jblas.DoubleMatrix;

import java.io.IOException;

import static utils.ServerResouces.getMatrixData;
import static utils.ServerResouces.getVectorData;

public class main {
    public static void oldmain(String args[]){
        try {
            DoubleMatrix H = new DoubleMatrix(getMatrixData("src/main/res/test1/H2.csv"));
            DoubleMatrix g = new DoubleMatrix(getVectorData("src/main/res/test1/g-30x30-1.csv"));
            DoubleMatrix f = new DoubleMatrix();
            Integer it = 0;
            System.out.println("CGNE is running!");
            CGNE.algorithm(H, g, f);
            System.out.println("CGNE finalized!");

            Image img = new Image(f);
            img.saveImage();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
