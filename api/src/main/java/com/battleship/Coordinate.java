package com.battleship;

public class Coordinate {

    private final int row;
    private final int column;

    private Coordinate(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Coordinate valueOf(int row, int column) {
        return new Coordinate(row, column);
    }

    public int row() {
        return row;
    }

    public int column() {
        return column;
    }

    public Coordinate goRightBy(int by) {
        return new Coordinate(row() + by, column());
    }

    public Coordinate goDownBy(int by) {
        return new Coordinate(row(), column() + by);
    }

    public Coordinate goLeftBy(int by) {
        return new Coordinate(row() - by, column());
    }

    public Coordinate goUpBy(int by) {
        return new Coordinate(row(), column() - by);
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
        int pathSize = finalCoords.row - row();

        Coordinate[] path = new Coordinate[pathSize];

        for (int i = 0; i < pathSize; i++) {
            path[i] = goRightBy(i);
        }

        return path;
    }

    public Coordinate[] goDownUntil(Coordinate finalCoords) {
        int pathSize = finalCoords.column - column();

        Coordinate[] path = new Coordinate[pathSize];

        for (int i = 0; i < pathSize; i++) {
            path[i] = goDownBy(i);
        }

        return path;
    }
}
