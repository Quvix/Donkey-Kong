package states;

import core.Renderable;

public interface GameState extends Renderable {
    void init();
    void update();
}
