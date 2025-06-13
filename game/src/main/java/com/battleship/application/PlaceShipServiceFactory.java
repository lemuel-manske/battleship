package com.battleship.application;

import com.battleship.PlaceServiceShip;
import com.battleship.model.FleetRepository;

public class PlaceShipServiceFactory {

    public static PlaceServiceShip createPlaceShipService(Session session, FleetRepository fleetRepository) {
        return new PlaceShipServiceImpl(session, fleetRepository);
    }
}
