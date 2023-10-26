package edu.hw2.Task1;

public record Multiplication(Expr firstExpr, Expr secondExpr) implements Expr {
    @Override
    public double evaluate() {
        return firstExpr().evaluate() * secondExpr().evaluate();
    }
}
