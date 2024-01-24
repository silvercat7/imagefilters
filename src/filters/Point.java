package filters;

public class Point {
    private int row;
    private int col;
    private short red;
    private short green;
    private short blue;

    public Point(short r, short g, short b) {
        red = r;
        green = g;
        blue = b;
    }

    public Point(int row, int col, short r, short g, short b) {
        this.row = row;
        this.col = col;
        red = r;
        green = g;
        blue = b;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public short getRed() {
        return red;
    }

    public short getGreen() {
        return green;
    }

    public short getBlue() {
        return blue;
    }

    public double getDistanceTo(short r, short g, short b) {
        return Math.sqrt(Math.pow(Math.abs(red - r), 2) + Math.pow(Math.abs(green - g), 2) + Math.pow(Math.abs(blue - b), 2));
    }
}