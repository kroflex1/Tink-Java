package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;
import java.util.concurrent.ThreadLocalRandom;

public interface Transformation {
    Point apply(Point point, Coefficients coefficients);

    default double getRadius(Point point) {
        return Math.pow(point.x(), 2) + Math.pow(point.y(), 2);
    }

    default double getRootOfRadius(Point point) {
        return Math.sqrt(getRadius(point));
    }

    default double getPhi(Point point) {
        return Math.atan2(point.y(), point.x());
    }

    default double getTheta(Point point) {
        return Math.atan2(point.x(), point.y());
    }

    default double getRandomOmega() {
        return ThreadLocalRandom.current().nextInt(2) == 0 ? 0 : Math.PI;
    }
}
