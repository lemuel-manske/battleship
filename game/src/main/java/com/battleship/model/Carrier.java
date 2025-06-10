package com.battleship.model;

public final class Carrier extends Ship {

    private static final String PRINT_FORMAT = "Carrier: [ size=%d ]";

    @Override
    public int size() {
        return 5;
    }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(size());
    }
}
