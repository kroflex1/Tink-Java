package edu.project4.Transformations;

import edu.project4.Point;

public class Linear extends Transformation {
    public Linear(Coefficients coefficients) {
        super(coefficients);
    }

    @Override
    public Point apply(Point point) {
        return new Point(point.x(), point.y());
    }
}
