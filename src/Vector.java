public class Vector {
    private Point start_point;
    private Point end_point;

    public Vector(Point start_point, Point end_point) {
        this.start_point = start_point;
        this.end_point = end_point;
    }

    public Vector() {
    }

    public Point getStart_point() {
        return start_point;
    }

    public void setStart_point(Point start_point) {
        this.start_point = start_point;
    }

    public Point getEnd_point() {
        return end_point;
    }

    public void setEnd_point(Point end_point) {
        this.end_point = end_point;
    }

    @Override
    public String toString() {
        return "Vector{" +
                "start_point=" + start_point.toString() +
                ", end_point=" + end_point.toString() +
                '}';
    }
}
