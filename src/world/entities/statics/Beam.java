package world.entities.statics;

import gfx.Assets;
import gfx.Drawer;
import gfx.Sprite;
import world.World;
import world.entities.Entity;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Beam extends Entity {
    public Beam(int x, int y, World world) {
        super(x, y, world);
        img = Assets.getInstance().getSprite(Sprite.BEAM);
    }

    @Override
    public void update() {

    }
}
