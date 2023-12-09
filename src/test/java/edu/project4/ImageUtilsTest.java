package edu.project4;

import edu.project4.Image.ImageFormat;
import edu.project4.Image.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.ImageIO;

import edu.project4.Records.FractalImage;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageUtilsTest {
    @ParameterizedTest
    @EnumSource(ImageFormat.class)
    void testCreateImage(ImageFormat imageFormat, @TempDir Path tempDir) {
        int width = 500;
        int height = 500;
        Path pathToImage = tempDir.resolve("testImage");
        FractalImage fractalImage = new FractalImage(width, height);
        ImageUtils.save(fractalImage, pathToImage, imageFormat);

        Path exceptPath = tempDir.resolve("testImage" + "." + imageFormat.toString().toLowerCase());
        assertTrue(Files.exists(exceptPath));

        try {
            BufferedImage bufferedImage = ImageIO.read(exceptPath.toFile());
            assertEquals(width, bufferedImage.getWidth());
            assertEquals(height, bufferedImage.getHeight());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
