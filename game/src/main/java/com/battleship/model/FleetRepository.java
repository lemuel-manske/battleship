package com.battleship.model;

import java.util.UUID;

public interface FleetRepository {

    Fleet getById(UUID fleetId);

    void add(Fleet fleet);
}
