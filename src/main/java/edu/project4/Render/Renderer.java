package edu.project4.Render;

import edu.project4.Records.Coefficients;
import edu.project4.Records.FractalImage;
import edu.project4.Records.Rect;
import edu.project4.Transformations.Transformation;
import java.util.List;

public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample
    );
}
