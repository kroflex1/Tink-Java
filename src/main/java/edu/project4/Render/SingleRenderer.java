package edu.project4.Render;

import edu.project4.Records.Coefficients;
import edu.project4.Records.FractalImage;
import edu.project4.Records.Pixel;
import edu.project4.Records.Rect;
import edu.project4.Transformations.Transformation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleRenderer extends Renderer {

    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample
    ) {
        Map<Coordinate, Pixel> newPixels = new HashMap<>();
        fillWithNewPixels(world, coefficients, transformations, samples, iterPerSample, newPixels);
        return displayPixelsOnCanvas(canvas, newPixels);
    }
}
