package gfx;

import utils.ResourceReader;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Assets {
    private static Assets instance = new Assets();

    private Map<Sprite, BufferedImage> sprites;
    private Map<SpriteAnimation, BufferedImage[]> spriteAnimations;

    private Assets() {
        init();
    }

    public static Assets getInstance() {
        return instance;
    }

    public void init() {
        sprites = new HashMap<>();
        spriteAnimations = new HashMap<>();
        loadSprites();
    }

    public void loadSprites() {
        sprites.put(Sprite.BEAM, ResourceReader.loadImage("sprites/beam.png"));
        sprites.put(Sprite.LADDER, ResourceReader.loadImage("sprites/ladder.png"));
        sprites.put(Sprite.PLAYER_IDLE_LEFT, ResourceReader.loadImage("sprites/player_idle_left.png"));
        sprites.put(Sprite.PLAYER_IDLE_RIGHT, ResourceReader.loadImage("sprites/player_idle_right.png"));
        sprites.put(Sprite.PLAYER_JUMP_LEFT, ResourceReader.loadImage("sprites/player_jump_left.png"));
        sprites.put(Sprite.PLAYER_JUMP_RIGHT, ResourceReader.loadImage("sprites/player_jump_right.png"));
        sprites.put(Sprite.PLAYER_STANDING, ResourceReader.loadImage("sprites/player_standing.png"));
        sprites.put(Sprite.PLAYER_CLIMBING_UP1, ResourceReader.loadImage("sprites/player_climbing3.png"));
        sprites.put(Sprite.PLAYER_CLIMBING_UP2, ResourceReader.loadImage("sprites/player_climbing4.png"));
        sprites.put(Sprite.LOGO, ResourceReader.loadImage("sprites/donkey_kong_logo.png"));
        sprites.put(Sprite.MENU_LIST, ResourceReader.loadImage("sprites/menu_list.png"));
        sprites.put(Sprite.PLAYER_FALL_LEFT, ResourceReader.loadImage("sprites/player_fall_left.png"));
        sprites.put(Sprite.PLAYER_FALL_RIGHT, ResourceReader.loadImage("sprites/player_fall_right.png"));
        sprites.put(Sprite.BARREL_STANDING, ResourceReader.loadImage("sprites/barrel_front_standing.png"));
        sprites.put(Sprite.DK_IDLE, ResourceReader.loadImage("sprites/dk_idle.png"));
        sprites.put(Sprite.DK_LEFT, ResourceReader.loadImage("sprites/dk_left.png"));
        sprites.put(Sprite.DK_RIGHT, ResourceReader.loadImage("sprites/dk_right.png"));
        sprites.put(Sprite.PRINCESS, ResourceReader.loadImage("sprites/princess.png"));

        spriteAnimations.put(SpriteAnimation.PLAYER_RUNNING_LEFT, new BufferedImage[]{
                ResourceReader.loadImage("sprites/player_running_left1.png"),
                ResourceReader.loadImage("sprites/player_running_left2.png")
        });
        spriteAnimations.put(SpriteAnimation.PLAYER_RUNNING_RIGHT, new BufferedImage[]{
                ResourceReader.loadImage("sprites/player_running_right1.png"),
                ResourceReader.loadImage("sprites/player_running_right2.png")
        });
        spriteAnimations.put(SpriteAnimation.PLAYER_CLIMBING, new BufferedImage[]{
                ResourceReader.loadImage("sprites/player_climbing1.png"),
                ResourceReader.loadImage("sprites/player_climbing2.png")
        });
        spriteAnimations.put(SpriteAnimation.BARREL_ROLLING, new BufferedImage[]{
                ResourceReader.loadImage("sprites/barrel_rolling1.png"),
                ResourceReader.loadImage("sprites/barrel_rolling2.png"),
                ResourceReader.loadImage("sprites/barrel_rolling3.png"),
                ResourceReader.loadImage("sprites/barrel_rolling4.png"),
        });
        spriteAnimations.put(SpriteAnimation.PLAYER_DEATH, new BufferedImage[]{
                ResourceReader.loadImage("sprites/player_death1.png"),
                ResourceReader.loadImage("sprites/player_death2.png"),
                ResourceReader.loadImage("sprites/player_death3.png"),
                ResourceReader.loadImage("sprites/player_death4.png"),
                ResourceReader.loadImage("sprites/player_death1.png"),
                ResourceReader.loadImage("sprites/player_death2.png"),
                ResourceReader.loadImage("sprites/player_death3.png"),
                ResourceReader.loadImage("sprites/player_death4.png"),
                ResourceReader.loadImage("sprites/player_death1.png"),
                ResourceReader.loadImage("sprites/player_death2.png"),
                ResourceReader.loadImage("sprites/player_death3.png"),
                ResourceReader.loadImage("sprites/player_death4.png"),
                ResourceReader.loadImage("sprites/player_death5.png"),
        });
        spriteAnimations.put(SpriteAnimation.DK_WIN, new BufferedImage[]{
                ResourceReader.loadImage("sprites/dk_win1.png"),
                ResourceReader.loadImage("sprites/dk_win2.png"),
        });
        spriteAnimations.put(SpriteAnimation.DK_CLIMBING, new BufferedImage[]{
                ResourceReader.loadImage("sprites/dk_climbing1.png"),
                ResourceReader.loadImage("sprites/dk_climbing2.png"),
        });
    }

    public BufferedImage getSprite(Sprite sprite) {
        return sprites.get(sprite);
    }

    public BufferedImage[] getSpriteAnimations(SpriteAnimation sprite) {
        return spriteAnimations.get(sprite);
    }
}
