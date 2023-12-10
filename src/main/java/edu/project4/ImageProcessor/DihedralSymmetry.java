package edu.project4.ImageProcessor;

import edu.project4.Records.FractalImage;
import edu.project4.Records.Pixel;
import edu.project4.Records.Point;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

public class DihedralSymmetry implements ImageProcessor {
    @Override
    public void process(FractalImage image) {
        Map<Point, Pixel> symmetricalPixels = getSymmetricalPixels(image);
        addPixelsToImage(image, symmetricalPixels);

    }

    private void addPixelsToImage(FractalImage image, Map<Point, Pixel> newPixels) {
        for (Map.Entry<Point, Pixel> pair : newPixels.entrySet()) {
            int x = (int) pair.getKey().x();
            int y = (int) pair.getKey().y();
            Pixel oldPixel = image.getPixel(x, y);
            Pixel newPixel = pair.getValue();
            Color newColor;
            if (oldPixel.color() == Color.BLACK) {
                newColor = newPixel.color();
            } else {
                newColor = oldPixel.mixWithAnotherColor(newPixel.color()).color();
            }
            image.setPixel(x, y, new Pixel(newColor, oldPixel.hitCount() + newPixel.hitCount()));
        }
    }

    private Map<Point, Pixel> getSymmetricalPixels(FractalImage image) {
        Map<Point, Pixel> symmetryPixels = new HashMap<>();
        double xLine = image.width() / 2.0;
        for (int x = 0; x < image.width(); ++x) {
            for (int y = 0; y < image.height(); ++y) {
                Pixel originPixel = image.getPixel(x, y);
                if (originPixel.color() != Color.BLACK) {
                    double newX = x;
                    if (x == (int) xLine) {
                        continue;
                    }
                    if (x < xLine) {
                        newX = (x + (xLine - x) * 2);
                    } else if (x > xLine) {
                        newX = (x - (x - xLine) * 2);
                    }
                    symmetryPixels.put(new Point(newX, y), originPixel);
                }
            }
        }
        return symmetryPixels;
    }
}
