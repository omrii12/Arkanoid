package Arkanoid.Geometry;

/**
 * Arkanoid.Sprite.Ball.Arkanoid.Geometry.Velocity specifies the change in position on the `x` and the `y` axes.
  */
public class Velocity {
    private double dx;
    private double dy;

    /**
     *
     * @param dx velocity in x-axis
     * @param dy velocity in y-axis
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     *
     * @param angle angle of velocity
     * @param speed speed
     * @return new Arkanoid.Sprite.Ball.Arkanoid.Geometry.Velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double dx = Math.sin(Math.toRadians(angle)) * speed;
        double dy = -Math.cos(Math.toRadians(angle)) * speed;
        return new Velocity(dx, dy);
    }
    /**
     *
     * @return this.dx
     */
    public double getDx() {
        return dx;
    }

    /**
     *
     * @return this.dy
     */
    public double getDy() {
        return dy;
    }

    /**.
     * Take a point with position (x,y) and return a new point with position (x+dx, y+dy)
     * @param p point p
     * @return new point with position (x+dx, y+dy)
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + this.dx, p.getY() + this.dy);
    }

}