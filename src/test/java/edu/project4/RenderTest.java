package edu.project4;

import edu.project4.Image.ImageFormat;
import edu.project4.Image.ImageUtils;
import edu.project4.Transformations.Coefficients;
import edu.project4.Transformations.Diemond;
import edu.project4.Transformations.Exponential;
import edu.project4.Transformations.Linear;
import edu.project4.Transformations.Popcorn;
import edu.project4.Transformations.Sinusoidal;
import edu.project4.Transformations.Spherical;
import edu.project4.Transformations.Swirl;
import edu.project4.Transformations.Waves;
import org.junit.jupiter.api.Test;
import java.nio.file.Path;
import java.util.List;

public class RenderTest {

    @Test
    void test() {
        Rect rect = new Rect(0, 0, 1920, 1080);
        Renderer renderer = new Renderer();
        FractalImage canvas = FractalImage.create(1920, 1080);

        Coefficients coefficients1 = new Coefficients(-0.493,-1.066,1.032,-0.054,0.110,1.294);
        Function function1 = new Function(new Waves(coefficients1), coefficients1, 0.578125);

        Coefficients coefficients2 = new Coefficients(-0.636,-0.776,0.141,0.317,0.771,0.4140625);
        Function function2 = new Function(new Waves(coefficients2), coefficients2);
        FractalImage result =
            renderer.render(canvas, rect, List.of(function1, function2), 100000, (short) 3000);

        Path path = Path.of("C:/Users/Kroflex/Desktop/test");
        ImageUtils.save(result, path, ImageFormat.BMP);
    }
}
