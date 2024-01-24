package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class TileImage implements PixelFilter {
    private int numAcross;
    private int numDown;

    public TileImage() {
        numAcross = Integer.parseInt(JOptionPane.showInputDialog("choose how many tiles across"));
        numDown = Integer.parseInt(JOptionPane.showInputDialog("choose how many tiles down"));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();

        img.setColorChannels(red, green, blue);
        return img;
    }
}