package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class ColorNoise implements PixelFilter {
    private double probability;

    public ColorNoise() {
        String response = JOptionPane.showInputDialog("choose a noise probability");
        probability = Double.parseDouble(response);
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        for (int row = 0; row < red.length; row++) {
            for (int col = 0; col < red[0].length; col++) {
                if (Math.random() < probability) {
                    red[row][col] = (short) (Math.random() * 256);
                    green[row][col] = (short) (Math.random() * 256);
                    blue[row][col] = (short) (Math.random() * 256);
                }
            }
        }
        img.setColorChannels(red, green, blue);
        return img;
    }
}
