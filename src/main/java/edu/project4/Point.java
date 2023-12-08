package edu.project4;

public record Point(double x, double y) {
    public boolean isBelongsToSegmentX(double start, double end) {
        checkSegment(start, end);
        return x >= start && x <= end;
    }

    public boolean isBelongsToSegmentY(double start, double end) {
        checkSegment(start, end);
        return y >= start && y <= end;
    }

    private void checkSegment(double start, double end) {
        if (start > end) {
            throw new IllegalArgumentException("Start cannot be greater than the end");
        }
    }
}
