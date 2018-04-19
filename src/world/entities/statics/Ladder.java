package world.entities.statics;

import gfx.Drawer;
import gfx.Sprite;
import world.World;
import world.entities.Entity;

import java.awt.*;

public class Ladder extends Entity {
    private int height;
    public Ladder(int x, int y, int height, World world) {
        super(x, y, world);
        this.y -= assets.getSprite(Sprite.BEAM).getHeight();
        img = assets.getSprite(Sprite.LADDER);
        this.height = height;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        for(int i = 0; i < height / img.getHeight(); i++) {
            Drawer.drawImage(g, img, x, y  + assets.getSprite(Sprite.BEAM).getHeight() + i * img.getHeight());
        }
        Drawer.drawImage(g, img, x, y  + assets.getSprite(Sprite.BEAM).getHeight() + height - height % img.getHeight(), img.getWidth(), height % img.getHeight());
    }

    @Override
    public Dimension getSize() {
        return new Dimension(img.getWidth(), height + assets.getSprite(Sprite.BEAM).getHeight());
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)getSize().getWidth(), (int)getSize().getHeight());
    }
}
