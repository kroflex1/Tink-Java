package edu.project4.Transformations;

import edu.project4.Point;
import java.util.function.Function;

public interface Transformation{

    public Point apply(Point point, Coefficients coefficients);

}
