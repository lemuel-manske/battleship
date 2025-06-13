package com.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.battleship.model.Fleet;
import com.battleship.model.Ship;

public class FleetAssertions {

    public static void assertNoShipAtCoords(Coordinate coords, Fleet fleet) {
        assertNull(fleet.getShipAt(coords));
    }

    public static void assertThatShipIsAtCoordinateMovingRight(Ship ship, Coordinate expectedCoords, Fleet fleet) {
        for (int i = 0; i < ship.size(); i++) {
            assertEquals(ship, fleet.getShipAt(expectedCoords.goRightBy(i)));
        }
    }

    public static void assertThatShipIsAtCoordinateMovingDown(Ship ship, Coordinate expectedCoords, Fleet fleet) {
        for (int i = 0; i < ship.size(); i++) {
            assertEquals(ship, fleet.getShipAt(expectedCoords.goDownBy(i)));
        }
    }
}
