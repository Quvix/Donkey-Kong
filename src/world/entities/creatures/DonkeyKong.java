package world.entities.creatures;

import gfx.Animation;
import gfx.Sprite;
import gfx.SpriteAnimation;
import states.GameStateManager;
import states.State;
import utils.Direction;
import world.World;
import world.entities.Entity;

import java.util.Random;

public class DonkeyKong extends Entity {
    private static long BARREL_SPAWN_TIME = 80;
    private static final float CLIMBING_SPEED = 0.7f;
    private static final long STATE_CHANGE_DURATION = 2000;
    private Long stateChangeTimer = null;
    private long counter = BARREL_SPAWN_TIME/2;
    private Animation winAnimation;
    private Animation climbAnimation;
    private boolean climbing = false;

    public DonkeyKong(int x, int y, World world) {
        super(x, y, world);
        img = assets.getSprite(Sprite.DK_IDLE);
        winAnimation = new Animation(5, assets.getSpriteAnimations(SpriteAnimation.DK_WIN));
        climbAnimation = new Animation(4, assets.getSpriteAnimations(SpriteAnimation.DK_CLIMBING));
    }

    @Override
    public void update() {
        if(!world.getPlayer().isDestroyed()) {
            if(!world.isPlayerInWinDest()) {
                counter++;
                if(counter == BARREL_SPAWN_TIME) {
                    img = assets.getSprite(Sprite.DK_RIGHT);
                    world.spawnBarrel((int)(x + getSize().getWidth()), (int)(y + getSize().getHeight() - assets.getSpriteAnimations(SpriteAnimation.BARREL_ROLLING)[0].getHeight()), Direction.RIGHT);
                    Random rnd = new Random();
                    BARREL_SPAWN_TIME = rnd.nextInt(40) + 80;
                    counter = 0;
                } else {
                    if(counter > BARREL_SPAWN_TIME - 15) {
                        img = assets.getSprite(Sprite.DK_LEFT);
                    } else if(counter > 15) {
                        img = assets.getSprite(Sprite.DK_IDLE);
                    }
                }
            } else {
                if(!climbing) {
                    y = 27;
                    x = 56;
                    climbing = true;
                }
                climbAnimation.update();
                img = climbAnimation.getCurrentFrame();
                y -= CLIMBING_SPEED;
                if(y < 0 - img.getHeight()) {
                    if(stateChangeTimer == null) {
                        stateChangeTimer = System.currentTimeMillis();
                    }
                    if(stateChangeTimer + STATE_CHANGE_DURATION < System.currentTimeMillis()) {
                        GameStateManager.getInstance().changeState(State.MENU);
                    }
                }
            }
        } else {
            winAnimation.update();
            img = winAnimation.getCurrentFrame();
        }
    }
}
