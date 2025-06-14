package com.battleship.model;

import com.battleship.Coordinate;
import java.util.Arrays;
import java.util.UUID;

public class Fleet {

    private static final int MAX_ROWS = 10;
    private static final int MAX_COLS = 10;

    private final UUID id;

    private final Ship[][] ships;
    private final boolean[][] compromised;

    public Fleet() {
        this(UUID.randomUUID());
    }

    public Fleet(UUID fleetId) {
        id = fleetId;
        ships = new Ship[MAX_ROWS][MAX_COLS];
        compromised = new boolean[MAX_ROWS][MAX_COLS];
    }

    public UUID getId() {
        return id;
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

    public Ship getShipAt(Coordinate coords) {
        return getShip(coords);
    }

    public Strike shootShip(Coordinate coords) {
        if (!anyShipAt(coords)) {
            return new Miss();
        }

        Ship shotShip = shootShipAt(coords);

        shotShip.hit();

        return new Hit();
    }

    private boolean anyShipAt(Coordinate coords) {
        if (!isWithinBounds(coords)) {
            throw new OutOfFleetBoundsException();
        }

        return getShip(coords) != null;
    }

    private Ship shootShipAt(Coordinate coords) {
        Ship ship = getShip(coords);

        setShip(coords, null);

        return ship;
    }

    private void placeShipAlongPath(Ship ship, Coordinate[] path) {
        for (Coordinate coords : path) {
            if (isCompromised(coords)) {
                throw new CoordinateAlreadyOccupiedException();
            }
        }

        for (Coordinate coords : path) {
            setShip(coords, ship);
            setCompromised(coords);
            compromiseNearbyCoordinates(coords);
        }
    }

    private boolean canMakePath(Coordinate start, Coordinate end) {
        return !isWithinBounds(start) || !isWithinBounds(end);
    }

    private void compromiseNearbyCoordinates(Coordinate coords) {
        Arrays.stream(coords.getNearbyCoordinates())
            .filter(this::isWithinBounds)
            .forEach(this::setCompromised);
    }

    private boolean isWithinBounds(Coordinate pos) {
        return pos.row() <= MAX_ROWS && pos.column() <= MAX_COLS && !(pos.row() < 0 || pos.column() < 0);
    }

    private boolean isCompromised(Coordinate pos) {
        return compromised[pos.column()][pos.row()];
    }

    private void setCompromised(Coordinate c) {
        compromised[c.column()][c.row()] = true;
    }

    private void setShip(Coordinate coords, Ship ship) {
        ships[coords.row()][coords.column()] = ship;
    }

    private Ship getShip(Coordinate coords) {
        return ships[coords.row()][coords.column()];
    }
}
