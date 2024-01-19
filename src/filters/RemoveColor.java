package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class RemoveColor implements PixelFilter {
    private String color;

    public RemoveColor() {
        color = JOptionPane.showInputDialog("choose a color to remove");
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        if (color.equals("red")) {
            red = new short[img.getHeight()][img.getWidth()];
        } else if (color.equals("green")) {
            green = new short[img.getHeight()][img.getWidth()];
        } else {
            blue = new short[img.getHeight()][img.getWidth()];
        }
        img.setColorChannels(red, green, blue);
        return img;
    }
}