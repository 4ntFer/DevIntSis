package ProcessImg.Algorithms;

import org.jblas.DoubleMatrix;

public class CGNE extends CGALG {

    //H é a matriz modelo e g é o vetor de sinal
    public static int algorithm(DoubleMatrix H, DoubleMatrix g, DoubleMatrix result){
        int numberIteractions = 0;
        System.out.println("Executing CGNE!");

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

            // Valor absoluto do erro
            if(error < 0){
                error *= -1;
            }

            f = newF;
            r = newR;
            p = newP;
            numberIteractions++;
        }while (error > 1e-4 );

        result.copy(f);
        System.out.println("CGNE Finalized!");
        return numberIteractions;
    }
}
