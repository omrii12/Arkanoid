package Arkanoid.Utils;

import Arkanoid.Geometry.Line;
import Arkanoid.Geometry.Point;

import java.awt.Color;
import java.util.Random;

/**
 * this class is for all frame configurations, like screen size, rectangle size..
 */
public class Configurations {
    public static final Point GREY_RECT_START = new Point(50, 50);
    public static final Point GREY_RECT_END = new Point(500, 500);
    public static final Point YELLOW_RECT_START = new Point(450, 450);
    public static final Point YELLOW_RECT_END = new Point(600, 600);
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 600;

    public static final double COMPARISON_THRESHOLD = 0.0001;
    /**
     * generates a random color.
     * @return new color
     */
    public static Color generateRandomColor() {
        Random r = new Random();
        return new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
    }

    /**
     * @param radius radius of the ball
     * @return new point outside both rectangles
     */
    public static Point generateRandomPointOutsideRects(int radius) {
        Random rand = new Random();
        int minX = (int) GREY_RECT_END.getX() + radius + 1;
        int maxX = SCREEN_WIDTH - radius - 1;
        int minY = radius + 1;
        int maxY = (int) YELLOW_RECT_START.getY() - radius - 1;

        int x = rand.nextInt(maxX - minX) + minX;
        int y = rand.nextInt(maxY - minY) + minY;

        return new Point(x, y);
    }


    /**
     * @return Arkanoid.Geometry.Line array with all borders
     */
    public static Line[] createFrameBorders() {
        int w = Configurations.SCREEN_WIDTH;
        int h = Configurations.SCREEN_HEIGHT;
        Line l1 = new Line(0, 0, w, 0);
        Line l2 = new Line(w, 0, w, h);
        Line l3 = new Line(w, h, 0, h);
        Line l4 = new Line(0, h, 0, 0);
        return new Line[]{l1, l2, l3, l4};
    }

    /**
     * @return line array with all borders of gray rectangle
     */
    public static Line[] createGrayRecBorders() {
        int x1 = (int) Configurations.GREY_RECT_START.getX();
        int y1 = (int) Configurations.GREY_RECT_START.getY();
        int x2 = (int) Configurations.GREY_RECT_END.getX();
        int y2 = (int) Configurations.GREY_RECT_END.getY();

        Line l1 = new Line(x1, y1, x2, y1);
        Line l2 = new Line(x2, y1, x2, y2);
        Line l3 = new Line(x2, y2, x1, y2);
        Line l4 = new Line(x1, y2, x1, y1);
        return new Line[]{l1, l2, l3, l4};
    }
    /**
     * @return line array with all borders of yellow rectangle
    **/
    public static Line[] createYelRecBorders() {
        int x1 = (int) Configurations.YELLOW_RECT_START.getX();
        int y1 = (int) Configurations.YELLOW_RECT_START.getY();
        int x2 = (int) Configurations.YELLOW_RECT_END.getX();
        int y2 = (int) Configurations.YELLOW_RECT_END.getY();

        Line l1 = new Line(x1, y1, x2, y1);
        Line l2 = new Line(x2, y1, x2, y2);
        Line l3 = new Line(x2, y2, x1, y2);
        Line l4 = new Line(x1, y2, x1, y1);
        return new Line[]{l1, l2, l3, l4};
    }

}
