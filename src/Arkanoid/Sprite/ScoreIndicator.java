package Arkanoid.Sprite;

import Arkanoid.Game.Game;
import Arkanoid.Utils.Configurations;
import Arkanoid.Utils.Counter;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Score Indicator.
 */
public class ScoreIndicator implements Sprite {
    private Counter score;
    /**
     * @param score score counter
     */
    public ScoreIndicator(Counter score) {
        this.score = score;
    }
    /**
     * @param d drawSurface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.LIGHT_GRAY);
        d.fillRectangle(0, 0, 800, 20);
        d.setColor(Color.BLACK);
        String scoreText = "Score: " + score.getValue();
        int fontSize = 16;
        int textWidth = scoreText.length() * (fontSize / 2);
        int x = (Configurations.SCREEN_WIDTH - textWidth) / 2;
        d.drawText(x, 18, scoreText, fontSize);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {
        //
    }
    /**.
     * add Arkanoid.GameLogic.Collidable/sprite to the game
     * @param game Arkanoid.Game.Arkanoid.Game
     */
    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
    }
}
