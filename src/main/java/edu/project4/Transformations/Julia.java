package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public class Julia implements Transformation {
    @Override
    public Point apply(Point point, Coefficients coefficients) {
        double r = Math.sqrt(getRootOfRadius(point));
        double theta = getTheta(point);
        double omega = getRandomOmega();
        return new Point(r * Math.cos(theta / 2 + omega), r * Math.sin(theta / 2 + omega));
    }

}
