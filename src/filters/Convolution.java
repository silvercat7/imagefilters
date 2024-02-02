package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class Convolution implements PixelFilter {
    private double[][] kernel;
    private double kernelWeight;
    private boolean fancy;

    public Convolution() {
        fancy = false;
        String inputKernel = JOptionPane.showInputDialog("choose a kernel");
        if (inputKernel.equals("blur")) {
            kernel = new double[][]{{1, 1, 1}, {1, 1, 1}, {1, 1, 1}};
            kernelWeight = 9;
        } else if (inputKernel.equals("gaussian blur")) {
            kernel = new double[][]{{1, 2, 1}, {2, 4, 2}, {1, 2, 1}};
            kernelWeight = 16;
        } else if (inputKernel.equals("sharpen")) {
            kernel = new double[][]{{0, -1, 0}, {-1, 5, -1}, {0, -1, 0}};
            kernelWeight = 1;
        } else if (inputKernel.equals("horizontal lines")) {
            kernel = new double[][]{{-1, -1, -1}, {2, 2, 2}, {-1, -1, -1}};
            kernelWeight = 0;
        } else if (inputKernel.equals("vertical lines")) {
            kernel = new double[][]{{-1, 2, -1}, {-1, 2, -1}, {-1, 2, -1}};
            kernelWeight = 0;
        } else if (inputKernel.equals("forward diagonal")) {
            kernel = new double[][]{{-1, -1, 2}, {-1, 2, -1}, {2, -1, -1}};
            kernelWeight = 0;
        } else if (inputKernel.equals("backward diagonal")) {
            kernel = new double[][]{{2, -1, -1}, {-1, 2, -1}, {-1, -1, 2}};
            kernelWeight = 0;
        } else if (inputKernel.equals("simple edge detection")) {
            kernel = new double[][]{{-1, -1, -1}, {-1, 8, -1}, {-1, -1, -1}};
            kernelWeight = 0;
        } else if (inputKernel.equals("fancy edge detection")) {
            fancy = true;
        } else if (inputKernel.equals("emboss")) {
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
        fillBorder(color, newColor);
        for (int row = 0; row < color.length - 2; row++) {
            for (int col = 0; col < color[row].length - 2; col++) {
                if (!fancy) {
                    newColor[row + 1][col + 1] = computeOutput(color, row, col);
                } else {
                    newColor[row + 1][col + 1] = computeFancyOutput(color, row, col);
                }
            }
        }
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

    public short computeFancyOutput(short[][] image, int startingRow, int startingCol) {
        short output = threshold(sobelEdge(image, startingRow, startingCol));
        for (int i = 0; i < 4; i++) {
            output = thin(image, startingRow, startingCol);
            kernel = rotateKernel();
        }
        return output;
    }

    public short sobelEdge(short[][] image, int startingRow, int startingCol) {
        kernel = new double[][]{{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        short x = computeOutput(image, startingRow, startingCol);
        kernel = new double[][]{{1, 2, 1}, {0, 0, 0}, {-1, -2, -1}};
        short y = computeOutput(image, startingRow, startingCol);
        return (short) Math.sqrt((Math.pow(x, 2) + Math.pow(y, 2)));
    }

    public short threshold(short input) {
        if (input < 127) {
            return 0;
        } else {
            return 255;
        }
    }

    public short thin(short[][] image, int startingRow, int startingCol) {
        short output = 0;
        kernel = new double[][] {{0, 0, 0}, {-1, 255, -1}, {255, 255, 255}};
        if (doesNotMatch(image, startingRow, startingCol)) {
            output = 255;
        }
        kernel = new double[][] {{-1, 0, 0}, {255, 255, 0}, {-1, 255, -1}};
        if (doesNotMatch(image, startingRow, startingCol)) {
            output = 255;
        }
        return output;
    }

    public boolean doesNotMatch(short[][] image, int startingRow, int startingCol) {
        for (int row = 0; row <= 2; row++) {
            for (int col = 0; col <= 2; col++) {
                if (kernel[row][col] != -1) {
                    if (image[startingRow + row][startingCol + col] != kernel[row][col]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public double[][] rotateKernel() {
        double[][] newKernel = new double[kernel.length][kernel[0].length];
        int newRow = 0;
        for (int col = kernel[0].length - 1; col >= 0; col--) {
            for (int row = 0; row < kernel.length; row++) {
                newKernel[newRow][row] = kernel[row][col];
            }
            newRow++;
        }
        return newKernel;
    }

    public void fillBorder(short[][] color, short[][] newColor) {
        for (int row = 0; row < color.length; row++) {
            newColor[row][0] = color[row][0];
            newColor[row][color[row].length - 1] = color[row][color[row].length - 1];
        }
        for (int col = 0; col < color[0].length; col++) {
            newColor[0][col] = color[0][col];
            newColor[color.length - 1][col] = color[color.length - 1][col];
        }
    }
}