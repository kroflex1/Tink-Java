package edu.project4;

import edu.project4.Image.ImageFormat;
import edu.project4.Image.ImageUtils;
import edu.project4.Transformations.Coefficients;
import edu.project4.Transformations.Sinusoidal;
import edu.project4.Transformations.Swirl;
import edu.project4.Transformations.Transformation;
import edu.project4.Transformations.Waves;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import java.nio.file.Path;
import java.util.List;

public class RenderTest {

    @Test
    void test() {
        Rect rect = new Rect(0, 0, 1920, 1080);
        Renderer renderer = new Renderer();
        FractalImage canvas = FractalImage.createEmptyFractalImage(1920, 1080);

        Coefficients coefficients1 = Coefficients.createRandomCoefficients();
        Transformation transformation = new Sinusoidal();

        FractalImage result =
            renderer.render(canvas, rect, List.of(coefficients1, Coefficients.createRandomCoefficients(), Coefficients.createRandomCoefficients()), List.of(transformation), 50000, (short) 5000);

        Path path = Path.of("C:/Users/Kroflex/Desktop/test");
        ImageUtils.save(result, path, ImageFormat.BMP);
    }
}
