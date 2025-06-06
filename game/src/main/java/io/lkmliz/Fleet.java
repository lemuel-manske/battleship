package io.lkmliz;

class Fleet {

    private static final int MAX_ROWS = 10;
    private static final int MAX_COLS = 10;

    private final Ship[][] fleet = new Ship[MAX_ROWS][MAX_COLS];

    private final boolean[][] compromised = new boolean[MAX_ROWS][MAX_COLS];

    public void placeShip(Ship aShip, Coordinate coords) {
        placeShip(aShip, coords, new HorizontalPlacementStrategy());
    }

    public void placeShipVertically(Ship aShip, Coordinate coords) {
        placeShip(aShip, coords, new VerticalPlacementStrategy());
    }

    public Ship shipAt(Coordinate pos) {
        return fleet[pos.y()][pos.x()];
    }

    private void placeShip(Ship ship, Coordinate coords, PlacementStrategy placementStrategy) {
        if (!isCoordinateWithinBounds(coords) || !shipSizeFitsCoordinate(ship, coords)) {
            throw new OutOfFleetBoundsException();
        }

        if (shipAt(coords) != null) {
            throw new CoordinateAlreadyOccupiedException();
        }

        placementStrategy.placeShip(ship, coords);
    }

    private boolean isCoordinateWithinBounds(Coordinate pos) {
        return pos.x() <= MAX_ROWS && pos.y() <= MAX_COLS;
    }

    private boolean shipSizeFitsCoordinate(Ship ship, Coordinate pos) {
        return ship.size() + pos.x() <= MAX_COLS;
    }

    private interface PlacementStrategy {

        void placeShip(Ship aShip, Coordinate coords);
    }

    private class VerticalPlacementStrategy implements PlacementStrategy {

        @Override
        public void placeShip(Ship ship, Coordinate coords) {
            for (int i = coords.y(); i < coords.y() + ship.size(); i += 1) {
                fleet[i][coords.x()] = ship;
            }
        }
    }

    private class HorizontalPlacementStrategy implements PlacementStrategy {

        @Override
        public void placeShip(Ship ship, Coordinate coords) {
            for (int i = coords.x(); i < coords.x() + ship.size(); i += 1) {
                fleet[coords.y()][i] = ship;
            }
        }
    }
}
