package Arkanoid.Collections;

import Arkanoid.Events.Collidable;
import Arkanoid.Events.CollisionInfo;
import Arkanoid.Geometry.Line;
import Arkanoid.Geometry.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * game environment class.
 */
public class GameEnvironment {
    private List<Collidable> collList;
    /**.
     * init game environment
     */
    public GameEnvironment() {
        this.collList = new ArrayList<>();
    }
    /**
     * @param c collidable to add to the list
     */
    public void addCollidable(Collidable c) {
        if (collList == null) {
            this.collList = new ArrayList<>();
            this.collList.add(c);
        } else {
            this.collList.add(c);
        }
    }

    // Assume an object moving from line.start() to line.end().
    // If this object will not collide with any of the collidables
    // in this collection, return null. Else, return the information
    // about the closest collision that is going to occur.

    /**
     * @param trajectory line of the object trajectory
     * @return closest collision that is going to occur
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        Point closestPoint = null;
        Collidable closestCollidable = null;
        double minDistance = Integer.MAX_VALUE;
        //go over all collidables and check for potential collisions
        for (Collidable c : this.collList) {
            Point p = trajectory.closestIntersectionToStartOfLine(c.getCollisionRectangle());
            if (p != null) {
                double distance = trajectory.start().distance(p);
                if (distance < minDistance) {
                    minDistance = distance;
                    closestPoint = p;
                    closestCollidable = c;
                }
            }
        }
        if (closestPoint == null) { //no collisions
            return null;
        }
        return new CollisionInfo(closestPoint, closestCollidable);
    }
    /**
     * @return this.collist
     */
    public List<Collidable> getCollList() {
        return collList;
    }
}