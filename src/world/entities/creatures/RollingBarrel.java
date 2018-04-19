package world.entities.creatures;

import core.Game;
import gfx.Animation;
import gfx.SpriteAnimation;
import utils.Direction;
import world.World;
import world.entities.Entity;

import java.awt.*;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class RollingBarrel extends Entity {
    private static final float FALLING_SPEED = 2f;
    private static final float VERTICAL_SPEED = 2f;
    private static final int CHANCE_TO_FALL = 60;
    private Direction direction;
    private boolean falling = true;
    private Animation rollingAnimation = new Animation(3, assets.getSpriteAnimations(SpriteAnimation.BARREL_ROLLING));
    private Entity lastCheckedLadder = null;

    public RollingBarrel(int x, int y, World world, Direction direction) {
        super(x, y, world);
        this.direction = direction;
        if(direction == Direction.RIGHT) {
            velX = VERTICAL_SPEED;
        } else {
            velX = -VERTICAL_SPEED;
        }
    }

    @Override
    public void update() {
        checkCanvasArea();
        x += velX;
        y += velY;
        checkCollisions();
        if(velX > 0) {
            rollingAnimation.update();
        } else {
            rollingAnimation.updateBackward();
        }
        img = rollingAnimation.getCurrentFrame();
    }

    private void checkCanvasArea() {
        if(world.getLowestMapY() > (int)y + getSize().getHeight()) {
            if(x + velX + getSize().getWidth() > Game.CANVAS_SIZE.getWidth()) {
                x = (int)Game.CANVAS_SIZE.getWidth() - (int)getSize().getWidth() - abs((int)(Game.CANVAS_SIZE.getWidth() - x - getSize().getWidth()));
                velX = velX * -1;
                changeDirection();
            }

            if(x + velX < 0) {
                x = abs(0 - x);
                velX = velX * -1;
                changeDirection();
            }
        } else {
            if((x + getSize().getWidth() + velX < 0) || (x + velX > Game.CANVAS_SIZE.getWidth())) {
                destroy();
            }
        }
    }

    private void checkCollisions() {
        int height = (int)Game.CANVAS_SIZE.getHeight();
        Rectangle collisionBox = new Rectangle((int)x, (int)y + (int)getSize().getHeight(), (int)getSize().getWidth(), 2);
        List<Entity> collidingBeams = world.getCollidingBeams(collisionBox);
        for(Entity beam : collidingBeams) {
            if(beam.getY() < height) {
                height = (int)beam.getY();
            }
        }

        Rectangle ladderCollisionBox = new Rectangle((int)x + (int)getSize().getWidth()/2, (int)y + (int)getSize().getHeight() + 1, 1, 2);
        Entity ladder = world.getCollidingLadder(ladderCollisionBox);
        if(ladder != null && velX != 0) {
            Random rnd = new Random();
            if(!(rnd.nextInt(100) < CHANCE_TO_FALL) || ladder == lastCheckedLadder) {
                lastCheckedLadder = ladder;
                ladder = null;
            }
        }

        if(!collidingBeams.isEmpty() && ladder == null) {
            y = height - (int)getSize().getHeight();
            if(falling) {
                falling = false;
                if(velX == 0) {
                    changeDirection();
                }
                if(direction == Direction.RIGHT) {
                    velX = VERTICAL_SPEED;
                } else {
                    velX = -VERTICAL_SPEED;
                }
            }
            velY = 0;
        } else {
            if(!falling) {
                falling = true;
                velX /= 2;
                velY = FALLING_SPEED;

                if(ladder != null) {
                    x = ladder.getX() + (float)((ladder.getSize().getWidth() - getSize().getWidth()) / 2);
                    velX = 0;
                }
            }
        }

        if(world.getPlayer().intersects(this)) {
            world.getPlayer().destroy();
        }
    }

    private void changeDirection() {
        if(direction == Direction.RIGHT) {
            direction = Direction.LEFT;
        } else {
            direction = Direction.RIGHT;
        }
        //System.out.println("DIRECTION CHANGED TO " + direction.toString());
    }
}
