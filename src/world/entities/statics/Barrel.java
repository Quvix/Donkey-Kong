package world.entities.statics;

import gfx.Sprite;
import world.World;
import world.entities.Entity;

public class Barrel extends Entity {

    public Barrel(int x, int y, World world) {
        super(x, y, world);
        img = assets.getSprite(Sprite.BARREL_STANDING);
    }

    @Override
    public void update() {

    }
}
