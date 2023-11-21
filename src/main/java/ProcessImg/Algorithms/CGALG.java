package ProcessImg.Algorithms;

import org.jblas.DoubleMatrix;

import java.math.BigDecimal;

public class CGALG {
    /**
     * Fornece o erro entre as duas matrizes.
     * @param newR
     * @param r
     * @return
     */
    protected static double error(DoubleMatrix newR, DoubleMatrix r){
        BigDecimal newR_norm = new BigDecimal(newR.norm2());
        BigDecimal R_norm = new BigDecimal(r.norm2());
        double error = newR_norm.subtract(R_norm).doubleValue();

        //System.out.println("Error: " + newR_norm.doubleValue() + " - " + R_norm.doubleValue() + " = " + error);
        return error;
    }
}
