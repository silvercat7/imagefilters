package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class DownSampling implements PixelFilter {
    private final int divisor;

    public DownSampling() {
        divisor = 2;
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] image = img.getBWPixelGrid();
        short[][] filtered = new short[image.length/divisor][image[0].length/divisor];
        int rowCounter = 0;
        int colCounter = 0;
        for (int row = 0; row < filtered.length; row++) {
            for (int col = 0; col < filtered[0].length; col++) {
                filtered[row][col] = getAverage(row + (divisor * rowCounter), col + (divisor + colCounter), image);
                colCounter++;
            }
            rowCounter++;
        }
        img.setPixels(filtered);
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