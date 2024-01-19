package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class SwapColors implements PixelFilter {
    private String color1;
    private String color2;

    public SwapColors() {
        color1 = JOptionPane.showInputDialog("choose the first color to swap");
        color2 = JOptionPane.showInputDialog("choose the second color to swap");
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        if (color1.equals("red") || color2.equals("red")) {
            if (color1.equals("green") || color2.equals("green")) {
                img.setColorChannels(green, red, blue);
            } else {
                img.setColorChannels(blue, green, red);
            }
        } else {
            img.setColorChannels(red, blue, green);
        }
        return img;
    }
}
