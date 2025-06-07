package com.battleship;

import static org.junit.jupiter.api.Assertions.assertSame;

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

        board.placeShip(carrier, coordinate);

        Coordinate finalCoords = coordinate.goRightBy(carrier.size());

        for (Coordinate c : coordinate.goRightUntil(finalCoords)) {
            assertSame(carrier, board.getShipAt(c));
        }
    }
}
