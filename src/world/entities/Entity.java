package world.entities;

import core.Renderable;
import gfx.Assets;
import gfx.Drawer;
import world.World;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Entity implements Renderable {
    protected float x, y;
    protected float velX = 0;
    protected float velY = 0;
    protected World world;
    protected BufferedImage img = null;
    protected Assets assets = Assets.getInstance();
    private boolean destroyed = false;

    public Entity(int x, int y, World world) {
        this.x = x;
        this.y = y;
        this.world = world;
    }

    public abstract void update();

    @Override
    public void render(Graphics2D g) {
        if(img != null) {
            Drawer.drawImage(g, img, x, y);
        }
    }

    public Dimension getSize() {
        if(img != null) {
            return new Dimension(img.getWidth(), img.getHeight());
        }
        return new Dimension(0, 0);
    }

    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)getSize().getWidth(), (int)getSize().getHeight());
    }

    public boolean intersects(Rectangle rec) {
        return rec.intersects(this.getBounds());
    }

    public boolean intersects(Entity e) {
        return intersects(e.getBounds());
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void destroy() {
        destroyed = true;
    }
}
