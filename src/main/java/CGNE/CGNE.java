package CGNE;

import org.jblas.DoubleMatrix;
import org.jblas.FloatMatrix;

public class CGNE {

    //H é a matriz modelo e g é o vetor de sinal
    public static DoubleMatrix getImage(DoubleMatrix H, DoubleMatrix g){
        DoubleMatrix f = DoubleMatrix.zeros(H.columns);
        DoubleMatrix r = g.sub(H.mul(f));
        DoubleMatrix z = H.transpose().mul(r);

        DoubleMatrix p = z;

        for(int i = 0 ; i <= 4 ; i++){
            DoubleMatrix alpha = r.transpose().mul(r).div(
              p.transpose().mul(p)
            );

            DoubleMatrix newF = f.add(alpha.mul(p));

            DoubleMatrix newR =  r.sub(alpha.mul(H.mul(p)));

            DoubleMatrix beta = newR.transpose().mul(newR).div(
                    r.transpose().mul(r)
            );

            DoubleMatrix newP = H.transpose().mul(newR).add(beta.mul(p));

            f = newF;
            r = newR;
            p = newP;
        }



        return f;
    }
}
