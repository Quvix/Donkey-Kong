package states;

import core.Game;
import gfx.Assets;
import gfx.Drawer;
import gfx.Menu;
import gfx.Sprite;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MenuState implements GameState {
    private Menu menu;
    private BufferedImage logo;

    public MenuState() {
        init();
        logo = Assets.getInstance().getSprite(Sprite.LOGO);
    }

    @Override
    public void init() {
        menu = new Menu(110);
    }

    @Override
    public void update() {
        menu.update();
    }

    @Override
    public void render(Graphics2D g) {
        Drawer.drawImage(g, logo , 0, 10);
        menu.render(g);
        g.setColor(Color.WHITE);
        Drawer.drawString(g, "© 2017 JAKUB VITASEK", (int)Game.CANVAS_SIZE.getWidth() / 2, 210, Drawer.Alignment.CENTER);
        Drawer.drawString(g, "MADE IN CZECH REPUBLIC", (int)Game.CANVAS_SIZE.getWidth() / 2, 220, Drawer.Alignment.CENTER);
    }
}
