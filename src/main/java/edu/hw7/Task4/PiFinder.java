package edu.hw7.Task4;

public abstract class PiFinder {
    protected final static Point CIRCLE_CENTER = new Point(0.5, 0.5);
    protected final static double RADIUS = 0.5;

    public abstract double findPi(int numberOfIterations);

    protected double getDistanceBetweenTwoPoints(Point firstPoint, Point secondPoint) {
        return Math.sqrt(Math.pow(firstPoint.x() - secondPoint.x(), 2)
            + Math.pow(firstPoint.y() - secondPoint.y(), 2));
    }
}
