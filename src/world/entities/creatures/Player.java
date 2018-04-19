package world.entities.creatures;

import core.Game;
import gfx.Animation;
import gfx.Assets;
import gfx.Sprite;
import gfx.SpriteAnimation;
import input.KeyInput;
import states.GameStateManager;
import states.State;
import utils.Direction;
import world.World;
import world.entities.Entity;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class Player extends Entity {
    private static final float GRAVITY = 0.1f;
    private static final float JUMPING_POWER = 1.6f;
    private static final float VERTICAL_SPEED = 1.5f;
    private static final float CLIMBING_SPEED = 0.7f;
    private static final long FALL_IMG_DURATION = 1000;
    private static final long DURATION_BEFORE_CHANGING_STATE = 2000;

    private Direction direction = Direction.RIGHT;
    private boolean falling = false;
    private boolean jumping = false;
    private final Animation runningLeft;
    private final Animation runningRight;
    private final Animation climbing;
    private Entity climbingLadder = null;
    private Animation deathAnimation;
    private long fallImgTimer;
    private Long changeStateTimer = null;

    public Player(int x, int y, World world) {
        super(x, y, world);
        img = Assets.getInstance().getSprite(Sprite.PLAYER_IDLE_RIGHT);
        runningLeft = new Animation(2, assets.getSpriteAnimations(SpriteAnimation.PLAYER_RUNNING_LEFT));
        runningRight = new Animation(2, assets.getSpriteAnimations(SpriteAnimation.PLAYER_RUNNING_RIGHT));
        climbing = new Animation(3, assets.getSpriteAnimations(SpriteAnimation.PLAYER_CLIMBING));
        deathAnimation = new Animation(2, assets.getSpriteAnimations(SpriteAnimation.PLAYER_DEATH));
    }

    @Override
    public void update() {
        if(!isDestroyed()) {
            if(falling) {
                velY += GRAVITY;
            } else {
                velX = 0;
                if(!world.isPlayerInWinDest()) {
                    handleKeyInputs();
                } else {
                    setIdle();
                    world.destroyEnemies();
                }
            }

            checkCanvasArea();

            y += velY;
            x += velX;

            if(climbingLadder == null) {
                checkCollisions();
            }
        } else {
            if(fallImgTimer + FALL_IMG_DURATION < System.currentTimeMillis()) {
                world.destroyEnemies();
                if(!deathAnimation.isLastFrame()) {
                    deathAnimation.update();
                    img = deathAnimation.getCurrentFrame();
                } else {
                    if(changeStateTimer == null) {
                        changeStateTimer = System.currentTimeMillis();
                    } else {
                        if(changeStateTimer + DURATION_BEFORE_CHANGING_STATE < System.currentTimeMillis()) {
                            GameStateManager.getInstance().changeState(State.MENU);
                        }
                    }
                }
            }
        }
    }

    private void setIdle() {
        if(direction == Direction.LEFT) {
            img = assets.getSprite(Sprite.PLAYER_IDLE_LEFT);
        } else if(direction == Direction.RIGHT) {
            img = assets.getSprite(Sprite.PLAYER_IDLE_RIGHT);
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

        if(!collidingBeams.isEmpty()) {
            y = height - (int)getSize().getHeight();
            if(falling) {
                if(jumping) {
                    jumping = false;
                } else {
                    destroy();
                }
                falling = false;
            }
            velY = 0;
        } else {
            if(!falling) {
                falling = true;
            }
        }
    }

    private void handleKeyInputs() {
        if(KeyInput.PRESSED.contains(KeyEvent.VK_UP)) {
            climb(Direction.UP);
        } else if(KeyInput.PRESSED.contains(KeyEvent.VK_DOWN)) {
            climb(Direction.DOWN);
        }

        if(climbingLadder == null) {
            if(KeyInput.PRESSED.contains(KeyEvent.VK_LEFT)) {
                direction = Direction.LEFT;
                velX -= VERTICAL_SPEED;
                runningLeft.update();
                img = runningLeft.getCurrentFrame();
            } else if(KeyInput.PRESSED.contains(KeyEvent.VK_RIGHT)) {
                direction = Direction.RIGHT;
                velX += VERTICAL_SPEED;
                runningRight.update();
                img = runningRight.getCurrentFrame();
            } else {
                if(img != assets.getSprite(Sprite.PLAYER_STANDING)) {
                    setIdle();
                }
            }

            if(KeyInput.PRESSED.contains(KeyEvent.VK_SPACE)) {
                velX /= 1.5f;
                falling = true;
                jumping = true;
                velY -= JUMPING_POWER;
                if(direction == Direction.LEFT) {
                    img = assets.getSprite(Sprite.PLAYER_JUMP_LEFT);
                } else {
                    img = assets.getSprite(Sprite.PLAYER_JUMP_RIGHT);
                }
            }
        }
    }

    private void checkCanvasArea() {
        if(x + velX + getSize().getWidth() > Game.CANVAS_SIZE.getWidth()) {
            velX = (int)(Game.CANVAS_SIZE.getWidth() - x - getSize().getWidth());
            setIdle();
        }

        if(x + velX < 0) {
            velX = (int)(0 - x);
            setIdle();
        }
    }

    private void climb(Direction direction) {
        if(climbingLadder == null) {
            Rectangle collisionBox;
            if(direction == Direction.UP) {
                collisionBox = new Rectangle((int)x + (int)getSize().getWidth()/2, (int)y, 1, (int)getSize().getHeight());
            } else {
                collisionBox = new Rectangle((int)x + (int)getSize().getWidth()/2, (int)y + (int)getSize().getHeight() + 1, 1, 2);
            }
            Entity ladder = world.getCollidingLadder(collisionBox);
            if(ladder != null) {
                climbingLadder = ladder;
                climbingAnimation();
                centerToLadder();
            }
        } else {
            if(direction == Direction.UP) {
                y -= CLIMBING_SPEED;
                climbingAnimation();

                if(!this.intersects(climbingLadder)) {
                    img = assets.getSprite(Sprite.PLAYER_STANDING);
                    centerToLadder();
                    y = climbingLadder.getY() - (int)getSize().getHeight();
                    climbingLadder = null;
                }
            } else {
                y += CLIMBING_SPEED;
                climbingAnimation();

                Rectangle collisionBox = new Rectangle((int)x, (int)(y + getSize().getHeight()), (int)getSize().getWidth(), 2);
                if(!collisionBox.intersects(climbingLadder.getBounds())) {
                    img = assets.getSprite(Sprite.PLAYER_STANDING);
                    centerToLadder();
                    y = (int)(climbingLadder.getY() + climbingLadder.getSize().getHeight() - getSize().getHeight());
                    climbingLadder = null;
                }
            }
        }
    }

    private void climbingAnimation() {
        if(y < climbingLadder.getY() - 10) {
            img = assets.getSprite(Sprite.PLAYER_CLIMBING_UP2);
        } else if(y < climbingLadder.getY() - 6) {
            img = assets.getSprite(Sprite.PLAYER_CLIMBING_UP1);
        } else {
            climbing.update();
            img = climbing.getCurrentFrame();
        }
    }

    private void centerToLadder() {
        if(climbingLadder != null) {
            x = climbingLadder.getX() + (float)((climbingLadder.getSize().getWidth() - getSize().getWidth()) / 2);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        if(falling && !jumping) {
            if(direction == Direction.RIGHT) {
                img = assets.getSprite(Sprite.PLAYER_FALL_RIGHT);
            } else {
                img = assets.getSprite(Sprite.PLAYER_FALL_LEFT);
            }
        }
        fallImgTimer = System.currentTimeMillis();
    }
}