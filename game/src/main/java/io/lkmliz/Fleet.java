package io.lkmliz;

class Fleet {

    private static final int MAX_ROWS = 10;
    private static final int MAX_COLS = 10;

    private final Ship[][] grid;
    private final boolean[][] compromised;

    public Fleet() {
        grid = new Ship[MAX_ROWS][MAX_COLS];
        compromised = new boolean[MAX_ROWS][MAX_COLS];
    }

    public void placeShipHorizontally(Ship ship, Coordinate coords) {
        validateShipCoords(ship, coords);

        for (int i = 0; i < ship.size(); i++) {
            if (isCompromised(coords.goRight(i))) {
                throw new CoordinateAlreadyOccupiedException();
            }
        }

        for (int i = coords.x(); i < coords.x() + ship.size(); i++) {
            grid[coords.y()][i] = ship;
            compromiseNearbyCoordinates(coords.goRight(i - coords.x()));
        }
    }

    public void placeShipVertically(Ship ship, Coordinate coords) {
        validateShipCoords(ship, coords);

        for (int i = 0; i < ship.size(); i++) {
            if (isCompromised(coords.goDown(i))) {
                throw new CoordinateAlreadyOccupiedException();
            }
        }

        for (int i = coords.y(); i < coords.y() + ship.size(); i++) {
            grid[i][coords.x()] = ship;
            compromiseNearbyCoordinates(coords.goDown(i - coords.y()));
        }
    }

    public Ship shipAt(Coordinate pos) {
        return grid[pos.y()][pos.x()];
    }

    private void validateShipCoords(Ship ship, Coordinate coords) {
        if (!isWithinBounds(coords) || !shipSizeFitsCoordinate(ship, coords)) {
            throw new OutOfFleetBoundsException();
        }

        if (shipAt(coords) != null) {
            throw new CoordinateAlreadyOccupiedException();
        }
    }

    private boolean isCompromised(Coordinate pos) {
        return compromised[pos.y()][pos.x()];
    }

    private boolean shipSizeFitsCoordinate(Ship ship, Coordinate pos) {
        return isWithinBounds(pos.goRight(ship.size()));
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
