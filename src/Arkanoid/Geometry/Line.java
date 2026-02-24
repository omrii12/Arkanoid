package Arkanoid.Geometry;

import java.util.List;

/**
 * Arkanoid.Geometry.Line class.
 */
public class Line {
    private Point start;
    private Point end;
    private double m;
    private double b;
    /**
     * @param start start point of the line
     * @param end   end point of the line
     */
    public Line(Point start, Point end) {
        this.start = new Point(start.getX(), start.getY());
        this.end = new Point(end.getX(), end.getY());
        if (this.hasM()) {
            this.m = this.calculateM();
            this.b = this.calculateB();
        } else {
            this.m = Integer.MAX_VALUE;
        }
    }

    /**
     * @param x1 x of start point
     * @param y1 y of start point
     * @param x2 x of end point
     * @param y2 y of end point
     */
    public Line(double x1, double y1, double x2, double y2) {
        this(new Point(x1, y1), new Point(x2, y2)); // calls the regular constructor
    }


    /**
     * @return middle point of the line
     */
    public Point middle() {
        return new Point(((start.getX() + end.getX()) / 2), ((start.getY()) + end.getY()) / 2);
    }


    /**
     * @return the start point of the line
     */
    public Point start() {
        return new Point(this.start.getX(), this.start.getY());
    }

    /**
     * @return the end point of the line
     */
    public Point end() {
        return new Point(this.end.getX(), this.end.getY());
    }


    /**
     * @param other point to check if this intersects with other
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        if (other == null) {
            return false;
        }
        if (this.equals(other)) {
            return true;
        }

        Point p = this.intersectionWith(other);
        if (p == null && this.isInclusion(other)) {
            return true;
        }
        return p != null;
    }


    /**
     *
     * @param interX x of the intersection point
     * @param interY y of the intersection point
     * @return true if the intersection point is not in the range of the lines
     */
    public boolean isOutOfRange(double interX, double interY) {
        return interX > Math.max(this.start.getX(), this.end.getX()) + Point.COMPARISON_THRESHOLD
                || interX < Math.min(this.start.getX(), this.end.getX()) - Point.COMPARISON_THRESHOLD
                || interY > Math.max(this.start.getY(), this.end.getY()) + Point.COMPARISON_THRESHOLD
                || interY < Math.min(this.start.getY(), this.end.getY()) - Point.COMPARISON_THRESHOLD;
    }


    /**
     * @return the slope of this line (returns m in mx+b)
     */
    public double calculateM() {
        return (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
    }

    /**
     *
     * @return the slope of this line
     */
    public boolean hasM() {
        return this.end.getX() - this.start.getX() != 0;
    }

    /**
     *
     * @return the y of the intersection point of this line with Y axis of the line
     */
    public double calculateB() {
        return (this.start.getY() - this.m * this.start.getX());
    }


    /**
     * @param other1 point other1
     * @param other2 point other2
     * @return Returns true if this 2 lines intersect with this line, false otherwise
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return this.isIntersecting(other1) && this.isIntersecting(other2);
    }

    /**
     * @param other point other
     * @return Returns the intersection point if the lines intersect, and null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (other == null) {
            return null;
        }
        if (this.equals(other)) {
            return null;
        }
        if (this.isInclusion(other)) {
            return null;
        }
        if (this.start.equals(other.end)) {
            return this.start;
        }
        if (this.end.equals(other.start)) {
            return this.end;
        }
        if (this.start.equals(other.start)) {
            return this.start;
        }
        if (this.end.equals(other.end)) {
            return this.end;
        }

        if (!this.hasM() && !other.hasM()) { //this and other are x=something
            if (Math.abs(this.start.getX() - other.start.getX()) <= Point.COMPARISON_THRESHOLD) {

                return null;
            }
        } else if (!this.hasM()) { //this is x=something
            double interY = other.m * this.start.getX() + other.b;
            if (this.isOutOfRange(this.start.getX(), interY)
                    || other.isOutOfRange(this.start.getX(), interY)) {
                return null;
            }
            return new Point(this.start.getX(), interY);
        } else if (!other.hasM()) { //other is x=something
            double interY = this.m * other.start.getX() + this.b;
            if (this.isOutOfRange(other.start.getX(), interY)
                    || other.isOutOfRange(other.start.getX(), interY)) {
                return null;
            }
            return new Point(other.start.getX(), interY);

        } else { //both this and other are y=mx+b
            if (Math.abs(this.m - other.m) <= Point.COMPARISON_THRESHOLD) { //parallel lines
                return null;
            }
            double intersPointX = (other.b - this.b) / (this.m - other.m); // calculate intersection point's X
            double intersPointY = (this.m * intersPointX + this.b); //calculate intersection point's Y

            if (this.isOutOfRange(intersPointX, intersPointY)
                    || other.isOutOfRange(intersPointX, intersPointY)) {
                return null;
            }
            return new Point(intersPointX, intersPointY);
            //returns true if intersection point is in range, else otherwise
        }
        return null;
    }

    /**
     *
     * @param other line other
     * @return true if there is an inclusion between this and other, false otherwise
     */
    public boolean isInclusion(Line other) {
        if (!this.hasM() && !other.hasM()) { //both are x = something
            if (Math.abs(this.start.getX() - other.start.getX()) < Point.COMPARISON_THRESHOLD) { //both have the same x
                if (Math.max(this.start.getY(), other.start.getY())
                        < Math.min(this.end.getY(), other.end.getY())) {
                    return true;
                }
            }
        }

        if (Math.abs(this.m - other.m) < Point.COMPARISON_THRESHOLD
                && Math.abs(this.b - other.b) < Point.COMPARISON_THRESHOLD) { //both are y=mx+b
            if (Math.max(this.start.getX(), other.start.getX()) < Math.min(this.end.getX(), other.end.getX())) {
                return true;
            }
        }
        return  false;

    }


    /**
     * @param other point other
     * @return return true if the lines are equal, false otherwise
     */
    public boolean equals(Line other) {
        if (this.start.equals(other.start) && this.end.equals(other.end)) {
            return true;
        }
        if (this.start.equals(other.end) && this.end.equals(other.start)) {
            return true;
        }
        return false;
    }
    /**
     * @param rect rectangle
     * @return closest intersection point of the line with the rectangle
     * // If this line does not intersect with the rectangle, return null.
     *     // Otherwise, return the closest intersection point to the
     *     // start of the line.
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        List<Point> intersectionP = rect.intersectionPoints(this);
        if (intersectionP.isEmpty()) { //no intersection points
            return null;
        }
        double minDistance = Integer.MAX_VALUE;
        Point closest = null;
        for (Point p : intersectionP) {
            if (this.start.distance(p) < minDistance) { //checks if the distance is smaller
                minDistance = this.start.distance(p);
                closest = p;
            }
        }
        return closest;
    }

}
