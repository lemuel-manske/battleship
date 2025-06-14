package com.battleship.application;

import com.battleship.AttackShipService;
import com.battleship.Coordinate;
import com.battleship.MissShotException;
import com.battleship.model.Fleet;
import com.battleship.model.FleetRepository;
import com.battleship.model.Strike;

public class AttackShipServiceImpl implements AttackShipService {

    private final Fleet fleet;

    public AttackShipServiceImpl(Session session, FleetRepository fleetRepository) {
        fleet = fleetRepository.getById(session.getFleetId());
    }

    @Override
    public void attackShipAt(Coordinate coordinate) {
        Strike result = fleet.shootShip(coordinate);

        if (!result.isHit()) {
            throw new MissShotException();
        }
    }
}
