package io.lkmliz;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FleetTest {

    private Fleet fleet;

    @Nested
    class GivenEmptyFleet {

        @BeforeEach
        void setUpFleet() {
            fleet = new Fleet();
        }

        @Test
        void whenAnyShipIsPlacedOutOfFleetNorthBounds_ThenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.placeShipHorizontally(anyShip(), Coordinate.valueOf(7, -1)));
        }

        @Test
        void whenAnyShipIsPlacedOutOfFleetWestBounds_ThenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.placeShipHorizontally(anyShip(), Coordinate.valueOf(-1, 7)));
        }

        @Test
        void whenAnyShipIsPlacedOutOfFleetSouthBounds_ThenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.placeShipHorizontally(anyShip(), Coordinate.valueOf(11, 7)));
        }

        @Test
        void whenAnyShipIsPlacedOutOfFleetEastBounds_ThenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.placeShipHorizontally(anyShip(), Coordinate.valueOf(8, 12)));
        }

        @Test
        void whenAnyShipIsPlacedOutOfFleetBounds_ThenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.placeShipHorizontally(anyShip(), Coordinate.valueOf(13, 12)));
        }

        @Test
        void whenShipExceedsFleetEastLimit_ThenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.placeShipHorizontally(Ship.carrier(), Coordinate.valueOf(6, 3)));
        }

        @Test
        void whenShipExceedsFleetSouthLimit_ThenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.placeShipVertically(Ship.carrier(), Coordinate.valueOf(3, 8)));
        }

        @Test
        void whenShipIsPlacedAtFreeCoordinate_ThenShipMustBeAtCoordinateSpecified() {
            Ship aShip = Ship.submarine();

            Coordinate coords = Coordinate.valueOf(2, 4);

            fleet.placeShipHorizontally(aShip, coords);

            assertThatShipIsAtCoordinateMovingRight(aShip, coords);
        }

        @Test
        void whenShipIsPlacedVerticallyAtFreeCoordinate_ThenShipMustBeAtCoordinateSpecified() {
            Ship aShip = Ship.submarine();

            Coordinate coords = Coordinate.valueOf(2, 4);

            fleet.placeShipVertically(aShip, coords);

            assertThatShipIsAtCoordinateMovingDown(aShip, coords);
        }

        @Nested
        class AndShipIsPlacedAtFreeCoordinate {

            private Ship alreadyPlacedShip;
            private Coordinate alreadyPlacedShipCoords;

            @BeforeEach
            void putShipAtCoordinate() {
                alreadyPlacedShip = Ship.submarine();
                alreadyPlacedShipCoords = Coordinate.valueOf(2, 4);

                fleet.placeShipHorizontally(alreadyPlacedShip, alreadyPlacedShipCoords);

                assertThatShipIsAtCoordinateMovingRight(alreadyPlacedShip, alreadyPlacedShipCoords);
            }

            @Test
            void whenAnotherShipIsPlacedAtAnyOccupiedCoordinate_ThenThrowCoordinateAlreadyOccupiedException() {
                assertThatShipIsAlreadyPlacedMovingRight(alreadyPlacedShipCoords, anyShip());
            }

            @Test
            void whenAnotherShipIsPlacedNearAtAlreadyPlacedOne_ThenThrowCoordinateAlreadyOccupiedException() {
                assertThatNearbyCoordinatesAreOccupied(alreadyPlacedShipCoords, anyShip());
            }

            @Test
            void whenAnotherShipIsPlacedAwayFromAlreadyPlacedOne_ButShipsSizeWillInvadeCompromisedArea_Horizontally_ThenThrowCoordinateAlreadyOccupiedException() {
                Ship shipToPlace = Ship.patrolBoat();

                assertThatShipsInvadeOccupiedAreaHorizontally(shipToPlace, alreadyPlacedShipCoords.goLeftBy(shipToPlace.size()));
            }

            @Test
            void whenAnotherShipIsPlacedAwayFromAlreadyPlacedOne_ButShipsSizeWillInvadeCompromisedAreaVertically__ThenThrowCoordinateAlreadyOccupiedException() {
                Ship shipToPlace = Ship.patrolBoat();

                assertThatShipsInvadeOccupiedAreaVertically(shipToPlace, alreadyPlacedShipCoords.goUpBy(shipToPlace.size()));
            }

            private void assertThatShipsInvadeOccupiedAreaVertically(Ship shipToPlace, Coordinate coordinate) {
                assertThrows(CoordinateAlreadyOccupiedException.class, () -> fleet.placeShipVertically(shipToPlace, coordinate));
            }

            private void assertThatShipsInvadeOccupiedAreaHorizontally(Ship ship, Coordinate coords) {
                assertThrows(CoordinateAlreadyOccupiedException.class, () -> fleet.placeShipHorizontally(ship, coords));
            }

            private void assertThatShipIsAlreadyPlacedMovingRight(Coordinate coords, Ship aShip) {
                for (int i = 0; i < alreadyPlacedShip.size(); i++) {
                    Coordinate goneRight = coords.goRightBy(i);

                    assertThrows(CoordinateAlreadyOccupiedException.class, () -> fleet.placeShipHorizontally(aShip, goneRight));
                }
            }

            private void assertThatNearbyCoordinatesAreOccupied(Coordinate coords, Ship aShip) {
                for (Coordinate c : coords.getNearbyCoordinates()) {
                    assertThrows(CoordinateAlreadyOccupiedException.class, () -> fleet.placeShipHorizontally(aShip, c));
                }
            }
        }
    }

    private void assertThatShipIsAtCoordinateMovingRight(Ship ship, Coordinate expectedCoords) {
        for (int i = 0; i < ship.size(); i++) {
            assertSame(ship, fleet.shipAt(expectedCoords.goRightBy(i)));
        }
    }

    private void assertThatShipIsAtCoordinateMovingDown(Ship ship, Coordinate expectedCoords) {
        for (int i = 0; i < ship.size(); i++) {
            assertSame(ship, fleet.shipAt(expectedCoords.goDownBy(i)));
        }
    }

    private Ship anyShip() {
        return Ship.submarine();
    }
}
