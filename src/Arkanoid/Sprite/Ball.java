package Arkanoid.Sprite;

import Arkanoid.Collections.GameEnvironment;
import Arkanoid.Game.Game;
import Arkanoid.Events.Collidable;
import Arkanoid.Events.CollisionInfo;
import Arkanoid.Geometry.Line;
import Arkanoid.Geometry.Point;
import Arkanoid.Geometry.Rectangle;
import Arkanoid.Geometry.Velocity;
import Arkanoid.Utils.Configurations;
import biuoop.DrawSurface;
import java.awt.Color;

/**
 * Class Arkanoid.Sprite.Ball.Arkanoid.Sprite.Ball.
 */
public class Ball implements Sprite {
    private Point center;
    private int radius;
    private Color color;
    private Velocity velocity;
    private Line[] borders;
    private GameEnvironment gameEnvironment;

    /**.
     * constructor
     * @param center center point of the ball
     * @param r radius of ball
     * @param color color of the ball
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = new Point(center.getX(), center.getY());
        this.radius = r;
        this.color = color;
        this.velocity = null;
    }
    /**
     * @param lines all walls that the ball can touch
     */
    public void setBorders(Line[]lines) {
        this.borders = new Line[lines.length];
        for (int i = 0; i < lines.length; i++) {
            Line l = lines[i];
            borders[i] = new Line(l.start().getX(), l.start().getY(), l.end().getX(), l.end().getY());
        }
    }

    /**
     * @param g game environment
     */
    public void setGameEnvironment(GameEnvironment g) {
        this.gameEnvironment = g;
    }
    /**
     *
     * @return this.center.x
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /**
     *
     * @return this.center.y
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /**
     *
     * @return  this radius
     */
    public int getSize() {
        return this.radius;
    }

    /**
     *
     * @param size new radius
     */
    public void setSize(int size) {
        this.radius = size;
    }
    /**
     *
     * @return  this.color
     */
    public java.awt.Color getColor() {
        return this.color;
    }
    /**.
     *  draw the ball on the given DrawSurface
     * @param  surface surface DrawSurface object
     */
    @Override
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle((int) this.center.getX(), (int) this.center.getY(), this.radius);
    }

    /**
     *
     * @param v new velocity
     */
    public void setVelocity(Velocity v) {
        this.velocity = new Velocity(v.getDx(), v.getDy());
    }

    /**
     *
     * @param dx x-velocity
     * @param dy y-velocity
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);

    }

    /**
     *
     * @return this.velocity
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**.
     * moves the ball
     */
    public void moveOneStep() {
        Line trajectory = new Line(center.getX(), center.getY(),
                center.getX() + velocity.getDx(), center.getY() + velocity.getDy());
        CollisionInfo cInfo = this.gameEnvironment.getClosestCollision(trajectory);
        if (cInfo == null) {
            this.center = this.velocity.applyToPoint(this.center);
        } else {
            double threshold = Configurations.COMPARISON_THRESHOLD;
            double dx = this.velocity.getDx();
            double dy = this.velocity.getDy();
            double distance = Math.sqrt(dx * dx + dy * dy);
            double stepX = dx / distance * threshold;
            double stepY = dy / distance * threshold;
            this.center = new Point(cInfo.collisionPoint().getX() - stepX,
                    cInfo.collisionPoint().getY() - stepY);
            this.velocity = cInfo.collisionObject().hit(this, cInfo.collisionPoint(), this.velocity);
        }
        checkIfBallIsInPaddle();
    }
    /**
     * notify sprite that time has passed.
     */
    @Override
    public void timePassed() {
        moveOneStep();
    }
    /**.
     * add Arkanoid.GameLogic.Collidable/sprite to the game
     * @param game Arkanoid.Game.Arkanoid.Game
     */
    @Override
    public void addToGame(Game game) {
        game.addSprite(this);
    }
    /**.
     * checks if the ball is inside the paddle
     */
    private void checkIfBallIsInPaddle() {
        for (Collidable c : this.gameEnvironment.getCollList()) {
            if (c.getClass() == Paddle.class) {
                Rectangle rect = ((Paddle) c).getCollisionRectangle();
                double ballX = this.center.getX();
                double ballY = this.center.getY();
                double paddleLeft = rect.getUpperLeft().getX();
                double paddleRight = paddleLeft + rect.getWidth();
                double paddleTop = rect.getUpperLeft().getY();
                double paddleBottom = paddleTop + rect.getHeight();

                double minX = this.radius + 1;
                double maxX = Configurations.SCREEN_WIDTH - this.radius - 1;

                double wallThreshold = 25;
                boolean nearLeftWall = ballX < wallThreshold + this.radius;
                boolean nearRightWall = ballX > Configurations.SCREEN_WIDTH - wallThreshold - this.radius;

                if (ballY >= paddleTop - this.radius && ballY <= paddleBottom + this.radius) {
                    if (Math.abs(ballX - paddleLeft) <= this.radius + 1) {
                        double newX = Math.max(paddleLeft - this.radius - 1, minX);
                        this.center = new Point(newX, ballY);

                        if (nearLeftWall) {
                            this.velocity = new Velocity(3, this.velocity.getDy());
                        } else {
                            if (this.velocity.getDx() > -2) {
                                this.velocity = new Velocity(-2, this.velocity.getDy());
                            }
                        }
                        return;
                    } else if (Math.abs(ballX - paddleRight) <= this.radius + 1) {
                        double newX = Math.min(paddleRight + this.radius + 1, maxX);
                        this.center = new Point(newX, ballY);

                        if (nearRightWall) {
                            this.velocity = new Velocity(-3, this.velocity.getDy());
                        } else {
                            if (this.velocity.getDx() < 2) {
                                this.velocity = new Velocity(2, this.velocity.getDy());
                            }
                        }
                        return;
                    }
                }

                if (rect.contains(this.center)) {
                    double distToLeft = ballX - paddleLeft;
                    double distToRight = paddleRight - ballX;
                    double distToTop = ballY - paddleTop;
                    double distToBottom = paddleBottom - ballY;

                    double minHorizontal = Math.min(distToLeft, distToRight);
                    double minVertical = Math.min(distToTop, distToBottom);

                    if (minHorizontal < minVertical + Configurations.COMPARISON_THRESHOLD) {
                        if (distToLeft < distToRight) {
                            double newX = Math.max(paddleLeft - this.radius - 1, minX);
                            this.center = new Point(newX, ballY);

                            if (nearLeftWall) {
                                this.velocity = new Velocity(3, this.velocity.getDy());
                            } else {
                                this.velocity = new Velocity(-Math.max(Math.abs(this.velocity.getDx()), 2),
                                        this.velocity.getDy());
                            }
                        } else {
                            double newX = Math.min(paddleRight + this.radius + 1, maxX);
                            this.center = new Point(newX, ballY);

                            if (nearRightWall) {
                                this.velocity = new Velocity(-3, this.velocity.getDy());
                            } else {
                                this.velocity = new Velocity(Math.max(Math.abs(this.velocity.getDx()), 2),
                                        this.velocity.getDy());
                            }
                        }
                    } else {
                        if (distToTop < distToBottom) {
                            this.center = new Point(ballX, paddleTop - this.radius - 1);
                            this.velocity = new Velocity(this.velocity.getDx(), -Math.abs(this.velocity.getDy()));
                        } else {
                            this.center = new Point(ballX, paddleBottom + this.radius + 1);
                            this.velocity = new Velocity(this.velocity.getDx(), Math.abs(this.velocity.getDy()));
                        }
                    }
                    return;
                }
            }
        }
    }
    /**.
     * sets color
     * @param color color
     */
    public void setColor(Color color) {
        this.color = color;
    }
    /**
     * removes ball from game.
     * @param game game
     */
    public void removeFromGame(Game game) {
        game.removeSprite(this);
    }

}