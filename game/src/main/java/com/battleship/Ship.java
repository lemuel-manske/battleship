package com.battleship;

public interface Ship {

    static Ship patrolBoat() {
        return new PatrolBoat();
    }

    static Ship submarine() {
        return new Submarine();
    }

    static Ship destroyer() {
        return new Destroyer();
    }

    static Ship battleship() {
        return new Battleship();
    }

    static Ship carrier() {
        return new Carrier();
    }

    int size();
}
