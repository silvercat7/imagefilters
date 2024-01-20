package filters;

import interfaces.PixelFilter;
import core.DImage;

public class DoNothing implements PixelFilter {
    @Override
    public DImage processImage(DImage img) {
        return img;
    }
}