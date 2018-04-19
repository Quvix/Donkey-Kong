package gfx;

import java.awt.image.BufferedImage;

public class Animation {
    private int speed;
    private int timer;
    private int index;
    private BufferedImage[] frames;

    public Animation(int speed, BufferedImage[] frames) {
        this.speed = speed;
        this.frames = frames;
        if(frames.length == 0) {
            throw new IllegalArgumentException("Can't create animation without frames");
        }
    }

    public void init() {
        timer = 0;
        index = 0;
    }

    public void update() {
        timer++;
        if(timer > speed) {
            timer = 0;
            index++;
            if(index == frames.length) {
                index = 0;
            }
        }
    }

    public void updateBackward() {
        timer--;
        if(timer < 0) {
            timer = speed;
            index--;
            if(index < 0) {
                index = frames.length - 1;
            }
        }
    }

    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

    public boolean isLastFrame() {
        return index == frames.length - 1;
    }
}
