package com.battleship;

public sealed abstract class Ship permits
    PatrolBoat,
    Submarine,
    Destroyer,
    Battleship,
    Carrier {

    private int timesHit;

    abstract int size();

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

    public boolean isSunk() {
        return timesHit == size();
    }

    public void hit() {
        timesHit++;
    }
}
