package edu.project4.Render;

import edu.project4.Records.Coefficients;
import edu.project4.Records.FractalImage;
import edu.project4.Records.Pixel;
import edu.project4.Records.Rect;
import edu.project4.Transformations.Transformation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiRenderer extends Renderer {
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
        Map<Coordinate, Pixel> newPixels = new ConcurrentHashMap<>();
        CountDownLatch countDownLatch = new CountDownLatch(numberOFThreads);
        for (int i = 0; i < numberOFThreads * 2; ++i) {
            executor.submit(() -> {
                    fillWithNewPixels(
                        canvas,
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
        executor.shutdown();
        return displayPixelsOnCanvas(canvas, newPixels);
    }
}



