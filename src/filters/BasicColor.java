package filters;

import interfaces.PixelFilter;
import core.DImage;

public class BasicColor implements PixelFilter {


    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        img.setColorChannels(red, green, blue);
        return img;
    }
}

