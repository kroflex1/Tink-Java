package edu.project4.Transformations;

import edu.project4.Point;

public abstract class Transformation {
    protected final Coefficients coeff;

    public Transformation(Coefficients coefficients) {
        coeff = coefficients;
    }

    public abstract Point apply(Point point);
}
