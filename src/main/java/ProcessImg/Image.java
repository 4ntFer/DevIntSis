package ProcessImg;

import org.jblas.DoubleMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class Image {
    private int width;
    private int height;
    private DoubleMatrix matrix;

    public Image(DoubleMatrix f){
        width = f.columns;
        height = f.rows;
        matrix = f;

    }

    public void saveImage() throws IOException {
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for(int x = 0 ; x < width ; x++){
            for(int y = 0 ; y < height ;  y++){
                BigDecimal alpha = new BigDecimal(matrix.get(x,y));
                alpha = alpha.abs();

                buffImg.setRGB(y,x, Color.HSBtoRGB(0,
                        0,
                        (float)alpha.doubleValue()));
            }
        }

        File file = new File("img.png");
        ImageIO.write(buffImg, "png", file);
    }
}
