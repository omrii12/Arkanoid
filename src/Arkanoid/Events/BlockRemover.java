package Arkanoid.Events;

import Arkanoid.Game.Game;
import Arkanoid.Sprite.Ball;
import Arkanoid.Sprite.Block;
import Arkanoid.Utils.Counter;

/**
 * a Arkanoid.GameLogic.BlockRemover is in charge of removing blocks from the game, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;
    /**
     * @param game Arkanoid.Game.Arkanoid.Game game
     * @param remainingBlocks Arkanoid.Utils.Counter
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }
    /**
     * Blocks that are hit should be removed
     * from the game. Remember to remove this listener from the block
     * that is being removed from the game.
     * @param beingHit Arkanoid.Sprite.Block that was hit
     * @param hitter ball hitter
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(game);
        remainingBlocks.decrease(1);
    }
}