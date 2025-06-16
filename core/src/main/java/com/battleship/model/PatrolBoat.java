package com.battleship.model;

public final class PatrolBoat extends Ship {

    private static final String PRINT_FORMAT = "PatrolBoat: [ size=%d ]";

    @Override
    public int size() {
        return 2;
    }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(size());
    }
}
