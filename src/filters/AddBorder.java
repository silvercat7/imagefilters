package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class AddBorder implements PixelFilter {
    private final int borderWidth;
    private final short borderColor;

    public AddBorder() {
        String width = JOptionPane.showInputDialog("choose a border width");
        borderWidth = Integer.parseInt(width);
        String color = JOptionPane.showInputDialog("choose a border color");
        borderColor = Short.parseShort(color);
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] image = img.getBWPixelGrid();
        short[][] filtered = new short[image.length + (borderWidth * 2)][image[0].length + (borderWidth * 2)];
        for (int row = 0; row < filtered.length; row++) {
            for (int col = 0; col < filtered[0].length; col++) {
                filtered[row][col] = borderColor;
                if (col >= borderWidth) {
                    col += image[0].length;
                }
            }
            if (row >= borderWidth) {
                row += image.length;
            }
        }
        for (int row = borderWidth; row < image.length; row++) {
            for (int col = borderWidth; col < image[row].length; col++) {
                filtered[row][col] = image[row - borderWidth][col - borderWidth];
            }
        }
        img.setPixels(image);
        return img;
    }
}