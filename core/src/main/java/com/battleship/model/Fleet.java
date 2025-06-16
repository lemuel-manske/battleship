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

    public static Fleet create() {
        return new Fleet();
    }

    public static Fleet withId(UUID fleetId) {
        return new Fleet(fleetId);
    }

    public UUID getId() {
        return id;
    }

    public void placeShipHorizontally(Ship ship, Coordinate initialCoords) {
        Coordinate finalCoords = initialCoords.goRightBy(ship.size());

        Coordinate[] path = initialCoords.goRightUntil(finalCoords);

        placeShipAlongPath(ship, path);
    }

    public void placeShipVertically(Ship ship, Coordinate initialCoords) {
        Coordinate finalCoords = initialCoords.goDownBy(ship.size());

        Coordinate[] path = initialCoords.goDownUntil(finalCoords);

        placeShipAlongPath(ship, path);
    }

    public Ship getShipAt(Coordinate coords) {
        if (!isWithinBounds(coords)) {
            throw new OutOfFleetBoundsException();
        }

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
        if (!canMakePath(path)) {
            throw new OutOfFleetBoundsException();
        }

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

    private boolean canMakePath(Coordinate[] path) {
        Coordinate start = path[0];
        Coordinate end = path[path.length - 1];

        return isWithinBounds(start) && isWithinBounds(end);
    }

    private void compromiseNearbyCoordinates(Coordinate coords) {
        Arrays.stream(coords.getNearbyCoordinates())
            .filter(this::isWithinBounds)
            .forEach(this::setCompromised);
    }

    private boolean isWithinBounds(Coordinate pos) {
        return pos.row() < MAX_ROWS && pos.column() <= MAX_COLS && !(pos.row() < 0 || pos.column() < 0);
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

    private Fleet() {
        this(UUID.randomUUID());
    }

    private Fleet(UUID fleetId) {
        id = fleetId;
        ships = new Ship[MAX_ROWS][MAX_COLS];
        compromised = new boolean[MAX_ROWS][MAX_COLS];
    }
}
