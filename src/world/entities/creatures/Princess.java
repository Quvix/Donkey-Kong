package world.entities.creatures;

import gfx.Sprite;
import world.World;
import world.entities.Entity;

public class Princess extends Entity {

    public Princess(int x, int y, World world) {
        super(x, y, world);
        img = assets.getSprite(Sprite.PRINCESS);
    }

    @Override
    public void update() {
        if(world.isPlayerInWinDest()) {
            img = null;
        }
    }
}
