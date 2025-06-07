package com.battleship;

public class Board {

    private final Fleet fleet;

    public Board(Fleet fleet) {
        this.fleet = fleet;
    }

    public void placeShip(Ship ship, Coordinate coordinate) {
        fleet.placeShipHorizontally(ship, coordinate);
    }

    public Ship getShipAt(Coordinate coordinate) {
        return fleet.shipAt(coordinate);
    }
}
