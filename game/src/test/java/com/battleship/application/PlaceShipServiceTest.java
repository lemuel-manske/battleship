package com.battleship.application;

import static com.battleship.FleetAssertions.assertThatShipIsAtCoordinateMovingRight;

import com.battleship.Coordinate;
import com.battleship.PlaceServiceShip;
import com.battleship.ShipDto;
import com.battleship.ShipType;
import com.battleship.model.Fleet;
import com.battleship.model.FleetRepository;
import com.battleship.model.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaceShipServiceTest {

    private Session session;

    private PlaceServiceShip placeShipService;
    private FleetRepository fleetRepository;

    @BeforeEach
    void setUp() {
        session = new SessionStub();
        fleetRepository = new FleetRepositoryStub();

        fleetRepository.add(new Fleet(session.getFleetId()));

        placeShipService = new PlaceShipServiceImpl(session, fleetRepository);
    }

    @Test
    void givenFreshFleet_whenShipIsPlaced_thenRetrieveShipAtSpecifiedPosition() {
        ShipDto shipToPlace = new ShipDto(ShipType.BATTLESHIP, Coordinate.valueOf(1, 3));

        placeShipService.placeShip(shipToPlace);

        Fleet fleet = fleetRepository.getById(session.getFleetId());

        assertThatShipIsAtCoordinateMovingRight(Ship.battleship(), Coordinate.valueOf(1, 3), fleet);
    }
}
