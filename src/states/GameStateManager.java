package states;

import core.Renderable;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class GameStateManager implements Renderable {
    private static GameStateManager INSTANCE = new GameStateManager();
    private GameState currentState = null;
    private Map<State, GameState> states = new HashMap<>();

    private GameStateManager() {
    }

    public static GameStateManager getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new GameStateManager();
        }
        return INSTANCE;
    }

    public GameState changeState(State state) {
        return currentState = states.computeIfAbsent(state, (value)-> {
            switch(state) {
                case LEVEL1:
                    return new Level1State();
                case MENU:
                    return new MenuState();
                default:
                    throw new RuntimeException("State not supported!");
            }
        });
    }

    public void changeStateAndInit(State state) {
        changeState(state);
        states.get(state).init();
    }

    @Override
    public void render(Graphics2D g) {
        currentState.render(g);
    }

    public void update() {
        currentState.update();
    }
}
