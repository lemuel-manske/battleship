package com.battleship;

public class Board {

    private final Fleet fleet;

    public Board(Fleet fleet) {
        this.fleet = fleet;
    }

    public void placeShipHorizontally(Ship ship, Coordinate coordinate) {
        fleet.placeShipHorizontally(ship, coordinate);
    }

    public void placeShipVertically(Ship ship, Coordinate coordinate) {
        fleet.placeShipVertically(ship, coordinate);
    }

    public Ship getShipAt(Coordinate coordinate) {
        return fleet.getShipAt(coordinate);
    }

    public Strike attack(Coordinate coordinate) {
        if (!fleet.anyShipAt(coordinate)) {
            return new Miss();
        }

        Ship shotShip = fleet.shootShipAt(coordinate);

        shotShip.hit();

        return new Hit();
    }
}
