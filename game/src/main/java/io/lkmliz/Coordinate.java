package io.lkmliz;

public class Coordinate {

    private final int x;
    private final int y;

    private Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coordinate valueOf(int x, int y) {
        return new Coordinate(x, y);
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public Coordinate goRight(int by) {
        return new Coordinate(this.x + by, y);
    }

    public Coordinate goDown(int by) {
        return new Coordinate(x, this.y + by);
    }
}
