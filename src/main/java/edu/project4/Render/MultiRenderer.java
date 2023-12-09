package edu.project4.Render;

import edu.project4.Records.Coefficients;
import edu.project4.Records.FractalImage;
import edu.project4.Records.Pixel;
import edu.project4.Records.Point;
import edu.project4.Records.Rect;
import edu.project4.Transformations.Affine;
import edu.project4.Transformations.Transformation;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

public class MultiRenderer implements Renderer {
    private final double MIN_X = -1.777;
    private final double MAX_X = 1.777;
    private final double MIN_Y = -1;
    private final double MAX_Y = 1;
    private final static Transformation AFFINE_TRANSFORMATION = new Affine();
    private final static short DEFAULT_NUMBER_OF_THREADS = 4;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();

    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample
    ) {
        ExecutorService executor = Executors.newFixedThreadPool(DEFAULT_NUMBER_OF_THREADS);
        List<CompletableFuture<Map<Coordinate, Pixel>>> tasks = new ArrayList<>();
        for (int i = 0; i < DEFAULT_NUMBER_OF_THREADS; ++i) {
            tasks.add(CompletableFuture.supplyAsync(() -> partRender(
                world,
                coefficients,
                transformations,
                samples / DEFAULT_NUMBER_OF_THREADS,
                iterPerSample
            ), executor));
        }
        for (var task : tasks) {
            try {
                for (Map.Entry<Coordinate, Pixel> inf : task.get().entrySet()) {
                    Pixel pixelOnCanvas = canvas.getPixel(inf.getKey().x, inf.getKey().y());
                    if (pixelOnCanvas.hitCount() == 0) {
                        canvas.setPixel(inf.getKey().x, inf.getKey().y(), inf.getValue());
                    } else {
                        Color updatedColor = pixelOnCanvas.mixWithAnotherColor(inf.getValue().color()).color();
                        Pixel updatedPixel =
                            new Pixel(updatedColor, pixelOnCanvas.hitCount() + inf.getValue().hitCount());
                        canvas.setPixel(inf.getKey().x, inf.getKey().y(), updatedPixel);
                    }

                }
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        executor.shutdown();
        return canvas;
    }

    private Map<Coordinate, Pixel> partRender(
        Rect world,
        List<Coefficients> coefficients,
        List<Transformation> transformations,
        int samples,
        short iterPerSample
    ) {
        Map<Coordinate, Pixel> result = new HashMap<>();
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
                if (step >= 0 && newPoint.isBelongsToSegmentX(MIN_X, MAX_X) &&
                    newPoint.isBelongsToSegmentY(MIN_X, MAX_X)) {
                    int x = (int) (world.width() - (MAX_X - newPoint.x()) / (MAX_X - MIN_X) * world.width());
                    int y = (int) (world.height() - (MAX_Y - newPoint.y()) / (MAX_Y - MIN_Y) * world.height());
                    if (world.contains(new Point(x, y))) {
                        Coordinate currentCoordinate = new Coordinate(x, y);
                        if (!result.containsKey(currentCoordinate)) {
                            result.put(currentCoordinate, new Pixel(coeff.color(), 1));
                        } else {
                            Pixel pixel = result.get(currentCoordinate);
                            result.put(currentCoordinate, pixel.mixWithAnotherColor(coeff.color()).increaseHitCount());
                        }
                    }
                }
            }
        }
        return result;
    }

    private record Coordinate(int x, int y) {

    }
}
