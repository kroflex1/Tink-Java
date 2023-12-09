package edu.project4.Render;

import edu.project4.Records.Coefficients;
import edu.project4.Records.FractalImage;
import edu.project4.Records.Pixel;
import edu.project4.Records.Point;
import edu.project4.Records.Rect;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Transformation;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SingleRenderer implements Renderer {
    private final double MIN_X = -1.777;
    private final double MAX_X = 1.777;
    private final double MIN_Y = -1;
    private final double MAX_Y = 1;
    private final static Transformation AFFINE_TRANSFORMATION = new Affine();

    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample
    ) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int num = 0; num < samples; ++num) {
            Point newPoint = new Point(random.nextDouble(MIN_X, MAX_X), random.nextDouble(MIN_Y, MAX_Y));
            for (short step = -20; step < iterPerSample; ++step) {
                Coefficients currentCoeff = coefficients.get(random.nextInt(coefficients.size()));
                Transformation transformation = transformations.get(random.nextInt(transformations.size()));
                newPoint = transformation.apply(
                    AFFINE_TRANSFORMATION.apply(newPoint, currentCoeff),
                    currentCoeff
                );
                if (step >= 0 && newPoint.isBelongsToSegmentX(MIN_X, MAX_X)
                    && newPoint.isBelongsToSegmentY(MIN_X, MAX_X)) {
                    int x = (int) (world.width() - (MAX_X - newPoint.x()) / (MAX_X - MIN_X) * world.width());
                    int y = (int) (world.height() - (MAX_Y - newPoint.y()) / (MAX_Y - MIN_Y) * world.height());
                    if (world.contains(new Point(x, y))) {
                        Pixel currentPixel = canvas.getPixel(x, y);
                        if (currentPixel.hitCount() == 0) {
                            currentPixel = currentPixel.setNewColor(currentCoeff.color());
                        } else {
                            currentPixel = currentPixel.mixWithAnotherColor(currentCoeff.color());
                        }
                        canvas.setPixel(x, y, currentPixel.increaseHitCount());
                    }
                }
            }
        }
        return canvas;
    }
}
