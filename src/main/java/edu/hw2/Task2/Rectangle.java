package edu.hw2.Task2;

public class Rectangle {
    private final int height;
    private final int width;

    public Rectangle(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public Rectangle() {
        this(0, 0);
    }

    public Rectangle setWidth(int width) {
        return new Rectangle(this.height, width);
    }

    public Rectangle setHeight(int height) {
        return new Rectangle(height, this.width);
    }

    public double area() {
        return width * height;
    }

}
