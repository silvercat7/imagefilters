package filters;

import interfaces.Drawable;
import interfaces.PixelFilter;
import core.DImage;
import processing.core.PApplet;

public class Drawing implements PixelFilter, Drawable {

    @Override
    public DImage processImage(DImage img) {
        return img;
    }

    @Override
    public void drawOverlay(PApplet window, DImage original, DImage filtered) {
        window.fill(255, 0, 0);
        window.ellipse(original.getWidth(), original.getHeight(), 10, 10);

        window.fill(0, 255, 0);
        window.ellipse(0, 0, 10, 10);
    }
}

