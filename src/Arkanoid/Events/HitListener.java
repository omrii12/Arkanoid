package Arkanoid.Events;

import Arkanoid.Sprite.Ball;
import Arkanoid.Sprite.Block;

/**
 * Arkanoid.GameLogic.HitListener interface.
 */
public interface HitListener {
    /**
     * This method is called whenever the beingHit object is hit.
     *  The hitter parameter is the Arkanoid.Sprite.Ball that's doing the hitting.
     * @param beingHit block being hit
     * @param hitter ball hitter
     */
    void hitEvent(Block beingHit, Ball hitter);
}
