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
        for (int row = 0; row < color.length - 2; row++) {
            for (int col = 0; col < color[0].length - 2; col++) {
                color[row + 1][col + 1] = computeOutput(color, row, col);
            }
        }
        return color;
    }

    public short computeOutput(short[][] image, int startingRow, int startingCol) {
        short output = 0;
        for (int row = startingRow; row <= startingRow + 2; row++) {
            for (int col = startingCol; col <= startingCol + 2; col++) {
                double weight = kernel[row - startingRow][col - startingCol];
                short pixel = image[row][col];
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
}