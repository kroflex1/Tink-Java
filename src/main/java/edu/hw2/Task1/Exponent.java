package edu.hw2.Task1;

public record Exponent(Expr expr, Expr power) implements Expr {
    @Override
    public double evaluate() {
        return Math.pow(expr().evaluate(), power().evaluate());
    }

    public Exponent(Expr expr, int power) {
        this(expr, new Constant(power));
    }
}
