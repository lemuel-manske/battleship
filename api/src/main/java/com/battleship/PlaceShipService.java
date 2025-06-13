package com.battleship;

public interface PlaceShipService extends Service {

    void placeShip(ShipDto shipToPlace);
}
