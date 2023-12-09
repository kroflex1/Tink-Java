package edu.project4.Records;

import java.awt.Color;

public class FractalImage {
    private final int width;
    private final int height;
    private final Pixel[][] data;

    public FractalImage(int width, int height) {
        this.width = width;
        this.height = height;
        data = new Pixel[height][width];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                data[y][x] = Pixel.createBlack();
            }
        }
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public Pixel getPixel(int x, int y) {
        return data[y][x];
    }

    public void setPixel(int x, int y, Pixel newPixel) {
        data[y][x] = newPixel;
    }

    public void updatePixelColor(int x, int y, Color color) {
        data[y][x] = data[y][x].setNewColor(color);
    }
}
