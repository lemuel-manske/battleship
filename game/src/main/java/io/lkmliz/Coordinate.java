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

    public Coordinate goLeft(int by) {
        return new Coordinate(this.x - by, y);
    }

    public Coordinate goUp(int by) {
        return new Coordinate(x, this.y - by);
    }

    public Coordinate[] getNearbyCoordinates() {
        return new Coordinate[] {
            goRight(1), goDown(1),
            goLeft(1), goUp(1),
            goRight(1).goDown(1), goLeft(1).goUp(1),
            goRight(1).goUp(1), goLeft(1).goDown(1)
        };
    }
}
