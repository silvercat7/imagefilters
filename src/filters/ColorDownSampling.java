package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class ColorDownSampling implements PixelFilter {
    private final int divisor;

    public ColorDownSampling() {
        divisor = Integer.parseInt(JOptionPane.showInputDialog("enter a divisor"));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        short[][] filteredRed = new short[red.length/divisor][red[0].length/divisor];
        short[][] filteredGreen = new short[green.length/divisor][green[0].length/divisor];
        short[][] filteredBlue = new short[blue.length/divisor][blue[0].length/divisor];
        for (int row = 0; row < filteredRed.length; row++) {
            for (int col = 0; col < filteredRed[0].length; col++) {
                filteredRed[row][col] = getAverage(row * divisor, col * divisor, red);
                filteredGreen[row][col] = getAverage(row * divisor, col * divisor, green);
                filteredBlue[row][col] = getAverage(row * divisor, col * divisor, blue);
            }
        }
        img.setColorChannels(filteredRed, filteredGreen, filteredBlue);
        return img;
    }

    public short getAverage(int rowIndex, int colIndex, short[][] image) {
        short average = 0;
        for (int row = rowIndex; row < rowIndex + divisor && row < image.length; row++) {
            for (int col = colIndex; col < colIndex + divisor && col < image[0].length; col++) {
                average += image[row][col];
            }
        }
        return (short) (average/(divisor * divisor));
    }
}