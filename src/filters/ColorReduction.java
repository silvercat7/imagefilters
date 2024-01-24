package filters;

import core.DImage;
import interfaces.PixelFilter;

import javax.swing.*;
import java.util.ArrayList;

public class ColorReduction implements PixelFilter {
    private final int k;

    public ColorReduction() {
        k = Integer.parseInt(JOptionPane.showInputDialog("enter the number of colors"));
    }

    @Override
    public DImage processImage(DImage img) {
        short[][] red = img.getRedChannel();
        short[][] green = img.getGreenChannel();
        short[][] blue = img.getBlueChannel();
        ArrayList<Cluster> clusters = initializeClusters();
        ArrayList<Point> points = initializePoints(red, green, blue);
        for (int i = 0; i < 10; i++) {
            clearPoints(clusters);
            assignPoints(clusters, points);
            calculateCenters(clusters);
        }
        ArrayList<short[][]> rgb = reduceColors(clusters, red, green, blue);
        img.setColorChannels(rgb.get(0), rgb.get(1), rgb.get(2));
        return img;
    }

    public ArrayList<Cluster> initializeClusters() {
        ArrayList<Cluster> clusters = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            short r = (short) (Math.random() * 256);
            short g = (short) (Math.random() * 256);
            short b = (short) (Math.random() * 256);
            clusters.add(new Cluster(r, g, b));
        }
        return clusters;
    }

    public ArrayList<Point> initializePoints(short[][] red, short[][] green, short[][] blue) {
        ArrayList<Point> points = new ArrayList<>();
        for (int row = 0; row < red.length; row++) {
            for (int col = 0; col < red[0].length; col++) {
                points.add(new Point(row, col, red[row][col], green[row][col], blue[row][col]));
            }
        }
        return points;
    }

    public void clearPoints(ArrayList<Cluster> clusters) {
        for (Cluster c : clusters) {
            c.clearPoints();
        }
    }

    public void assignPoints(ArrayList<Cluster> clusters, ArrayList<Point> points) {
        for (Point p : points) {
            findClosest(p, clusters).addPoint(p);
        }
    }

    public Cluster findClosest(Point p, ArrayList<Cluster> clusters) {
        double minDistance = Double.MAX_VALUE;
        Cluster closest = null;
        for (Cluster c : clusters) {
            double distance = p.getDistanceTo(c.getCenter().getRed(), c.getCenter().getGreen(), c.getCenter().getBlue());
            if (distance < minDistance) {
                minDistance = distance;
                closest = c;
            }
        }
        return closest;
    }

    public void calculateCenters(ArrayList<Cluster> clusters) {
        for (Cluster c : clusters) {
            short red = getAverage(c, "red");
            short green = getAverage(c, "green");
            short blue = getAverage(c, "blue");
            c.moveCenter(red, green, blue);
        }
    }

    public short getAverage(Cluster c, String color) {
        short total = 0;
        ArrayList<Point> points = c.getPoints();
        for (Point p : points) {
            total += getColor(p, color);
        }
        if (points.size() > 0) {
            return (short) (total/points.size());
        } else {
            return 0;
        }
    }

    public short getColor(Point p, String color) {
        if (color.equals("red")) {
            return p.getRed();
        } else if (color.equals("green")) {
            return p.getGreen();
        } else {
            return p.getBlue();
        }
    }

    public ArrayList<short[][]> reduceColors(ArrayList<Cluster> clusters, short[][] red, short[][] green, short[][] blue) {
        ArrayList<short[][]> rgb = new ArrayList<>();
        for (Cluster c : clusters) {
            ArrayList<Point> points = c.getPoints();
            for (Point p : points) {
                int row = p.getRow();
                int col = p.getCol();
                red[row][col] = c.getCenter().getRed();
                green[row][col] = c.getCenter().getGreen();
                blue[row][col] = c.getCenter().getBlue();
            }
        }
        rgb.add(red);
        rgb.add(green);
        rgb.add(blue);
        return rgb;
    }
}