package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class ColorMasking implements PixelFilter {
    private short targetRed;
    private short targetGreen;
    private short targetBlue;
    private int threshold;

    public ColorMasking() {
        targetRed = Short.parseShort(JOptionPane.showInputDialog("enter the red value of the target color"));
        targetGreen = Short.parseShort(JOptionPane.showInputDialog("enter the green value of the target color"));
        targetBlue = Short.parseShort(JOptionPane.showInputDialog("enter the blue value of the target color"));
        threshold = Integer.parseInt(JOptionPane.showInputDialog("choose a threshold"));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        for (int row = 0; row < red.length; row++) {
            for (int col = 0; col < red[0].length; col++) {
                if (getColorDistance(red[row][col], green[row][col], blue[row][col]) < threshold) {
                    red[row][col] = 255;
                    green[row][col] = 255;
                    blue[row][col] = 255;
                } else {
                    red[row][col] = 0;
                    green[row][col] = 0;
                    blue[row][col] = 0;
                }
            }
        }
        img.setColorChannels(red, green, blue);
        return img;
    }

    public double getColorDistance(short red, short green, short blue) {
        int redDistance = Math.abs(targetRed - red);
        int greenDistance = Math.abs(targetGreen - green);
        int blueDistance = Math.abs(targetBlue - blue);
        return Math.sqrt(Math.pow(redDistance, 2) + Math.pow(greenDistance, 2) + Math.pow(blueDistance, 2));
    }
}