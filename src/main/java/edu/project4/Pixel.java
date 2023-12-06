package edu.project4;

public record Pixel(int r, int g, int b, int hitCount) {
    public static Pixel Black() {
        return new Pixel(0, 0, 0, 0);
    }
}
