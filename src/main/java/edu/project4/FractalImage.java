package edu.project4;

import java.util.ArrayList;
import java.util.stream.Stream;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        Pixel[][] pixels = new Pixel[height][width];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                pixels[y][x] = Pixel.Black();
            }
        }
        return new FractalImage(pixels, width, height);
    }

    public Pixel getPixel(int x, int y) {
        return data[y][x];
    }

    public void changePixelColor(int x, int y, int red, int green, int blue) {
        data[y][x] = data[y][x].setNewColor(red, green, blue);
    }

    public void increasePixelHitCount(int x, int y) {
        data[y][x] = data[y][x].increaseHitCount();
    }

}
