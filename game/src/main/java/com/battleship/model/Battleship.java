package com.battleship.model;

public final class Battleship extends Ship {

    private static final String PRINT_FORMAT = "Battleship: [ size=%d ]";

    @Override
    public int size() {
        return 4;
    }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(size());
    }
}
