package com.battleship.application;

import static com.battleship.FleetAssertions.assertThatShipIsAtCoordinateMovingRight;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.battleship.Coordinate;
import com.battleship.PlaceShipService;
import com.battleship.ShipDto;
import com.battleship.model.CoordinateAlreadyOccupiedException;
import com.battleship.model.Fleet;
import com.battleship.model.Ship;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PlaceShipServiceTest extends ServiceTest {

    private PlaceShipService placeShipService;

    @BeforeEach
    void setUp() {
        fleetRepository.add(Fleet.withId(session.getFleetId()));

        placeShipService = new PlaceShipServiceImpl(session, fleetRepository);
    }

    @Test
    void givenFreshFleet_whenShipIsPlaced_thenRetrieveShipAtSpecifiedPosition() {
        ShipDto shipToPlace = new ShipDto(ShipDto.Type.BATTLESHIP, Coordinate.valueOf(1, 3));

        placeShipService.placeShip(shipToPlace);

        Fleet fleet = fleetRepository.getById(session.getFleetId());

        assertThatShipIsAtCoordinateMovingRight(Ship.battleship(), Coordinate.valueOf(1, 3), fleet);
    }

    @Test
    void givenShipAtCoordinate_whenAnotherShipIsPlacedAtOccupiedCoordinates_thenThrowCoordinateAlreadyOccupiedException() {
        Coordinate initialCoords = Coordinate.valueOf(1, 3);

        ShipDto shipToPlace = new ShipDto(ShipDto.Type.BATTLESHIP, initialCoords);

        placeShipService.placeShip(shipToPlace);

        assertThatPathIsOccupied(initialCoords, Coordinate.valueOf(3, 3));
    }

    @Test
    void givenShipAtCoordinate_whenAnotherShipInvadesOccupiedArea_thenThrowCoordinateAlreadyOccupiedException() {
        Coordinate initialCoords = Coordinate.valueOf(3, 2);

        ShipDto shipToPlace = new ShipDto(ShipDto.Type.SUBMARINE, initialCoords);

        placeShipService.placeShip(shipToPlace);

        assertThatShipInvadesOccupiedArea(new ShipDto(ShipDto.Type.SUBMARINE, Coordinate.valueOf(1, 3)));
    }

    private void assertThatPathIsOccupied(Coordinate initialCoords, Coordinate finalCoords) {
        Coordinate[] occupiedCoords = initialCoords.goRightUntil(finalCoords);

        for (Coordinate occupied : occupiedCoords) {
            assertThatShipInvadesOccupiedArea(anyShipAt(occupied));
        }
    }

    private void assertThatShipInvadesOccupiedArea(ShipDto shipToPlace) {
        assertThrows(CoordinateAlreadyOccupiedException.class, () ->
            placeShipService.placeShip(shipToPlace));
    }

    private ShipDto anyShipAt(Coordinate occupied) {
        return new ShipDto(ShipDto.Type.SUBMARINE, occupied);
    }
}
