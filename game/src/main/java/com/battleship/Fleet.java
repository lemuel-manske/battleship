package com.battleship;

class Fleet {

    private static final int MAX_ROWS = 10;
    private static final int MAX_COLS = 10;

    private final Ship[][] ships;
    private final boolean[][] compromised;

    public Fleet() {
        ships = new Ship[MAX_ROWS][MAX_COLS];
        compromised = new boolean[MAX_ROWS][MAX_COLS];
    }

    public void placeShipHorizontally(Ship ship, Coordinate initialCoords) {
        Coordinate finalCoords = initialCoords.goRightBy(ship.size());

        if (canMakePath(initialCoords, finalCoords)) {
            throw new OutOfFleetBoundsException();
        }

        placeShipAlongPath(ship, initialCoords.goRightUntil(finalCoords));
    }

    public void placeShipVertically(Ship ship, Coordinate initialCoords) {
        Coordinate finalCoords = initialCoords.goDownBy(ship.size());

        if (canMakePath(initialCoords, finalCoords)) {
            throw new OutOfFleetBoundsException();
        }

        placeShipAlongPath(ship, initialCoords.goDownUntil(finalCoords));
    }

    private void placeShipAlongPath(Ship ship, Coordinate[] path) {
        for (Coordinate c : path) {
            if (isCompromised(c)) {
                throw new CoordinateAlreadyOccupiedException();
            }
        }

        for (Coordinate c : path) {
            ships[c.y()][c.x()] = ship;
            compromised[c.y()][c.x()] = true;
            compromiseNearbyCoordinates(c);
        }
    }

    public Ship shipAt(Coordinate pos) {
        return ships[pos.y()][pos.x()];
    }

    private boolean canMakePath(Coordinate start, Coordinate end) {
        return !isWithinBounds(start) || !isWithinBounds(end);
    }

    private boolean isCompromised(Coordinate pos) {
        return compromised[pos.y()][pos.x()];
    }

    private void compromiseNearbyCoordinates(Coordinate coords) {
        for (Coordinate c : coords.getNearbyCoordinates()) {
            if (isWithinBounds(c)) {
                compromised[c.y()][c.x()] = true;
            }
        }
    }

    private boolean isWithinBounds(Coordinate pos) {
        return pos.x() <= MAX_ROWS && pos.y() <= MAX_COLS && !(pos.x() < 0 || pos.y() < 0);
    }
}
