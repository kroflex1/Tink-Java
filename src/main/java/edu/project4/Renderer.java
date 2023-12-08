package edu.project4;

import edu.project4.Transformations.Transformation;
import javax.xml.crypto.dsig.TransformService;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static java.lang.Math.random;

public class Renderer {
    public FractalImage render(
        FractalImage canvas, Rect world, List<Function> variations, int samples, short iterPerSample
    ) {
        for (int num = 0; num < samples; ++num) {
            Point point = new Point(getRandomValue(-1, 1), getRandomValue(-1, 1));
            for (short step = -20; step < iterPerSample; ++step) {
                Function function = getRandomTransformation(variations);
                point = function.apply(point);
                if (step >= 0 && point.isBelongsToSegmentX(-1, 1) && point.isBelongsToSegmentY(-1, 1)) {
                    int x = (int) (world.width() - (1 - point.x()) / 2 * world.width());
                    int y = (int) (world.height() - (1 - point.y()) / 2 * world.height());
                    if (world.contains(new Point(x, y))) {
                        Pixel currentPixel = canvas.getPixel(x, y);
                        if (currentPixel.hitCount() == 0) {
                            canvas.changePixelColor(x, y, function.red(), function.green(), function.blue());
                        } else {
                            int newRed = (canvas.getPixel(x, y).r() + function.red()) / 2;
                            int newGreen = (canvas.getPixel(x, y).g() + function.green()) / 2;
                            int newBlue = (canvas.getPixel(x, y).b() + function.blue()) / 2;
                            canvas.changePixelColor(x, y, newRed, newGreen, newBlue);
                        }
                        canvas.increasePixelHitCount(x, y);
                    }
                }
            }
        }
        return canvas;
    }

    private double getRandomValue(double from, double to) {
        return ThreadLocalRandom.current().nextDouble(from, to);
    }

    private Function getRandomTransformation(List<Function> variations) {
        int randomIndex = ThreadLocalRandom.current().nextInt(variations.size());
        return variations.get(randomIndex);
    }
}
