package edu.project4.Records;

import java.awt.Color;

public record FractalImage(Pixel[][] data, int width, int height) {
    public static FractalImage createEmptyFractalImage(int width, int height) {
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

    public void setPixel(int x, int y, Pixel newPixel) {
        data[y][x] = newPixel;
    }

    public void increasePixelHitCount(int x, int y) {
        data[y][x] = data[y][x].increaseHitCount();
    }
}
