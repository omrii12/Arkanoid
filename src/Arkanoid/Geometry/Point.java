package Arkanoid.Geometry;

/**
 *
 */
public class Point {
    private double x;
    private double y;

    static final double COMPARISON_THRESHOLD = 0.00001;

    /**
     * @param x x coordinate of the point
     * @param y y coordinate of the point
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @param other point other to calculate the distance between this and other
     * @return the distance between this and other
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /**
     *
     * @return this.x
     */
    public double getX() {
        return x;
    }

    /**
     *
     * @return this.y
     */
    public double getY() {
        return y;
    }


    /**
     * @param other point other
     * @return returns true if points are equal
     */
    public boolean equals(Point other) {
        return Math.abs(this.getX() - other.getX()) <= Point.COMPARISON_THRESHOLD
                && Math.abs(this.getY() - other.getY()) <= Point.COMPARISON_THRESHOLD;
    }
}
