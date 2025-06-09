package com.battleship;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUpBoard() {
        Fleet fleet = new Fleet();

        board = new Board(fleet);
    }

    @Test
    void whenCarrierIsPlacedAtCoordinateGoingRight_thenOccupiesPathAlongItsSize() {
        Ship carrier = Ship.carrier();

        Coordinate coordinate = Coordinate.valueOf(0, 0);

        board.placeShipHorizontally(carrier, coordinate);

        Coordinate finalCoords = coordinate.goRightBy(carrier.size());

        for (Coordinate c : coordinate.goRightUntil(finalCoords)) {
            assertSame(carrier, board.getShipAt(c));
        }
    }

    @Test
    void whenAttackOufOfNorthBounds_thenThrowOutOfFleetBoundsException() {
        assertThrows(OutOfFleetBoundsException.class, () -> board.attack(Coordinate.valueOf(0, -1)));
    }

    @Test
    void whenAttackOufOfSouthBounds_thenThrowOutOfFleetBoundsException() {
        assertThrows(OutOfFleetBoundsException.class, () -> board.attack(Coordinate.valueOf(0, 11)));
    }

    @Test
    void whenAttackOufOfWestBounds_thenThrowOutOfFleetBoundsException() {
        assertThrows(OutOfFleetBoundsException.class, () -> board.attack(Coordinate.valueOf(-1, 0)));
    }

    @Test
    void whenAttackOufOfEastBounds_thenThrowOutOfFleetBoundsException() {
        assertThrows(OutOfFleetBoundsException.class, () -> board.attack(Coordinate.valueOf(11, 0)));
    }

    @Test
    void whenAttackOufOfFleetBounds_thenThrowOutOfFleetBoundsException() {
        assertThrows(OutOfFleetBoundsException.class, () -> board.attack(Coordinate.valueOf(11, 11)));
    }

    @Test
    void whenAttackInBoundsCoordinate_butNoShipAtCoordinate_thenMiss() {
        Strike response = board.attack(Coordinate.valueOf(0, 0));

        assertFalse(response.isHit());
    }

    @Test
    void givenShip_whenAttack_thenIsHit() {
        Coordinate coordinate = Coordinate.valueOf(0, 0);

        board.placeShipHorizontally(Ship.carrier(), coordinate);

        Strike response = board.attack(coordinate);

        assertTrue(response.isHit());
    }

    @Test
    void givenShip_whenHit_thenNoShipAtCoordinateAnymore() {
        Coordinate coordinate = Coordinate.valueOf(0, 0);

        board.placeShipHorizontally(Ship.carrier(), coordinate);

        board.attack(coordinate);

        assertNull(board.getShipAt(coordinate));
    }

    @Test
    void givenShip_whenJustHitOnce_thenIsNotSunkYetAsJustPartialDamage() {
        Ship ship = Ship.patrolBoat();

        board.placeShipHorizontally(ship, Coordinate.valueOf(0, 0));

        board.attack(Coordinate.valueOf(0, 0));

        assertFalse(ship.isSunk());
    }

    @Test
    void givenMultipleHits_whenFinalAttackIsMade_thenShipIsSunk() {
        Ship ship = Ship.patrolBoat();

        board.placeShipHorizontally(ship, Coordinate.valueOf(0, 0));

        board.attack(Coordinate.valueOf(0, 0));
        board.attack(Coordinate.valueOf(1, 0));

        assertTrue(ship.isSunk());
    }
}
