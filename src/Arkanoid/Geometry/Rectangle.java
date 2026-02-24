package Arkanoid.Geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * rectangle class.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    /**
     * Create a new rectangle with location and width/height.
     * @param upperLeft top left corner of the rectangle
     * @param width width of rectangle
     * @param height height of rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = new Point(upperLeft.getX(), upperLeft.getY());
        this.width = width;
        this.height = height;
    }
    /**
     * @param line line
     * @return a (possibly empty) List of intersection points
     */
    public List<Point> intersectionPoints(Line line) {
        double x = this.upperLeft.getX();
        double y = this.upperLeft.getY();
        Line l1 = new Line(x, y, x + width, y);
        Line l2 = new Line(x + width, y, x + width, y + height);
        Line l3 = new Line(x + width, y + height, x, y + height);
        Line l4 = new Line(x, y + height, x, y);
        Line[] lines = new Line[]{l1, l2, l3, l4};
        List<Point> list = new ArrayList<>();
        for (Line l : lines) {
            Point p = l.intersectionWith(line);
            if (p != null) {
                list.add(p);
            }
        }
        return list;
    }

    /**
     * @return the width of the rectangle
     */
    public double getWidth() {
        return this.width;
    }
    /**
     * @return this.height
     */
    public double getHeight() {
        return this.height;
    }
    /**
     * Returns the upper-left point of the rectangle.
     * @return top left corner
     */
    public Point getUpperLeft() {
        return new Point(this.upperLeft.getX(), this.upperLeft.getY());
    }
    /**.
     * @param p point p
     * @return true if a point is inside the rectangle, false otherwise
     */
    public boolean contains(Point p) {
        double x = p.getX();
        double y = p.getY();
        double left = this.upperLeft.getX();
        double right = left + this.width;
        double top = this.upperLeft.getY();
        double bottom = top + this.height;
        return (x >= left && x <= right && y >= top && y <= bottom);
    }

}