package filters;

import java.util.ArrayList;

public class Cluster {
    private Point center;
    private ArrayList<Point> points;

    public Cluster(short r, short g, short b) {
        center = new Point(r, g, b);
        points = new ArrayList<>();
    }

    public void addPoint(Point newPoint) {
        points.add(newPoint);
    }

    public void clearPoints() {
        points.clear();
    }

    public void moveCenter(short r, short g, short b) {
        center = new Point(r, g, b);
    }

    public Point getCenter() {
        return center;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }
}