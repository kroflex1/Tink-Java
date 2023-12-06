package edu.project4.Transformations;

import edu.project4.Point;

public class Swirl implements Transformation {

    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(Math.pow(point.x(), 2) + Math.pow(point.y(), 2));
        double sinR = Math.sin(r * r);
        double cosR = Math.cos(r * r);
        return new Point(point.x() * sinR - point.y() * cosR, point.x() * cosR + point.y() * sinR);
    }
}
