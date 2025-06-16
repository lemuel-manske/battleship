package com.battleship.model;

public sealed abstract class Ship permits
    PatrolBoat,
    Submarine,
    Destroyer,
    Battleship,
    Carrier {

    private int timesHit;

    public abstract int size();

    public static Ship patrolBoat() {
        return new PatrolBoat();
    }

    public static Ship submarine() {
        return new Submarine();
    }

    public static Ship destroyer() {
        return new Destroyer();
    }

    public static Ship battleship() {
        return new Battleship();
    }

    public static Ship carrier() {
        return new Carrier();
    }

    public boolean isSunk() {
        return timesHit == size();
    }

    public void hit() {
        timesHit++;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Ship ship = (Ship) obj;

        return size() == ship.size() && timesHit == ship.timesHit;
    }
}
