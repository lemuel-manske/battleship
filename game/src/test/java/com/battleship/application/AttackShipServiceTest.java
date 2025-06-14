package com.battleship.application;

import static com.battleship.FleetAssertions.assertNoShipAtCoords;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.battleship.AttackShipService;
import com.battleship.Coordinate;
import com.battleship.MissShotException;
import com.battleship.model.Fleet;
import com.battleship.model.FleetRepository;
import com.battleship.model.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttackShipServiceTest {

    private Session session;

    private AttackShipService attackShipService;
    private FleetRepository fleetRepository;

    @BeforeEach
    void setUp() {
        session = new SessionStub();
        fleetRepository = new FleetRepositoryStub();

        fleetRepository.add(new Fleet(session.getFleetId()));

        attackShipService = new AttackShipServiceImpl(session, fleetRepository);
    }

    @Test
    void givenFreshFleet_whenAttackAnyCoordinated_thenThrowMiss() {
        assertMiss(anyCoordinate());
    }

    @Test
    void givenPlacedShip_whenShot_thenNoShipAtCoordinateAnymore() {
        Coordinate coordinate = Coordinate.valueOf(3, 2);

        Fleet fleet = fleetRepository.getById(session.getFleetId());

        fleet.placeShipHorizontally(Ship.battleship(), coordinate);

        attackShipService.attackShipAt(coordinate);

        assertNoShipAtCoords(coordinate, fleet);
    }

    private void assertMiss(Coordinate coordinate) {
        assertThrows(MissShotException.class, () -> attackShipService.attackShipAt(coordinate));
    }

    private Coordinate anyCoordinate() {
        return Coordinate.valueOf(3, 2);
    }
}
