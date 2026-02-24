package Arkanoid.Sprite;

import Arkanoid.Game.Game;
import Arkanoid.Events.Collidable;
import Arkanoid.Events.HitListener;
import Arkanoid.Events.HitNotifier;
import Arkanoid.Geometry.Point;
import Arkanoid.Geometry.Rectangle;
import Arkanoid.Geometry.Velocity;
import Arkanoid.Utils.Configurations;
import biuoop.DrawSurface;
import java.awt.Color;
import java.util.List;
import java.util.ArrayList;
/**
 * block class.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rectangle;
    private Color color;
    private List<HitListener> hitListeners;
    private boolean isBorder;
    private boolean isDeathRegion;


    /**
     * @param rectangle rectangle
     * @param color color of rectangle
     */
    public Block(Rectangle rectangle, Color color) {
        this.rectangle = rectangle;
        this.color = color;
        this.hitListeners = new ArrayList<>();
    }
    /**
     * @param rectangle rectangle
     * @param color color
     * @param isBorder true if this block is a border, false otherwise
     */
    public Block(Rectangle rectangle, Color color, boolean isBorder) {
        this(rectangle, color);
        this.isBorder = isBorder;
    }
    /**
     * @return the collision shape of the object
     */
    @Override
    public Rectangle getCollisionRectangle() {
        return this.rectangle;
    }
    /**.
     * sets deathRegion
     * @param deathRegion true if it is the deathregion, false otherwise
     */
    public void setDeathRegion(boolean deathRegion) {
        isDeathRegion = deathRegion;
    }

    /**
     * @param collisionPoint  point of collision
     * @param currentVelocity current velocity
     * @param hitter Arkanoid.Sprite.Ball hitter.
     * @return new velocity after the hit
     */
    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double threshold = Configurations.COMPARISON_THRESHOLD;
        boolean hitVerticalWall = Math.abs(collisionPoint.getX() - rectangle.getUpperLeft().getX()) < threshold
                || Math.abs(collisionPoint.getX()
                - (rectangle.getUpperLeft().getX() + rectangle.getWidth())) < threshold;
        boolean hitHorizontalWall = Math.abs(collisionPoint.getY() - rectangle.getUpperLeft().getY()) < threshold
                || Math.abs(collisionPoint.getY()
                - (rectangle.getUpperLeft().getY() + rectangle.getHeight())) < threshold;
        double newDx = currentVelocity.getDx();
        double newDy = currentVelocity.getDy();
        if (hitVerticalWall) { //hit a vertical wall
            newDx = -newDx;
        }
        if (hitHorizontalWall) { //hit a horizontal wall
            newDy = -newDy;
        }
        if (!ballColorMatch(hitter) || this.isDeathRegion) {
            if (!this.isBorder && !this.isDeathRegion) {
                hitter.setColor(this.color);
            }
            this.notifyHit(hitter);
        }
        return new Velocity(newDx, newDy);
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

    /**
     * notify Arkanoid.Sprite.Arkanoid.Sprite that time has passed.
     */
    @Override
    public void timePassed() {
        //
    }
    /**.
     * add Spriteto the game
     * @param game Arkanoid.Game.Arkanoid.Game
     */
    @Override
    public void addToGame(Game game) {
        game.addCollidable(this);
        game.addSprite(this);
    }
    /**
     * @param ball ball object
     * @return true if this have the same color as ball, and false otherwise
     */
    public boolean ballColorMatch(Ball ball) {
        return this.color.getRed() == ball.getColor().getRed() && this.color.getGreen() == ball.getColor().getGreen()
                && this.color.getBlue() == ball.getColor().getBlue();
    }
    /**.
     * removes
     * @param game Arkanoid.Game.Arkanoid.Game object
     */
    public void removeFromGame(Game game) {
        game.removeCollidable(this);
        game.removeSprite(this);
    }
    /**.
     * notify all listeners
     * @param hitter ball hitter
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }
    /**
     * Add hl as a listener to hit events.
     *
     * @param hl hit listener
     */
    @Override
    public void addHitListener(HitListener hl) {
        if (this.hitListeners == null) {
            this.hitListeners = new ArrayList<>();
        }
        this.hitListeners.add(hl);
    }

    /**
     * Remove hl from the list of listeners to hit events.
     *
     * @param hl hit listener
     */
    @Override
    public void removeHitListener(HitListener hl) {
        if (!this.hitListeners.isEmpty()) {
            this.hitListeners.remove(hl);
        }
    }
}
