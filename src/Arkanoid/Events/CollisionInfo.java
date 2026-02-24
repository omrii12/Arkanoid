package Arkanoid.Events;

import Arkanoid.Geometry.Point;

/**
 * collision info class.
 */
public class CollisionInfo {
    private Point collisionPoint;
    private Collidable collisionObj;

    /**
     * @param collisionPoint collision point
     * @param collisionObj collision object
     */
    public CollisionInfo(Point collisionPoint, Collidable collisionObj) {
        this.collisionPoint = new Point(collisionPoint.getX(), collisionPoint.getY());
        this.collisionObj = collisionObj;
    }
    /**
     * @return point of collision
     */
    public Point collisionPoint() {
        return new Point(collisionPoint.getX(), collisionPoint.getY());
    }
    /**
     * @return collidable object involve in the collision
     */
    public Collidable collisionObject() {
        return this.collisionObj;
    }
}