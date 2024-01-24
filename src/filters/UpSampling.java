package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class UpSampling implements PixelFilter {
    private final int multiplier;

    public UpSampling() {
        multiplier = Integer.parseInt(JOptionPane.showInputDialog("enter a multiplier"));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] image = img.getBWPixelGrid();
        short[][] filtered = initializeFiltered(image);
        for (int row = 0; row < filtered.length; row++) {
            for (int col = 0; col < filtered[0].length; col++) {
                if (filtered[row][col] == 0) {
                    filtered[row][col] = getAverage(row/multiplier, col/multiplier, image);
                }
            }
        }
        img.setPixels(filtered);
        return img;
    }

    public short[][] initializeFiltered(short[][] image) {
        short[][] filtered = new short[image.length * multiplier][image[0].length * multiplier];
        for (int row = 0; row < image.length; row++) {
            for (int col = 0; col < image[0].length; col++) {
                filtered[row * multiplier][col * multiplier] = image[row][col];
            }
        }
        return filtered;
    }

    public short getAverage(int rowIndex, int colIndex, short[][] image) {
        short average = 0;
        int counter = 0;
        if (rowIndex > 0) {
            average += image[rowIndex - 1][colIndex];
            counter++;
        }
        if (rowIndex < image.length - 1) {
            average += image[rowIndex + 1][colIndex];
            counter++;
        }
        if (colIndex > 0) {
            average += image[rowIndex][colIndex - 1];
            counter++;
        }
        if (colIndex < image[0].length - 1) {
            average += image[rowIndex][colIndex + 1];
            counter++;
        }
        return (short) (average/counter);
    }
}