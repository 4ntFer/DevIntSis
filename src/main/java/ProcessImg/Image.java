package ProcessImg;

import org.jblas.DoubleMatrix;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

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
                float alpha = (float)matrix.get(x,y);

                if(alpha < 0)
                    alpha *= -1;

                buffImg.setRGB(y,x, Color.HSBtoRGB(0,
                        0,
                        alpha));

                System.out.println(alpha);
            }
        }

        File file = new File("img.png");
        ImageIO.write(buffImg, "png", file);
    }
}
