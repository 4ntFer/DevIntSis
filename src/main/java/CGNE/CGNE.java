package CGNE;

import org.jblas.DoubleMatrix;
import org.jblas.FloatMatrix;

public class CGNE {

    //H é a matriz modelo e g é o vetor de sinal
    public static DoubleMatrix getImage(DoubleMatrix H, DoubleMatrix g){
        System.out.println("H is " + H.rows + " x " + H.columns + " and g is " + g.columns);
        DoubleMatrix f = DoubleMatrix.zeros(H.columns);
        System.out.println("Multiply H "+ H.rows +" x "+ H.columns +" and f " + f.rows +" x "+ f.columns );
        DoubleMatrix r = g.sub(H.mmul(f));
        System.out.println("Multiply H "+ H.rows +" x "+ H.columns +" and r " + r.rows +" x "+ r.columns );
        DoubleMatrix z = H.transpose().mmul(r);

        DoubleMatrix p = z;

        for(int i = 0 ; i <= 2 ; i++){
            DoubleMatrix alpha = r.transpose().mmul(r).div(
              p.transpose().mmul(p)
            );

            DoubleMatrix newF = f.add(alpha.mmul(p));

            DoubleMatrix newR =  r.sub(alpha.mmul(H.mmul(p)));

            DoubleMatrix beta = newR.transpose().mmul(newR).div(
                    r.transpose().mmul(r)
            );

            DoubleMatrix newP = H.transpose().mmul(newR).add(beta.mmul(p));

            f = newF;
            r = newR;
            p = newP;
        }

        System.out.println("final f is " + f.rows +" x "+ f.columns );

        f.reshape(30, 30);

        return f;
    }
}
