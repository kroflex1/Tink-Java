package edu.project4.Image;

import edu.project4.FractalImage;
import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageUtils {

    public static void save(FractalImage image, Path filename, ImageFormat format) {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < image.height(); ++y) {
            for (int x = 0; x < image.width(); ++x) {
                int alpha = 255;
                int red = image.getPixel(x, y).r();
                int green = image.getPixel(x, y).g();
                int blue = image.getPixel(x, y).b();
                Color color = new Color(red, green, blue, alpha);
                bufferedImage.setRGB(x, y, color.getRGB());
            }
        }
        try {
            Path filePath = Paths.get(
                filename.getParent().toString(),
                filename.getFileName().toString() + "." + format.toString().toLowerCase()
            );
            ImageIO.write(bufferedImage, format.toString().toLowerCase(), filePath.toFile());
        } catch (IOException e) {
            System.out.println("Invalid file path");
        }
    }

    private ImageUtils() {
    }
}
