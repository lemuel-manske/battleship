package com.battleship.model;

import static com.battleship.FleetAssertions.assertNoShipAtCoords;
import static com.battleship.FleetAssertions.assertThatShipIsAtCoordinateMovingDown;
import static com.battleship.FleetAssertions.assertThatShipIsAtCoordinateMovingRight;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.battleship.Coordinate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class FleetTest {

    private Fleet fleet;

    @Nested
    class GivenEmptyFleet {

        @BeforeEach
        void setUpFleet() {
            fleet = Fleet.create();
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
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.placeShipHorizontally(anyShip(), Coordinate.valueOf(7, 11)));
        }

        @Test
        void whenAnyShipIsPlacedOutOfFleetEastBounds_ThenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.placeShipHorizontally(anyShip(), Coordinate.valueOf(12, 8)));
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

            assertThatShipIsAtCoordinateMovingRight(aShip, coords, fleet);
        }

        @Test
        void whenShipIsPlacedVerticallyAtFreeCoordinate_ThenShipMustBeAtCoordinateSpecified() {
            Ship aShip = Ship.submarine();

            Coordinate coords = Coordinate.valueOf(2, 4);

            fleet.placeShipVertically(aShip, coords);

            assertThatShipIsAtCoordinateMovingDown(aShip, coords, FleetTest.this.fleet);
        }

        @Test
        void whenShipIsPlacedAtFreeCoordinate_ThenShipTakesJustEnoughToFitItsSize() {
            Ship aShip = Ship.patrolBoat();

            Coordinate coords = Coordinate.valueOf(2, 4);

            fleet.placeShipHorizontally(aShip, coords);

            assertNoShipAtCoords(coords.goRightBy(aShip.size()), FleetTest.this.fleet);
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
            }

            @Test
            void whenAnotherShipIsPlacedAtAnyOccupiedCoordinate_ThenThrowCoordinateAlreadyOccupiedException() {
                assertThatShipIsAlreadyPlacedMovingRight(alreadyPlacedShipCoords, anyShip());
            }

            @Test
            void whenAnotherShipIsPlacedNearAtAlreadyPlacedOne_ThenThrowCoordinateAlreadyOccupiedException() {
                assertThatNearbyCoordinatesAreOccupied(alreadyPlacedShipCoords);
            }

            @Test
            void whenAnotherShipIsPlacedAwayFromAlreadyPlacedOne_ButShipsSizeWillInvadeCompromisedArea_Horizontally_ThenThrowCoordinateAlreadyOccupiedException() {
                Ship shipToPlace = Ship.patrolBoat();

                assertThatShipsInvadeOccupiedAreaHorizontally(shipToPlace, alreadyPlacedShipCoords.goLeftBy(shipToPlace.size()));
            }

            @Test
            void whenAnotherShipIsPlacedAwayFromAlreadyPlacedOne_ButShipsSizeWillInvadeCompromisedAreaVertically_ThenThrowCoordinateAlreadyOccupiedException() {
                assertThatShipsInvadeOccupiedAreaVertically(Ship.patrolBoat(), alreadyPlacedShipCoords.goUpBy(Ship.patrolBoat().size()));
            }

            @Test
            void whenAnotherShipIsPlacedAwayFromAlreadyPlacedOne_AndFitsIntoCoordinates_ThenShipIsPlaced() {
                Ship shipToPlace = Ship.patrolBoat();

                Coordinate coords = alreadyPlacedShipCoords.goRightBy(alreadyPlacedShip.size() + 1);

                fleet.placeShipVertically(shipToPlace, coords);

                assertThatShipIsAtCoordinateMovingDown(shipToPlace, coords, fleet);
            }

            @Test
            void whenTryingToPlaceShip_AndItTouchesAlreadyPlacedOneDiagonally_ThenThrowCoordinateAlreadyOccupiedException() {
                assertThatShipsInvadeOccupiedAreaHorizontally(Ship.patrolBoat(), Coordinate.valueOf(0, 3));
            }

            @Test
            void whenShipIsPlacedBetweenTwoOtherShips_ThenThrowCoordinateAlreadyOccupiedException() {
                fleet.placeShipHorizontally(Ship.patrolBoat(), alreadyPlacedShipCoords.goUpBy(2));

                assertThrows(CoordinateAlreadyOccupiedException.class, () -> fleet.placeShipHorizontally(Ship.patrolBoat(), alreadyPlacedShipCoords.goUpBy(1)));
            }

            private void assertThatShipIsAlreadyPlacedMovingRight(Coordinate coords, Ship aShip) {
                for (int i = 0; i < alreadyPlacedShip.size(); i++) {
                    Coordinate goneRight = coords.goRightBy(i);

                    assertThrows(CoordinateAlreadyOccupiedException.class, () -> fleet.placeShipHorizontally(aShip, goneRight));
                }
            }
        }

        @Test
        void whenAttackOufOfNorthBounds_thenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.shootShip(Coordinate.valueOf(0, -1)));
        }

        @Test
        void whenAttackOufOfSouthBounds_thenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.shootShip(Coordinate.valueOf(0, 11)));
        }

        @Test
        void whenAttackOufOfWestBounds_thenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.shootShip(Coordinate.valueOf(-1, 0)));
        }

        @Test
        void whenAttackOufOfEastBounds_thenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.shootShip(Coordinate.valueOf(11, 0)));
        }

        @Test
        void whenAttackOufOfFleetBounds_thenThrowOutOfFleetBoundsException() {
            assertThrows(OutOfFleetBoundsException.class, () -> fleet.shootShip(Coordinate.valueOf(11, 11)));
        }

        @Test
        void whenAttackInBoundsCoordinate_butNoShipAtCoordinate_thenMiss() {
            Strike response = fleet.shootShip(Coordinate.valueOf(0, 0));

            assertFalse(response.isHit());
        }

        @Test
        void givenShip_whenAttack_thenIsHit() {
            Coordinate coordinate = Coordinate.valueOf(0, 0);

            fleet.placeShipHorizontally(Ship.carrier(), coordinate);

            Strike response = fleet.shootShip(coordinate);

            assertTrue(response.isHit());
        }

        @Test
        void givenShip_whenHit_thenNoShipAtCoordinateAnymore() {
            Coordinate coordinate = Coordinate.valueOf(0, 0);

            fleet.placeShipHorizontally(Ship.carrier(), coordinate);

            fleet.shootShip(coordinate);

            assertNoShipAtCoords(coordinate, fleet);
        }

        @Test
        void givenShip_whenJustHitOnce_thenIsNotSunkYetAsJustPartialDamage() {
            Ship ship = Ship.patrolBoat();

            fleet.placeShipHorizontally(ship, Coordinate.valueOf(0, 0));

            fleet.shootShip(Coordinate.valueOf(0, 0));

            assertFalse(ship.isSunk());
        }

        @Test
        void givenMultipleHits_whenFinalAttackIsMade_thenShipIsSunk() {
            Ship ship = Ship.patrolBoat();

            fleet.placeShipHorizontally(ship, Coordinate.valueOf(0, 0));

            fleet.shootShip(Coordinate.valueOf(0, 0));
            fleet.shootShip(Coordinate.valueOf(1, 0));

            assertTrue(ship.isSunk());
        }
    }

    private void assertThatShipsInvadeOccupiedAreaVertically(Ship shipToPlace, Coordinate coordinate) {
        assertThrows(CoordinateAlreadyOccupiedException.class, () -> fleet.placeShipVertically(shipToPlace, coordinate));
    }

    private void assertThatShipsInvadeOccupiedAreaHorizontally(Ship ship, Coordinate coords) {
        assertThrows(CoordinateAlreadyOccupiedException.class, () -> fleet.placeShipHorizontally(ship, coords));
    }

    private void assertThatNearbyCoordinatesAreOccupied(Coordinate coords) {
        for (Coordinate c : coords.getNearbyCoordinates()) {
            assertThrows(CoordinateAlreadyOccupiedException.class, () -> fleet.placeShipHorizontally(anyShip(), c));
        }
    }

    private Ship anyShip() {
        return Ship.submarine();
    }
}
