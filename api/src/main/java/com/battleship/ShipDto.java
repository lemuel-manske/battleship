package com.battleship;

public record ShipDto(
    ShipType shipType,
    Coordinate coords) {
}
