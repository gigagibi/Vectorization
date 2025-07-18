import java.util.ArrayList;

public class Point {
    private int XE, YE, R, G, B, zVertical;
    double nearAmount=0, borderAmount=0;
    private boolean isActive = true;
    private ArrayList<int[]> nearestPointsCoordinates;
    private ArrayList<int[]> nearestBorderPointsCoordinates;

    public Point(int XE, int YE) {
        this.XE = XE;
        this.YE = YE;
        nearestPointsCoordinates = new ArrayList<int[]>();
    }

    public ArrayList<int[]> getNearestPointsCoordinates() {
        return nearestPointsCoordinates;
    }

    public void addNearestPoint(int x, int y) {
        int[] coords = new int[2];
        coords[0] = x;
        coords[1] = y;
        this.nearestPointsCoordinates.add(coords);
    }

    public int getXE() {
        return XE;
    }

    public void setXE(int XE) {
        this.XE = XE;
    }

    public int getYE() {
        return YE;
    }

    public void setYE(int YE) {
        this.YE = YE;
    }

    public double getNearAmount() {
        return nearAmount;
    }

    public double getBorderNearAmount() {
        return borderAmount;
    }

    public void increaseNearAmount() {
        this.nearAmount++;
    }

    public void increaseNearAmount(double a) {
        this.nearAmount+=a;
    }

    public void increaseBorderNearAmount() {
        this.borderAmount++;
    }

    public void increaseBorderNearAmount(double a) {
        this.borderAmount+=a;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getzVertical() {
        return zVertical;
    }

    public void setzVertical(int zVertical) {
        this.zVertical = zVertical;
    }

    public int getR() {
        return R;
    }

    public int getG() {
        return G;
    }

    public int getB() {
        return B;
    }

    public void setRGB(int r, int g, int b) {
        R = r; B = b; G = g;
        zVertical = (r+b+g)/3;
    }

    @Override
    public String toString() {
        return "Point{" +
                "XE=" + XE +
                ", YE=" + YE +
                '}';
    }
}
