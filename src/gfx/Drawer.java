package gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Drawer {
    public enum Alignment {
        CENTER, LEADING, TRAILING
    }
    public static void drawString(Graphics2D g, String text, int x, int y, Alignment alignment) {
        int width = g.getFontMetrics().stringWidth(text);
        switch(alignment) {
            case CENTER:
                g.drawString(text, x - width / 2, y);
                break;
            case LEADING:
                g.drawString(text, x, y);
                break;
            case TRAILING:
                g.drawString(text, x - width, y);
                break;
        }
    }

    public static void drawImage(Graphics2D g, BufferedImage img, float x, float y) {
        Drawer.drawImage(g, img, x, y, img.getWidth(), img.getHeight());
    }

    public static void drawImage(Graphics2D g, BufferedImage img, float x, float y, int width, int height) {
        g.drawImage(img, (int)x, (int)y, width, height, null);
    }

    public static void changeFontSize(Graphics2D g, float fontSize) {
        Font currentFont = g.getFont();
        Font newFont = currentFont.deriveFont(fontSize);
        g.setFont(newFont);
    }
}
