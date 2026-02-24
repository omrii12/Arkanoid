package Arkanoid.Events;

import Arkanoid.Game.Game;
import Arkanoid.Geometry.Point;
import Arkanoid.Geometry.Rectangle;
import Arkanoid.Geometry.Velocity;
import Arkanoid.Sprite.Ball;

/**
 * collidable interface.
 */
public interface Collidable {
    /**
     * @return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();


    /**
     * @param collisionPoint point of collision
     * @param currentVelocity current velocity
     * @return new velocity after the hit
     * @param b Arkanoid.Sprite.Ball b
     * // Notify the object that we collided with it at collisionPoint with
     *     // a given velocity.
     *     // The return is the new velocity expected after the hit (based on
     *     // the force the object inflicted on us).
     */
    Velocity hit(Ball b, Point collisionPoint, Velocity currentVelocity);
    /**.
     * add Arkanoid.GameLogic.Collidable/sprite to the game
     * @param game Arkanoid.Game.Arkanoid.Game
     */
    void addToGame(Game game);
}