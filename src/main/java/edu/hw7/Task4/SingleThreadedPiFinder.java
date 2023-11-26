package edu.hw7.Task4;

public class SingleThreadedPiFinder extends PiFinder {

    @Override
    @SuppressWarnings("MagicNumber")
    public double findPi(int numberOfIterations) {
        int totalCount = numberOfIterations;
        int circleCount = 0;
        for (int i = 0; i < numberOfIterations; ++i) {
            Point currentPoint = new Point(Math.random(), Math.random());
            double distanceToCenterOfCircle = getDistanceBetweenTwoPoints(CIRCLE_CENTER, currentPoint);
            if (distanceToCenterOfCircle <= RADIUS) {
                ++circleCount;
            }
        }
        return 4 * (circleCount * 1.0 / totalCount);
    }
}
