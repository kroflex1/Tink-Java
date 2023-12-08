package edu.project4.Records;

import edu.project4.Records.Point;
import java.util.concurrent.ThreadLocalRandom;

public record Rect(double x, double y, double width, double height) {
    public boolean contains(Point p) {
        return p.x() >= x && p.x() < x + width && p.y() >= y && p.y() < y + height;
    }

    public Point getRandomPoint() {
        return new Point(
            ThreadLocalRandom.current().nextDouble(x, x + width),
            ThreadLocalRandom.current().nextDouble(y, y + height)
        );
    }
}
