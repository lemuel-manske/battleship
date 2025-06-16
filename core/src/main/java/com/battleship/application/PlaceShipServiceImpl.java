package com.battleship.application;

import static com.battleship.application.ShipAssembler.assembleShip;

import com.battleship.PlaceShipService;
import com.battleship.ShipDto;
import com.battleship.model.Fleet;
import com.battleship.model.FleetRepository;

public class PlaceShipServiceImpl implements PlaceShipService {

    private final Fleet fleet;

    public PlaceShipServiceImpl(Session session, FleetRepository fleetRepository) {
        fleet = fleetRepository.getById(session.getFleetId());
    }

    @Override
    public void placeShip(ShipDto shipToPlace) {
        fleet.placeShipHorizontally(assembleShip(shipToPlace), shipToPlace.coords());
    }
}
