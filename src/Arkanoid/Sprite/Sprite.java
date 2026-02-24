package Arkanoid.Sprite;

import Arkanoid.Game.Game;
import biuoop.DrawSurface;
/**
 * Arkanoid.Sprite.Arkanoid.Sprite interface.
 */
public interface Sprite {
    /**
     * @param d drawSurface
     */
    void drawOn(DrawSurface d);
    /**
     * notify the sprite that time has passed.
     */
    void timePassed();
    /**.
     * add Arkanoid.GameLogic.Collidable/sprite to the game
     * @param game Arkanoid.Game.Arkanoid.Game
     */
    void addToGame(Game game);
}