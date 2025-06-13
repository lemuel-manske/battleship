package com.battleship;

public record ShipDto(Type shipType, Coordinate coords) {

    public enum Type {

        BATTLESHIP,
        CARRIER,
        DESTROYER,
        PATROL_BOAT,
        SUBMARINE;
    }
}
