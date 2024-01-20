package filters;

import core.DImage;
import interfaces.Interactive;
import interfaces.PixelFilter;

import javax.swing.*;

public class ColorMasking implements PixelFilter, Interactive {
    private short targetRed;
    private short targetGreen;
    private short targetBlue;
    private final double targetHue = convertToHue(targetRed, targetGreen, targetBlue);
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
                double distance = Math.abs(targetHue - convertToHue(red[row][col], green[row][col], blue[row][col]));
                if (distance < threshold) {
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

    public double convertToHue(short red, short green, short blue) {
        double r = red/255.0;
        double g = green/255.0;
        double b = blue/255.0;
        double max = Math.max(r, (Math.max(g, b)));
        double min = Math.min(r, Math.min(g, b));
        double hue;
        if (max == red) {
            hue = (g - b)/(max - min);
        } else if (max == green) {
            hue = 2.0 + (b - r)/(max - min);
        } else {
            hue = 4.0 + (r - g)/(max - min);
        }
        hue *= 60;
        if (hue < 0) {
            hue += 360;
        }
        return hue;
    }

    public double getColorDistance(short red, short green, short blue) {
        int redDistance = Math.abs(targetRed - red);
        int greenDistance = Math.abs(targetGreen - green);
        int blueDistance = Math.abs(targetBlue - blue);
        return Math.sqrt(Math.pow(redDistance, 2) + Math.pow(greenDistance, 2) + Math.pow(blueDistance, 2));
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        targetRed = red[mouseY][mouseX];
        targetGreen = green[mouseY][mouseX];
        targetBlue = blue[mouseY][mouseX];
    }

    @Override
    public void keyPressed(char key) {
        if (key == '+') {
            if (threshold <= 117) {
                threshold += 10;
            }
        }
        if (key == '-') {
            if (threshold >= 10) {
                threshold -= 10;
            }
        }
    }
}