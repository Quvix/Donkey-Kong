package gfx;

import core.Game;
import core.Renderable;
import input.KeyInput;
import states.GameStateManager;
import states.State;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;


public class Menu implements Renderable {
    private static final int ITEM_GAP = 10;
    private List<MenuItem> items = new LinkedList<>();
    private int y;
    private int currItemPosition = 0;
    private boolean btnPressed = false;
    private GameStateManager gsm = GameStateManager.getInstance();;

    public Menu(int y) {
        this.y = y;
        addItem("1 PLAYER GAME A", () ->
            gsm.changeStateAndInit(State.LEVEL1)
        );
        addItem("1 PLAYER GAME B", () -> {
            // TODO
        });
        addItem("2 PLAYER GAME A", () -> {
            // TODO
        });
        addItem("2 PLAYER GAME B", () -> {
            // TODO
        });
        items.get(currItemPosition).toggleHover();
    }

    public void update() {
        if(KeyInput.PRESSED.contains(KeyEvent.VK_UP)) {
            if(!btnPressed) {
                moveUp();
            }
            btnPressed = true;
        } else if(KeyInput.PRESSED.contains(KeyEvent.VK_DOWN)) {
            if(!btnPressed) {
                moveDown();
            }
            btnPressed = true;
        } else if(KeyInput.PRESSED.contains(KeyEvent.VK_ENTER)) {
            if(!btnPressed) {
                items.get(currItemPosition).doAction();
            }
            btnPressed = true;
        } else {
            btnPressed = false;
        }
    }

    private void moveUp() {
        items.get(currItemPosition).toggleHover();
        currItemPosition--;
        if(currItemPosition < 0) {
            currItemPosition = items.size() - 1;
        }
        items.get(currItemPosition).toggleHover();
    }

    private void moveDown() {
        items.get(currItemPosition).toggleHover();
        currItemPosition++;
        if(currItemPosition == items.size()) {
            currItemPosition = 0;
        }
        items.get(currItemPosition).toggleHover();
    }

    @Override
    public void render(Graphics2D g) {
        for(MenuItem item : items) {
            item.render(g);
        }
    }

    private void addItem(String text, Runnable func) {
        int height = y + MenuItem.FONT_SIZE + ((ITEM_GAP + MenuItem.FONT_SIZE) * items.size());
        items.add(new MenuItem(new Point((int)Game.CANVAS_SIZE.getWidth() / 2, height), text, func));
    }
}
