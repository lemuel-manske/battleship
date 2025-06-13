package com.battleship.application;

import java.util.UUID;

public class SessionStub implements Session {

    private final UUID fleetId;

    public SessionStub() {
        fleetId = UUID.randomUUID();
    }

    @Override
    public UUID getFleetId() {
        return fleetId;
    }
}
