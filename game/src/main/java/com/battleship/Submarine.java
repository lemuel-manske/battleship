package com.battleship;

public final class Submarine extends Ship {

    private static final String PRINT_FORMAT = "Submarine: [ size=%d ]";

    @Override
    public int size() {
        return 3;
    }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(size());
    }
}
