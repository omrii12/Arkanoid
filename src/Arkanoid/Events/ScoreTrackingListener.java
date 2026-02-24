package Arkanoid.Events;

import Arkanoid.Sprite.Ball;
import Arkanoid.Sprite.Block;
import Arkanoid.Utils.Counter;

/**.
 * Arkanoid.GameLogic.ScoreTrackingListener class
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    /**
     * @param scoreCounter counter
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }
    /**
     * @param beingHit block being hit
     * @param hitter ball hitter.
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        this.currentScore.increase(5);
    }
}
