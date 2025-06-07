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

    public Coordinate goRightBy(int by) {
        return new Coordinate(this.x + by, y);
    }

    public Coordinate goDownBy(int by) {
        return new Coordinate(x, this.y + by);
    }

    public Coordinate goLeftBy(int by) {
        return new Coordinate(this.x - by, y);
    }

    public Coordinate goUpBy(int by) {
        return new Coordinate(x, this.y - by);
    }

    public Coordinate[] getNearbyCoordinates() {
        return new Coordinate[] {
            goRightBy(1), goDownBy(1),
            goLeftBy(1), goUpBy(1),
            goRightBy(1).goDownBy(1), goLeftBy(1).goUpBy(1),
            goRightBy(1).goUpBy(1), goLeftBy(1).goDownBy(1)
        };
    }

    public Coordinate[] goRightUntil(Coordinate finalCoords) {
        Coordinate[] path = new Coordinate[finalCoords.x - this.x + 1];

        for (int i = 0; i <= finalCoords.x - this.x; i++) {
            path[i] = goRightBy(i);
        }

        return path;
    }

    public Coordinate[] goDownUntil(Coordinate finalCoords) {
        Coordinate[] path = new Coordinate[finalCoords.y - this.y + 1];

        for (int i = 0; i <= finalCoords.y - this.y; i++) {
            path[i] = goDownBy(i);
        }

        return path;
    }
}
