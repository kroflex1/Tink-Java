package edu.project4.ImageProcessor;

import edu.project4.Records.FractalImage;
import edu.project4.Records.Pixel;
import edu.project4.Records.Point;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RotationalSymmetry implements ImageProcessor {
    private final static int DEFAULT_NUMBER_OF_SYMMETRY = 3;

    @Override
    public void process(FractalImage image) {
        process(image, DEFAULT_NUMBER_OF_SYMMETRY);
    }

    public void process(FractalImage image, int numberOfSymmetry) {
        if (numberOfSymmetry <= 1) {
            throw new IllegalArgumentException("Number of symmetries cannot be less than 1");
        }
        double angle = 2 * Math.PI / numberOfSymmetry;
        double stepAngle = 2 * Math.PI / numberOfSymmetry;
        List<Map<Point, Pixel>> result = new ArrayList<>();
        for (int i = 0; i < numberOfSymmetry - 1; ++i) {
            Map<Point, Pixel> rotatedPixels = getRotatedPixels(image, angle);
            result.add(rotatedPixels);
            angle += stepAngle;
        }
        for (var rotatedPixel : result) {
            addPixelsToImage(image, rotatedPixel);
        }
    }

    private Map<Point, Pixel> getRotatedPixels(FractalImage image, double angle) {
        Map<Point, Pixel> rotatedPixels = new HashMap<>();
        Point relativePoint = new Point(image.width() / 2.0, image.height() / 2.0);
        for (int x = 0; x < image.width(); ++x) {
            for (int y = 0; y < image.height(); ++y) {
                Pixel originPixel = image.getPixel(x, y);
                if (originPixel.color() != Color.BLACK) {
                    Point rotatedPoint = rotatePoint(new Point(x, y), relativePoint, angle);
                    if (rotatedPoint.x() >= 0 && rotatedPoint.x() < image.width() && rotatedPoint.y() >= 0
                        && rotatedPoint.y() < image.height()) {
                        rotatedPixels.put(rotatedPoint, originPixel);
                    }
                }
            }
        }
        return rotatedPixels;
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
                newColor = oldPixel.mix(newPixel.color()).color();
            }
            image.setPixel(x, y, new Pixel(newColor, oldPixel.hitCount() + newPixel.hitCount()));
        }
    }

    private Point rotatePoint(Point point, Point relativePoint, double angle) {
        double x = relativePoint.x() + (point.x() - relativePoint.x()) * Math.cos(angle) - (point.y()
            - relativePoint.y()) * Math.sin(angle);
        double y = relativePoint.y() + (point.x() - relativePoint.x()) * Math.sin(angle) + (point.y()
            - relativePoint.y()) * Math.cos(angle);
        return new Point(x, y);
    }
}
