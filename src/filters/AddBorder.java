package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;

public class AddBorder implements PixelFilter {
    private final int borderWidth;
    private final short borderColor;

    public AddBorder() {
        borderWidth = Integer.parseInt(JOptionPane.showInputDialog("choose a border width"));
        borderColor = Short.parseShort(JOptionPane.showInputDialog("choose a border color"));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] image = img.getBWPixelGrid();
        short[][] filtered = new short[image.length + (borderWidth * 2)][image[0].length + (borderWidth * 2)];
        for (int row = 0; row < filtered.length; row++) {
            for (int col = 0; col < filtered[0].length; col++) {
                filtered[row][col] = borderColor;
            }
        }
        for (int row = borderWidth + 1; row < filtered.length - borderWidth - 1; row++) {
            for (int col = borderWidth + 1; col < filtered[0].length - borderWidth - 1; col++) {
                filtered[row][col] = image[row - borderWidth][col - borderWidth];
            }
        }
        img.setPixels(filtered);
        return img;
    }
}