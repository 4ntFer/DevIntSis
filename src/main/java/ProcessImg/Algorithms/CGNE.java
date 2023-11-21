package ProcessImg.Algorithms;

import org.jblas.DoubleMatrix;

public class CGNE extends CGALG {

    //H é a matriz modelo e g é o vetor de sinal
    public static DoubleMatrix getImage(DoubleMatrix H, DoubleMatrix g){

        System.out.println("Executing CGNE!");

        System.out.println("H is " + H.rows + " x " + H.columns + " and g is " + g.columns);
        DoubleMatrix f = DoubleMatrix.zeros(H.columns);
        DoubleMatrix r = g.sub(H.mmul(f));
        DoubleMatrix z = H.transpose().mmul(r);

        DoubleMatrix p = z;

        DoubleMatrix alpha;
        DoubleMatrix newF;
        DoubleMatrix newR;
        DoubleMatrix beta;
        DoubleMatrix newP;
        double error;
        do{
            alpha = r.transpose().mmul(r).div(
              p.transpose().mmul(p)
            );

            newF = f.add(alpha.mmul(p));

            newR =  r.sub(alpha.mmul(H.mmul(p)));

            beta = newR.transpose().mmul(newR).div(
                    r.transpose().mmul(r)
            );

            newP = H.transpose().mmul(newR).add(beta.mmul(p));

            error = error(newR, r);

            f = newF;
            r = newR;
            p = newP;
        }while (error > 1e-4 );

        System.out.println("final f is " + f.rows +" x "+ f.columns );

        f.reshape(60, 60);


        System.out.println("CGNE Finalized!");
        return f;
    }
}
