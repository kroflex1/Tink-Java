package edu.project4;

import edu.project4.Image.ImageFormat;
import edu.project4.Image.ImageUtils;
import edu.project4.ImageProcessor.GamaCorrection;
import edu.project4.ImageProcessor.RotationalSymmetry;
import edu.project4.Records.Coefficients;
import edu.project4.Records.FractalImage;
import edu.project4.Records.Rect;
import edu.project4.Render.MultiRenderer;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Diamond;
import edu.project4.Transformations.Exponential;
import edu.project4.Transformations.Handkerchief;
import edu.project4.Transformations.Horseshoe;
import edu.project4.Transformations.Julia;
import edu.project4.Transformations.Popcorn;
import edu.project4.Transformations.Sinusoidal;
import edu.project4.Transformations.Spherical;
import edu.project4.Transformations.Spiral;
import edu.project4.Transformations.Swirl;
import edu.project4.Transformations.Transformation;
import edu.project4.Transformations.Waves;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FractalFlameHelper {
    private final static List<Transformation> TRANSFORMATIONS = List.of(
        new Affine(),
        new Diamond(),
        new Exponential(),
        new Handkerchief(),
        new Horseshoe(),
        new Julia(),
        new Popcorn(),
        new Sinusoidal(),
        new Spherical(),
        new Spiral(),
        new Swirl(),
        new Waves()
    );

    private final static short MIN_NUMBER_OF_TRANSFORMATIONS = 1;
    private final static short MAX_NUMBER_OF_TRANSFORMATIONS = 5;
    private final static short MIN_NUMBER_OF_COEFFICIENTS = 1;
    private final static short MAX_NUMBER_OF_COEFFICIENTS = 10;
    private final static short MAX_NUMBER_OF_ROTATIONAL_SYMMETRY = 12;
    private final static int DEFAULT_WIDTH = 1920;
    private final static int DEFAULT_HEIGHT = 1080;
    private final static int MAX_NUMBER_OF_SAMPLES = 200_000;
    private final static int MIN_NUMBER_OF_SAMPLES = 20_000;
    private final static short MAX_NUMBER_OF_ITER_PER_SAMPLE = 3000;
    private final static short MIN_NUMBER_OF_ITER_PER_SAMPLE = 100;
    private final static double GAMMA = 0.6;

    public static void generateRandomFlames(Path folder, short numberOfFlames, ImageFormat imageFormat) {
        GamaCorrection gamaCorrection = new GamaCorrection(GAMMA);
        for (int i = 0; i < numberOfFlames; ++i) {
            FractalImage image = generateFractalImage();
            gamaCorrection.process(image);
            ImageUtils.save(image, folder.resolve(String.valueOf(i)), imageFormat);
        }
    }

    private static FractalImage generateFractalImage() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        FractalImage canvas = new FractalImage(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        Rect world = new Rect(0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
        List<Transformation> transformations = getRandomTransformations();
        List<Coefficients> coefficients = getRandomCoefficients();
        int samples = random.nextInt(MIN_NUMBER_OF_SAMPLES, MAX_NUMBER_OF_SAMPLES + 1);
        short iterPerSample = (short) random.nextInt(MIN_NUMBER_OF_ITER_PER_SAMPLE, MAX_NUMBER_OF_ITER_PER_SAMPLE);
        canvas = new MultiRenderer().render(canvas, world, coefficients, transformations, samples, iterPerSample);
        if (random.nextInt(2) == 1) {
            RotationalSymmetry rotationalSymmetry = new RotationalSymmetry();
            rotationalSymmetry.process(canvas, random.nextInt(2, MAX_NUMBER_OF_ROTATIONAL_SYMMETRY));
        }
        return canvas;
    }

    private static List<Transformation> getRandomTransformations() {
        List<Transformation> result = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < random.nextInt(MIN_NUMBER_OF_TRANSFORMATIONS, MAX_NUMBER_OF_TRANSFORMATIONS + 1); ++i) {
            result.add(TRANSFORMATIONS.get(random.nextInt(TRANSFORMATIONS.size())));
        }
        return result;
    }

    private static List<Coefficients> getRandomCoefficients() {
        List<Coefficients> result = new ArrayList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < random.nextInt(MIN_NUMBER_OF_COEFFICIENTS, MAX_NUMBER_OF_COEFFICIENTS + 1); ++i) {
            result.add(Coefficients.createRandomCoefficients());
        }
        return result;
    }

    private FractalFlameHelper() {

    }
}
