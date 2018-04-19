package core;

import input.KeyInput;
import states.GameStateManager;
import states.State;
import utils.ResourceReader;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {
    private static final String TITLE = "Donkey Kong";
    private static final int SCALE = 3;
    public static final Dimension CANVAS_SIZE = new Dimension(224, 245);

    private boolean running = false;
    private Thread thread;
    private Display display;
    private BufferStrategy bs;
    private GameStateManager gsm = GameStateManager.getInstance();

    public Game() {
    }

    private void init() {
        Dimension displaySize = new Dimension(CANVAS_SIZE);
        displaySize.height *= SCALE;
        displaySize.width *= SCALE;
        display = new Display(TITLE, displaySize);

        display.getCanvas().addKeyListener(new KeyInput());
        registerFonts();
        gsm.changeState(State.MENU);
    }

    @Override
    public void run() {
        final int UPDATES_PER_SECOND = 30;
        final double UPDATE_INTERVAL = 1_000 / UPDATES_PER_SECOND * 1_000_000;
        final int MAX_FRAMESKIP = 5;
        long timer = System.currentTimeMillis();;
        long nextUpdate = System.nanoTime();
        int frames = 0;
        int updates = 0;

        init();

        while (running) {
            int skippedFrames = 0;
            while (System.nanoTime() > nextUpdate && skippedFrames < MAX_FRAMESKIP) {
                update();
                updates++;
                nextUpdate += UPDATE_INTERVAL;
                skippedFrames++;
            }

            this.render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(String.format("UPS: %s, FPS: %s", updates, frames));
                frames = 0;
                updates = 0;
            }
        }
    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void render() {
        if(bs == null) {
            initBufferStrategy();
            return;
        }
        // Render single frame
        do {
            // The following loop ensures that the contents of the drawing buffer
            // are consistent in case the underlying surface was recreated
            do {
                // Get a new graphics context every time through the loop
                // to make sure the strategy is validated
                Graphics2D graphics = (Graphics2D) bs.getDrawGraphics();

                graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

                // Render to graphics

                graphics.scale(SCALE, SCALE);

                graphics.setColor(Color.BLACK);
                graphics.fillRect(0, 0, display.getCanvas().getPreferredSize().width, display.getCanvas().getPreferredSize().height);

                graphics.setFont(new Font("Press Start 2P", Font.PLAIN, 12));

                gsm.render(graphics);

                // Dispose the graphics
                graphics.dispose();

                // Repeat the rendering if the drawing buffer contents
                // were restored
            } while (bs.contentsRestored());

            // Display the buffer
            bs.show();

            // Repeat the rendering if the drawing buffer was lost
        } while (bs.contentsLost());
    }

    private void update() {
        gsm.update();
    }

    private void initBufferStrategy() {
        display.getCanvas().createBufferStrategy(2);
        bs = display.getCanvas().getBufferStrategy();
    }

    private void registerFonts() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(ResourceReader.loadFont("fonts/PressStart2P-Regular.ttf"));
    }
}
