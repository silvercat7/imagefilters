package filters;

import interfaces.PixelFilter;
import core.DImage;
import javax.swing.*;

public class Lighten implements PixelFilter {
    private final double percentage;

    public Lighten() {
        String response = JOptionPane.showInputDialog("enter a percentage");
        percentage = Double.parseDouble(response);
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] matrix = img.getBWPixelGrid();
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                short original = matrix[row][col];
                short filtered = (short) (original + ((255 - original) * percentage));
                matrix[row][col] = filtered;
            }
        }
        img.setPixels(matrix);
        return img;
    }
}