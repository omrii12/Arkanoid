package Arkanoid.Collections;

import Arkanoid.Sprite.Sprite;
import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * Arkanoid.Sprite.Arkanoid.Sprite Collection class.
 */
public class SpriteCollection {
    private List<Sprite> spritesList;
    /**.
     * init sprite collection
     */
    public SpriteCollection() {
        this.spritesList = new ArrayList<>();
    }
    /**
     * @param s Arkanoid.Sprite.Arkanoid.Sprite s
     */
    public void addSprite(Sprite s) {
        if (spritesList == null) {
            spritesList = new ArrayList<>();
        }
        spritesList.add(s);
    }
    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.spritesList);
        if (spritesCopy != null) {
            for (Sprite s : spritesCopy) {
                s.timePassed();
            }
        }
    }
    /**
     * call drawOn(d) on all sprites.
     * @param d drawSurface
     */
    public void drawAllOn(DrawSurface d) {
        if (spritesList != null) {
            for (Sprite s : spritesList) {
                s.drawOn(d);
            }
        }
    }
    /**
     * @return this sprite list
     */
    public List<Sprite> getSpritesList() {
        return spritesList;
    }
}