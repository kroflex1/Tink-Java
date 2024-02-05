package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public class Diamond implements Transformation {
    @Override
    public Point apply(Point point, Coefficients coefficients) {
        double r = getRootOfRadius(point);
        double phi = getTheta(point);
        return new Point(Math.sin(phi) * Math.cos(r), Math.cos(phi) * Math.sin(r));
    }
}
