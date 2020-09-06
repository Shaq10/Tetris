public class Axis {
    public int x; //x-axis
    public int y; //y-axis

    public Axis(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "["+x+","+y+"]";
    }
}
