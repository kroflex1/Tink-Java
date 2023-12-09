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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

public class MultiRenderer implements Renderer {
    private static final double MIN_X = -1.777;
    private static final double MAX_X = 1.777;
    private static final double MIN_Y = -1;
    private static final double MAX_Y = 1;
    private static final Transformation AFFINE_TRANSFORMATION = new Affine();
    private static final short DEFAULT_NUMBER_OF_THREADS = 4;

    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample
    ) {
        return render(canvas, world, coefficients, transformations, samples, iterPerSample, DEFAULT_NUMBER_OF_THREADS);
    }

    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample,
        short numberOFThreads
    ) {
        ExecutorService executor = Executors.newFixedThreadPool(numberOFThreads);
        ConcurrentHashMap<Coordinate, Pixel> newPixels = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(numberOFThreads);
        for (int i = 0; i < numberOFThreads * 2; ++i) {
            executor.submit(() -> {
                    partRender(
                        world,
                        coefficients,
                        transformations,
                        samples / (numberOFThreads * 2),
                        iterPerSample,
                        newPixels
                    );
                    countDownLatch.countDown();
                }
            );
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry<Coordinate, Pixel> inf : newPixels.entrySet()) {
            Pixel pixelOnCanvas = canvas.getPixel(inf.getKey().x, inf.getKey().y());
            if (pixelOnCanvas.hitCount() == 0) {
                canvas.setPixel(inf.getKey().x(), inf.getKey().y(), inf.getValue());
            } else {
                Color updatedColor = pixelOnCanvas.mixWithAnotherColor(inf.getValue().color()).color();
                Pixel updatedPixel =
                    new Pixel(updatedColor, pixelOnCanvas.hitCount() + inf.getValue().hitCount());
                canvas.setPixel(inf.getKey().x(), inf.getKey().y(), updatedPixel);
            }
        }
        executor.shutdown();
        return canvas;
    }

    private void partRender(
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample,
        ConcurrentHashMap<Coordinate, Pixel> newPixels
    ) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int num = 0; num < samples; ++num) {
            Point newPoint = new Point(random.nextDouble(MIN_X, MAX_X), random.nextDouble(MIN_Y, MAX_Y));
            for (short step = -20; step < iterPerSample; ++step) {
                Coefficients coeff = coefficients.get(random.nextInt(coefficients.size()));
                Transformation transformation = transformations.get(random.nextInt(transformations.size()));
                newPoint = transformation.apply(
                    AFFINE_TRANSFORMATION.apply(newPoint, coeff),
                    coeff
                );
                if (step >= 0 && newPoint.isBelongsToSegmentX(MIN_X, MAX_X)
                    && newPoint.isBelongsToSegmentY(MIN_X, MAX_X)) {
                    int x = (int) (world.width() - (MAX_X - newPoint.x()) / (MAX_X - MIN_X) * world.width());
                    int y = (int) (world.height() - (MAX_Y - newPoint.y()) / (MAX_Y - MIN_Y) * world.height());
                    if (world.contains(new Point(x, y))) {
                        Coordinate currentCoordinate = new Coordinate(x, y);
                        if (!newPixels.containsKey(currentCoordinate)) {
                            newPixels.put(currentCoordinate, new Pixel(coeff.color(), 1));
                        } else {
                            Pixel pixel = newPixels.get(currentCoordinate);
                            newPixels.replace(
                                currentCoordinate,
                                pixel.mixWithAnotherColor(coeff.color()).increaseHitCount()
                            );
                        }
                    }
                }
            }
        }
    }

    private record Coordinate(int x, int y) {

    }
}



