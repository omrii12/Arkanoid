package Arkanoid.Game;
import Arkanoid.Collections.SpriteCollection;
import Arkanoid.Collections.GameEnvironment;
import Arkanoid.Events.BallRemover;
import Arkanoid.Events.BlockRemover;
import Arkanoid.Events.ScoreTrackingListener;
import Arkanoid.Geometry.Point;
import Arkanoid.Geometry.Rectangle;
import Arkanoid.Geometry.Velocity;
import Arkanoid.Sprite.Ball;
import Arkanoid.Sprite.Block;
import Arkanoid.Sprite.Sprite;
import Arkanoid.Sprite.Paddle;
import Arkanoid.Sprite.ScoreIndicator;
import Arkanoid.Utils.Configurations;
import Arkanoid.Utils.Counter;
import Arkanoid.Events.Collidable;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import biuoop.Sleeper;

import java.awt.Color;


/**
 * Class Arkanoid.Game.Arkanoid.Game.
 */
public class Game {
    private SpriteCollection sprites;
    private GameEnvironment environment;
    private Paddle paddle;
    private KeyboardSensor keyboard;
    private GUI gui;
    private Counter blockCounter;
    private Counter ballCounter;
    private Counter score;
    private ScoreIndicator scoreIndicator;
    /**
     * init Arkanoid.Game.Arkanoid.Game.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.gui = new GUI("Arkanoid", Configurations.SCREEN_WIDTH, Configurations.SCREEN_HEIGHT);
        this.keyboard = gui.getKeyboardSensor();
    }
    /**
     * @param c Arkanoid.GameLogic.Collidable
     */
    public void addCollidable(Collidable c) {
        if (environment == null) {
            environment = new GameEnvironment();
        }
        environment.addCollidable(c);
    }
    /**
     * @param s Arkanoid.Sprite.Arkanoid.Sprite s
     */
    public void addSprite(Sprite s) {
        if (sprites == null) {
            sprites = new SpriteCollection();
        }
        sprites.addSprite(s);
    }
    /**
     * Initialize a new game: create the Blocks and
     * Arkanoid.Sprite.Ball.Arkanoid.Sprite.Ball (and Arkanoid.Sprite.Paddle)
     * and add them to the game.
     */
    public void initialize() {
        this.paddle = new Paddle(
                new Rectangle(new Point(350, 560), 100, 20), keyboard, Color.ORANGE,
                new Velocity(5, 0));
        this.paddle.addToGame(this);

        //create counters
        Counter remainingBlocks = new Counter();
        Counter remainingBalls = new Counter();
        Counter scoreCounter = new Counter();
        this.blockCounter = remainingBlocks;
        this.ballCounter = remainingBalls;
        this.score = scoreCounter;

        //create listeners
        BlockRemover blockRemover = new BlockRemover(this, remainingBlocks);
        BallRemover ballRemover = new BallRemover(this, remainingBalls);
        ScoreTrackingListener scoreListener = new ScoreTrackingListener(scoreCounter);

        this.scoreIndicator = new ScoreIndicator(this.score);
        this.scoreIndicator.addToGame(this);
        //create balls
        Ball ball1 = new Ball(new Point(400, 500), 5, Color.BLACK);
        Ball ball2 = new Ball(new Point(420, 500), 5, Color.BLACK);
        Ball ball3 = new Ball(new Point(440, 500), 5, Color.BLACK);
        ball1.setVelocity(Velocity.fromAngleAndSpeed(315, 5));
        ball2.setVelocity(Velocity.fromAngleAndSpeed(45, 5));
        ball3.setVelocity(Velocity.fromAngleAndSpeed(60, 5));
        ball1.setGameEnvironment(this.environment);
        ball2.setGameEnvironment(this.environment);
        ball3.setGameEnvironment(this.environment);
        ball1.addToGame(this);
        ball2.addToGame(this);
        ball3.addToGame(this);
        remainingBalls.increase(3);
        //create border walls
        Block topWall = new Block(new Rectangle(new Point(0, 0),
                800, 20), Color.GRAY, true);
        Block leftWall = new Block(new Rectangle(new Point(0, 20),
                20, 580), Color.GRAY, true);
        Block rightWall = new Block(new Rectangle(new Point(780, 20),
                20, 580), Color.GRAY, true);
        //Block bottomWall = new Block(new Rectangle(new Point(0, 580), 800, 20), Color.GRAY, true);
        topWall.addToGame(this);
        leftWall.addToGame(this);
        rightWall.addToGame(this);
        //bottomWall.addToGame(this);

        //create blocks in rows
        Color[] colors = {Color.GRAY, Color.RED, Color.YELLOW, Color.BLUE, Color.PINK, Color.GREEN};
        int startY = 100;
        int blockWidth = 50;
        int blockHeight = 20;
        int numRows = colors.length;

        for (int i = 0; i < numRows; i++) {
            int numBlocksInRow = 10 - i;
            for (int j = 0; j < numBlocksInRow; j++) {
                Block block = new Block(new Rectangle(new Point(730 - j * blockWidth, startY + i * blockHeight),
                        blockWidth, blockHeight), colors[i]);
                block.addToGame(this);
                block.addHitListener(blockRemover);
                block.addHitListener(scoreListener);
                remainingBlocks.increase(1);
            }
        }

        //create death-region block
        Block deathRegion = new Block(new Rectangle(new Point(0, 580), 800, 20), Color.BLACK,
                true);
        deathRegion.setDeathRegion(true);
        deathRegion.addToGame(this);
        deathRegion.addHitListener(ballRemover);


    }


    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        boolean bonus = false;

        while (this.blockCounter.getValue() > 0 && this.ballCounter.getValue() > 0) {
            long startTime = System.currentTimeMillis();

            DrawSurface d = gui.getDrawSurface();
            this.sprites.drawAllOn(d);
            this.scoreIndicator.drawOn(d);
            gui.show(d);
            this.sprites.notifyAllTimePassed();
            if (this.blockCounter.getValue() == 0 && !bonus) {
                score.increase(100);
                bonus = true;
            }
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }
        }

        if (this.blockCounter.getValue() == 0) {
            System.out.println("You Win!\nYour score is: " + score.getValue());
        } else if (this.ballCounter.getValue() == 0) {
            System.out.println("Game Over.\nYour score is: " + score.getValue());
        }

        gui.close();
    }
    /**.
     * removes collidable from gameEnvironment
     * @param c collidable c
     */
    public void removeCollidable(Collidable c) {
        this.environment.getCollList().remove(c);
    }
    /**
     * @param s Arkanoid.Sprite.Arkanoid.Sprite s
     */
    public void removeSprite(Sprite s) {
        this.sprites.getSpritesList().remove(s);
    }



}