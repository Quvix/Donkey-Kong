package states;

import world.World;

import java.awt.*;

public class Level1State implements GameState {
    private World world;

    public Level1State() {
        init();
    }

    @Override
    public void init() {
        world = new World("maps/level1.map");
    }

    @Override
    public void update() {
        world.update();
    }

    @Override
    public void render(Graphics2D g) {
        world.render(g);
    }
}
