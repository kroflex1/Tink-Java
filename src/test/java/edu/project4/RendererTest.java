package edu.project4;

import edu.project4.Image.ImageFormat;
import edu.project4.Image.ImageUtils;
import edu.project4.ImageProcessor.GamaCorrection;
import edu.project4.ImageProcessor.ImageProcessor;
import edu.project4.Records.FractalImage;
import edu.project4.Records.Rect;
import edu.project4.Records.Coefficients;
import edu.project4.Render.MultiRenderer;
import edu.project4.Render.Renderer;
import edu.project4.Render.SingleRenderer;
import edu.project4.Transformations.Diemond;
import edu.project4.Transformations.Popcorn;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

public class RendererTest {
    @ParameterizedTest
    @MethodSource("renderers")
    void testRenderer(Renderer renderer) {
        Rect rect = new Rect(0, 0, 1920, 1080);
        FractalImage canvas = new FractalImage(1920, 1080);
        FractalImage result =
            renderer.render(
                canvas,
                rect,
                List.of(
                    Coefficients.createRandomCoefficients(),
                    Coefficients.createRandomCoefficients(),
                    Coefficients.createRandomCoefficients(),
                    Coefficients.createRandomCoefficients(),
                    Coefficients.createRandomCoefficients()
                ),
                List.of(new Diemond(), new Popcorn()),
                10000,
                (short) 3000
            );
        ImageProcessor imageProcessor = new GamaCorrection(0.55);
        imageProcessor.process(result);
        Path path = Path.of("C:/Users/Kroflex/Desktop/flames/" + renderer.getClass().getName());
        ImageUtils.save(result, path, ImageFormat.PNG);
    }


    private static Stream<Arguments> renderers() {
        return Stream.of(
            Arguments.of(new MultiRenderer()),
            Arguments.of(new SingleRenderer())
        );
    }
}
