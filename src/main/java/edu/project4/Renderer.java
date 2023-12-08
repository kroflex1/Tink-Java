package edu.project4;

import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Coefficients;
import edu.project4.Transformations.Transformation;
import javax.xml.crypto.dsig.TransformService;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.random;

public class Renderer {
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
                if (step >= 0 && newPoint.isBelongsToSegmentX(MIN_X, MAX_X) &&
                    newPoint.isBelongsToSegmentY(MIN_X, MAX_X)) {
                    int x = (int) (world.width() - (MAX_X - newPoint.x()) / (MAX_X - MIN_X) * world.width());
                    int y = (int) (world.height() - (MAX_Y - newPoint.y()) / (MAX_Y - MIN_Y) * world.height());
                    if (world.contains(new Point(x, y))) {
                        Pixel currentPixel = canvas.getPixel(x, y);
                        if (currentPixel.hitCount() == 0) {
                            canvas.changePixelColor(x, y, currentCoeff.red(), currentCoeff.green(), currentCoeff.blue()
                            );
                        } else {
                            int newRed = (canvas.getPixel(x, y).r() + currentCoeff.red()) / 2;
                            int newGreen = (canvas.getPixel(x, y).g() + currentCoeff.green()) / 2;
                            int newBlue = (canvas.getPixel(x, y).b() + currentCoeff.blue()) / 2;
                            canvas.changePixelColor(x, y, newRed, newGreen, newBlue);
                        }
                        canvas.increasePixelHitCount(x, y);
                    }
                }
            }
        }
        return canvas;
    }

}
