package com.battleship;

public class Ship {

    private static final String PRINT_FORMAT = "[ size=%d ]";

    private final int size;

    private Ship(final int size) {
        this.size = size;
    }

    public static Ship patrolBoat() {
        return new Ship(2);
    }

    public static Ship submarine() {
        return new Ship(3);
    }

    public static Ship destroyer() {
        return new Ship(3);
    }

    public static Ship battleship() {
        return new Ship(4);
    }

    public static Ship carrier() {
        return new Ship(5);
    }

    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return PRINT_FORMAT.formatted(size);
    }
}
