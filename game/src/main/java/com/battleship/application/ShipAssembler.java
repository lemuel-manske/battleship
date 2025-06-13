package com.battleship.application;

import com.battleship.ShipDto;
import com.battleship.model.Ship;

public class ShipAssembler {

    public static Ship assembleShip(ShipDto dto) {
        return switch (dto.shipType()) {
            case BATTLESHIP -> Ship.battleship();
            case CARRIER -> Ship.carrier();
            case DESTROYER -> Ship.destroyer();
            case PATROL_BOAT -> Ship.patrolBoat();
            case SUBMARINE -> Ship.submarine();
        };
    }
}
