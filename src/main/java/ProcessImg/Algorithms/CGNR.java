package ProcessImg.Algorithms;

import org.jblas.DoubleMatrix;

import java.math.BigDecimal;

public class CGNR extends CGALG {

    public static int algorithm(DoubleMatrix H, DoubleMatrix g, DoubleMatrix result){
        int numberIteractions = 0;
        System.out.println("Executing CGNR!");
        DoubleMatrix f = DoubleMatrix.zeros(H.columns);
        DoubleMatrix r = g.sub(H.mmul(f));

        DoubleMatrix z = H.transpose().mmul(r);
        DoubleMatrix p = z;

        DoubleMatrix w;
        Double z_squared;
        Double w_squared;
        Double alpha;
        DoubleMatrix newF;
        DoubleMatrix newR;
        DoubleMatrix newZ;
        Double newZ_squared;
        Double beta;
        DoubleMatrix newP;
        double error;
        do{
            w = H.mmul(p);
            z_squared = z.norm2() * z.norm2();
            w_squared = w.norm2() * w.norm2();

            alpha = z_squared / w_squared;

            newF = f.add(p.mul(alpha));
            newR = r.sub(w.mul(alpha));

            newZ = H.transpose().mmul(newR);

            newZ_squared = newZ.norm2() * newZ.norm2();

            beta = newZ_squared / z_squared;

            newP = newZ.add(p.mul(beta));

            error = error(newR, r);

            // Valor absoluto do erro
            if(error < 0){
                error *= -1;
            }

            f = newF;
            r = newR;
            z = newZ;
            p = newP;
            numberIteractions++;
        }while (error > 1e-4);

        result.copy(f);
        System.out.println("CGNR Finalized!");
        return numberIteractions;
    }

}
