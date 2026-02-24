package Arkanoid.Sprite;

import Arkanoid.Game.Game;
import Arkanoid.Events.Collidable;
import Arkanoid.Geometry.Point;
import Arkanoid.Geometry.Rectangle;
import Arkanoid.Geometry.Velocity;
import Arkanoid.Utils.Configurations;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import java.awt.Color;

/**
 * Arkanoid.Sprite.Paddle class.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rectangle;
    private Color color;
    private Velocity speed;
    /**
     * @param rectangle paddle's rectangle
     * @param keyboard keyboard
     * @param c color
     * @param speed speed of paddle
     */
    public Paddle(Rectangle rectangle, KeyboardSensor keyboard, Color c, Velocity speed) {
        this.rectangle = new Rectangle(rectangle.getUpperLeft(), rectangle.getWidth(), rectangle.getHeight());
        this.keyboard = keyboard;
        this.color = c;
        this.speed = new Velocity(speed.getDx(), speed.getDy());
    }
    /**.
     * move paddle left
     */
    public void moveLeft() {
        Point nextTopLeft = new Point(this.rectangle.getUpperLeft().getX() - speed.getDx(),
                this.rectangle.getUpperLeft().getY());
        if (nextTopLeft.getX() <= 0) {
            nextTopLeft = new Point(Configurations.SCREEN_WIDTH, this.rectangle.getUpperLeft().getY());
        }
        this.rectangle = new Rectangle(nextTopLeft, rectangle.getWidth(), rectangle.getHeight());
    }
    /**.
     * move paddle right
     */
    public void moveRight() {
        Point nextTopLeft = new Point(this.rectangle.getUpperLeft().getX() + speed.getDx(),
                this.rectangle.getUpperLeft().getY());
        if (nextTopLeft.getX() + rectangle.getWidth() >= Configurations.SCREEN_WIDTH) {
            nextTopLeft = new Point(0, this.rectangle.getUpperLeft().getY());
        }
        this.rectangle = new Rectangle(nextTopLeft, rectangle.getWidth(), rectangle.getHeight());
    }
    // Arkanoid.Sprite.Arkanoid.Sprite
    /**
     * check if right or left keys were pressed.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }
    /**
     * @param d drawSurface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        Rectangle r = this.getCollisionRectangle();
        d.fillRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(),
                (int) r.getWidth(), (int) r.getHeight());
        d.setColor(Color.BLACK);
        d.drawRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(),
                (int) r.getWidth(), (int) r.getHeight());
    }
    // Arkanoid.GameLogic.Collidable
    /**
     * @return rectangle of paddle
     */
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }
    /**
     * @param collisionPoint point of collision
     * @param currentVelocity current velocity
     * @param b ball b
     * @return new velocity after the hit
     */
    public Velocity hit(Ball b, Point collisionPoint, Velocity currentVelocity) {
        double sizeOfRegion = this.rectangle.getWidth() / 5; //the paddle is divided into 5 regions of hit
        int region = calculateRegionOfHit(collisionPoint, sizeOfRegion);
        double speed = Math.sqrt(Math.pow(currentVelocity.getDx(), 2) + Math.pow(currentVelocity.getDy(), 2));
        switch (region) {
            case 1:
                return Velocity.fromAngleAndSpeed(300, speed);
            case 2:
                return Velocity.fromAngleAndSpeed(330, speed);
            case 3:
                return Velocity.fromAngleAndSpeed(0, speed);
            case 4:
                return Velocity.fromAngleAndSpeed(30, speed);
            default:
                return Velocity.fromAngleAndSpeed(60, speed);
        }
    }
    /**
     * Add this paddle to the game.
     * @param g Arkanoid.Game.Arkanoid.Game
     */
    public void addToGame(Game g) {
        g.addSprite(this);
        g.addCollidable(this);
    }
    /**
     * @param collisionPoint collision point
     * @param sizeRegion size of each hit region of the paddle
     * @return the region of the hit
     */
    public int calculateRegionOfHit(Point collisionPoint, double sizeRegion) {
        double xOfRec = this.rectangle.getUpperLeft().getX(); //x top left of the paddle
        double x = collisionPoint.getX();
        if (x <= xOfRec + sizeRegion) {
            return 1;
        }
        if (x <= xOfRec + 2 * sizeRegion) {
            return 2;
        }
        if (x <= xOfRec + 3 * sizeRegion) {
            return 3;
        }
        if (x <= xOfRec + 4 * sizeRegion) {
            return 4;
        }
        return 5;
    }
    /**
     * @return this speed
     */
    public Velocity getSpeed() {
        return speed;
    }
}