package edu.project4.Render;

import edu.project4.Records.Coefficients;
import edu.project4.Records.FractalImage;
import edu.project4.Records.Pixel;
import edu.project4.Records.Point;
import edu.project4.Records.Rect;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Renderer {
    protected static final double MIN_X = -1.777;
    protected static final double MAX_X = 1.777;
    protected static final double MIN_Y = -1;
    protected static final double MAX_Y = 1;
    protected static final short SKIP_STEPS = 20;
    protected static final Transformation AFFINE_TRANSFORMATION = new Affine();

    public abstract FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample
    );

    protected FractalImage displayPixelsOnCanvas(FractalImage canvas, Map<Coordinate, Pixel> newPixels) {
        for (Map.Entry<Coordinate, Pixel> inf : newPixels.entrySet()) {
            Pixel pixelOnCanvas = canvas.getPixel(inf.getKey().x, inf.getKey().y());
            if (pixelOnCanvas.hitCount() == 0) {
                canvas.setPixel(inf.getKey().x(), inf.getKey().y(), inf.getValue());
            } else {
                Color updatedColor = pixelOnCanvas.mix(inf.getValue().color()).color();
                Pixel updatedPixel =
                    new Pixel(updatedColor, pixelOnCanvas.hitCount() + inf.getValue().hitCount());
                canvas.setPixel(inf.getKey().x(), inf.getKey().y(), updatedPixel);
            }
        }
        return canvas;
    }

    protected void fillWithNewPixels(
        FractalImage canvas,
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample,
        Map<Coordinate, Pixel> currentColoredPixels
    ) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int num = 0; num < samples; ++num) {
            Point newPoint = new Point(random.nextDouble(MIN_X, MAX_X), random.nextDouble(MIN_Y, MAX_Y));
            for (short step = -SKIP_STEPS; step < iterPerSample; ++step) {
                Coefficients coeff = coefficients.get(random.nextInt(coefficients.size()));
                Transformation transformation = transformations.get(random.nextInt(transformations.size()));
                newPoint = transformation.apply(
                    AFFINE_TRANSFORMATION.apply(newPoint, coeff),
                    coeff
                );
                if (step >= 0 && newPoint.isBelongsToSegmentX(MIN_X, MAX_X)
                    && newPoint.belongsTo(MIN_X, MAX_X)) {
                    int x = (int) (world.width() - (MAX_X - newPoint.x()) / (MAX_X - MIN_X) * world.width());
                    int y = (int) (world.height() - (MAX_Y - newPoint.y()) / (MAX_Y - MIN_Y) * world.height());
                    if (world.contains(new Point(x, y)) && canvas.contains(x, y)) {
                        Coordinate currentCoordinate = new Coordinate(x, y);
                        if (!currentColoredPixels.containsKey(currentCoordinate)) {
                            currentColoredPixels.put(currentCoordinate, new Pixel(coeff.color(), 1));
                        } else {
                            Pixel pixel = currentColoredPixels.get(currentCoordinate);
                            currentColoredPixels.replace(
                                currentCoordinate,
                                pixel.mix(coeff.color()).increaseHitCount()
                            );
                        }
                    }
                }
            }
        }
    }

    protected record Coordinate(int x, int y) {

    }
}
