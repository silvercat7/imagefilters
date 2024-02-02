package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class Convolution implements PixelFilter {
    private double[][] kernel;
    private double kernelWeight;

    public Convolution() {
        String response = JOptionPane.showInputDialog("choose a kernel");
        if (response.equals("blur")) {
            kernel = new double[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
            kernelWeight = 9;
        } else if (response.equals("gaussian blur")) {
            kernel = new double[][]{{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
            kernelWeight = 16;
        } else if (response.equals("sharpen")) {
            kernel = new double[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
            kernelWeight = 1;
        } else if (response.equals("horizontal lines")) {
            kernel = new double[][]{{-1, -1, -1}, {2, 2, 2}, {-1, -1, -1}};
            kernelWeight = 0;
        } else if (response.equals("vertical lines")) {
            kernel = new double[][]{{-1, 2, -1}, {-1, 2, -1}, {-1, 2, -1}};
            kernelWeight = 0;
        } else if (response.equals("forward diagonal")) {
            kernel = new double[][]{{-1, -1, 2}, {-1, 2, -1}, {2, -1, -1}};
            kernelWeight = 0;
        } else if (response.equals("backward diagonal")) {
            kernel = new double[][]{{2, -1, -1}, {-1, 2, -1}, {-1, -1, 2}};
            kernelWeight = 0;
        } else if (response.equals("edge detection")) {
            kernel = new double[][]{{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
            kernelWeight = 0;
        } else if (response.equals("emboss")) {
            kernel = new double[][]{{4, 0, 0}, {0, 0, 0}, {0, 0, -4}};
            kernelWeight = 0;
        }
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        img.setColorChannels(convolve(red), convolve(green), convolve(blue));
        return img;
    }

    public short[][] convolve(short[][] color) {
        short[][] newColor = new short[color.length][color[0].length];
        for (int row = 0; row < color.length - 2; row++) {
            for (int col = 0; col < color[row].length - 2; col++) {
                newColor[row + 1][col + 1] = computeOutput(color, row, col);
            }
        }
        fillBorder(color, newColor);
        return newColor;
    }

    public short computeOutput(short[][] image, int startingRow, int startingCol) {
        short output = 0;
        for (int row = 0; row <= 2; row++) {
            for (int col = 0; col <= 2; col++) {
                double weight = kernel[row][col];
                short pixel = image[startingRow + row][startingCol + col];
                output += (pixel * weight);
            }
        }
        if (kernelWeight > 0) {
            output /= kernelWeight;
        }
        if (output < 0) {
            output = 0;
        } else if (output > 255) {
            output = 255;
        }
        return output;
    }

    public void fillBorder(short[][] color, short[][] newColor) {
        for (int col = 0; col < color[0].length; col++) {
            newColor[0][col] = color[0][col];
            newColor[color.length - 1][col] = color[color.length - 1][col];
        }
        for (int row = 0; row < color.length; row++) {
            newColor[row][0] = color[row][0];
            newColor[row][color[row].length - 1] = color[row][color[row].length - 1];
        }
    }
}