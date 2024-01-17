package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class Polychrome implements PixelFilter {
    private final int intervalLength;

    public Polychrome() {
        int numColors = Integer.parseInt(JOptionPane.showInputDialog("enter the number of colors"));
        intervalLength = 255/numColors;
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] image = img.getBWPixelGrid();
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[0].length; col++) {
                image[row][col] = calculateValue(image[row][col]);
            }
        }
        img.setPixels(image);
        return img;
    }

    public short calculateValue(short value) {
        short newValue = (short) (((value/intervalLength) * intervalLength) + (intervalLength/2));
        if (newValue == (intervalLength/2)) {
            return 0;
        } else if (newValue == (255 - (intervalLength/2))) {
            return 255;
        } else {
            return newValue;
        }
    }
}