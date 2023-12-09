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
import edu.project4.Transformations.Exponential;
import edu.project4.Transformations.Popcorn;
import edu.project4.Transformations.Transformation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RendererTest {
    @Test
    void testMultiRendererFasterThanSimpleRenderer() {
        FractalImage canvas = new FractalImage(1920, 1080);
        Rect world = new Rect(0, 0, 1920, 1080);
        List<Coefficients> coefficients = List.of(
            Coefficients.createRandomCoefficients(),
            Coefficients.createRandomCoefficients(),
            Coefficients.createRandomCoefficients(),
            Coefficients.createRandomCoefficients(),
            Coefficients.createRandomCoefficients()
        );
        List<Transformation> transformations = List.of(new Exponential());
        int samples = 10000;
        short iterPerSample = (short) 6000;

        long startTime = System.nanoTime();
        MultiRenderer multiRenderer = new MultiRenderer();
        multiRenderer.render(canvas, world, coefficients, transformations, samples, iterPerSample);
        long endTime = System.nanoTime();
        long multiRendererTime = endTime - startTime;

        startTime = System.nanoTime();
        SingleRenderer singleRenderer = new SingleRenderer();
        singleRenderer.render(canvas, world, coefficients, transformations, samples, iterPerSample);
        endTime = System.nanoTime();
        long simpleRendererTime = endTime - startTime;

        assertTrue(multiRendererTime < simpleRendererTime);
    }

//    @ParameterizedTest
//    @MethodSource("parameters")
//    void testMultiThreadRenderer(
//        FractalImage canvas,
//        Rect world,
//        List<Transformation> transformations,
//        int samples,
//        short iterPersample
//    ) {
//        Renderer renderer = new MultiRenderer();
//        FractalImage result = renderer.render(canvas, world, coefficients, transformations, samples, iterPersample);
//        ImageProcessor imageProcessor = new GamaCorrection(0.55);
//        imageProcessor.process(result);
//        Path path = Path.of("C:/Users/Kroflex/Desktop/flames/" + renderer.getClass().getName());
//        ImageUtils.save(result, path, ImageFormat.PNG);
//    }

//    private static Stream<Arguments> parameters() {
//        FractalImage canvas = new FractalImage(1920, 1080);
//        Rect rect = new Rect(0, 0, 1920, 1080);
//        List<Transformation> transformations = List.of(new Exponential());
//        int samples = 10000;
//        short iterPerSample = (short) 6000;
//        return Stream.of(
//            Arguments.of(canvas, rect, transformations, samples, iterPerSample)
//        );
//    }
//
//    private static Stream<Arguments> renderers() {
//        return Stream.of(
//            Arguments.of(new MultiRenderer()),
//            Arguments.of(new SingleRenderer())
//        );
//    }
}
