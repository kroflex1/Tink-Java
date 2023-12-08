package edu.project4;

public record Pixel(int r, int g, int b, int hitCount) {
    public static Pixel Black() {
        return new Pixel(0, 0, 0, 0);
    }

    public Pixel increaseHitCount() {
        return new Pixel(r(), g(), b(), hitCount + 1);
    }

    public Pixel setNewColor(int r, int g, int b) {
        return new Pixel(r, g, b, hitCount);
    }
}
