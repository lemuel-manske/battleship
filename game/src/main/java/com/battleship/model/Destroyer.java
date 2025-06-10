package com.battleship.model;

public final class Destroyer extends Ship {

    private static final String PRINT_FORMAT = "Destroyer: [ size=%d ]";

    @Override
    public int size() {
        return 3;
    }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(size());
    }
}
