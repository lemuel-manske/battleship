package com.battleship;

import java.util.UUID;

public interface PlaceShip extends Service {

    void placeShip(UUID fleetId, Ship shipToPlace);
}
