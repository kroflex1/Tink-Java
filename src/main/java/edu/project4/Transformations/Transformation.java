package edu.project4.Transformations;

import edu.project4.Records.Coefficients;
import edu.project4.Records.Point;

public interface Transformation{

    public Point apply(Point point, Coefficients coefficients);

}
