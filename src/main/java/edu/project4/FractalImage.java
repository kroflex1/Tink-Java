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

    public boolean contains(int x, int y) {
        return true;
    }

    public Pixel pixel(int x, int y) {
        return new Pixel(0, 0, 0, 0);
    }

}
