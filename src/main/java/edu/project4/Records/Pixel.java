package edu.project4.Records;

import java.awt.Color;

public record Pixel(Color color, int hitCount) {
    public static Pixel Black() {
        return new Pixel(Color.BLACK, 0);
    }

    public Pixel increaseHitCount() {
        return new Pixel(color, hitCount + 1);
    }

    public Pixel setNewColor(Color newColor) {
        return new Pixel(newColor, hitCount);
    }

    public Pixel mixWithAnotherColor(Color otherColor) {
        Color mixedColor = new Color(
            (color.getRed() + otherColor.getRed()) / 2,
            (color.getGreen() + otherColor.getGreen()) / 2,
            (color.getBlue() + otherColor.getBlue()) / 2
        );
        return new Pixel(mixedColor, hitCount);
    }
}
