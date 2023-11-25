package ProcessImg;

import org.jblas.DoubleMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class Image {
    private int width;
    private int height;

    BufferedImage buffImg;

    public Image(DoubleMatrix matrix){
        width = matrix.columns;
        height = matrix.rows;

        buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for(int x = 0 ; x < width ; x++){
            for(int y = 0 ; y < height ;  y++){
                BigDecimal alpha = new BigDecimal(matrix.get(x,y));
                alpha = alpha.abs();

                buffImg.setRGB(y,x, Color.HSBtoRGB(0,
                        0,
                        (float)alpha.doubleValue()));
            }
        }
    }

    public byte[] toByteArray() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(buffImg,"png", bos);
        return bos.toByteArray();
    }

    public void saveImage() throws IOException {
        File file = new File("img.png");
        ImageIO.write(buffImg, "png", file);
    }
}
