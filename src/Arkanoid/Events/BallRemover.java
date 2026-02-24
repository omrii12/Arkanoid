package Arkanoid.Events;

import Arkanoid.Game.Game;
import Arkanoid.Sprite.Ball;
import Arkanoid.Sprite.Block;
import Arkanoid.Utils.Counter;

/**.
 * Arkanoid.Sprite.Ball remover
 */
public class BallRemover implements HitListener {

    private Game game;
    private Counter remainingBalls;
    /**
     * @param game Arkanoid.Game.Arkanoid.Game game
     * @param remainingBalls Arkanoid.Utils.Counter
     */
    public BallRemover(Game game, Counter remainingBalls) {
        this.game = game;
        this.remainingBalls = remainingBalls;
    }
    /**
     * Balls that hit the death region should be removed from the game.
     * @param beingHit Arkanoid.Sprite.Block that was hit
     * @param hitter ball hitter
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        remainingBalls.decrease(1);
    }
}
