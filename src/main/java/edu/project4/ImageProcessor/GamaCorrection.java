package edu.project4.ImageProcessor;

import edu.project4.Records.FractalImage;
import edu.project4.Records.Pixel;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class GamaCorrection implements ImageProcessor {

    private final double gamma;

    public GamaCorrection(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void process(FractalImage image) {
        double max = 0;
        List<PixelInf> pixelsThatNeedToChangeColor = new ArrayList<>();
        for (int y = 0; y < image.height(); ++y) {
            for (int x = 0; x < image.width(); ++x) {
                Pixel currentPixel = image.getPixel(x, y);
                if (currentPixel.hitCount() != 0) {
                    double normal = Math.log10(currentPixel.hitCount());
                    pixelsThatNeedToChangeColor.add(new PixelInf(x, y, currentPixel.color(), normal));
                    max = Math.max(max, normal);
                }
            }
        }
        for (PixelInf pixelInf : pixelsThatNeedToChangeColor) {
            int newRed = (int) (pixelInf.color().getRed() * Math.pow(pixelInf.normal() / max, 1.0 / gamma));
            int newGreen = (int) (pixelInf.color().getGreen() * Math.pow(pixelInf.normal() / max, 1.0 / gamma));
            int newBlue = (int) (pixelInf.color().getBlue() * Math.pow(pixelInf.normal() / max, 1.0 / gamma));
            Color newColor = new Color(newRed, newGreen, newBlue);
            image.updatePixelColor(pixelInf.x(), pixelInf.y(), newColor);
        }
    }

    private record PixelInf(int x, int y, Color color, double normal) {

    }
}
