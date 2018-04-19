package gfx;

import core.Renderable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;

public class MenuItem implements Renderable {
    public static final int FONT_SIZE = 8;
    private boolean hover = false;
    private Runnable action;
    private String text;
    private Point position;
    private BufferedImage listHover;

    public MenuItem(Point position, String text, Runnable action) {
        this.action = action;
        this.text = text;
        this.position = position;
        listHover = Assets.getInstance().getSprite(Sprite.MENU_LIST);
    }

    @Override
    public void render(Graphics2D g) {
        if(hover) {
            Drawer.drawImage(g, listHover, (int)position.getX() - 80 , (int)position.getY() - FONT_SIZE);
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.ORANGE);
        }
        Drawer.changeFontSize(g, FONT_SIZE);
        Drawer.drawString(g, text, (int)position.getX(), (int)position.getY(), Drawer.Alignment.CENTER);
    }

    public void doAction() {
        action.run();
    }

    public boolean isHover() {
        return hover;
    }

    public void toggleHover() {
        hover = !hover;
    }


}
