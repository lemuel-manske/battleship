package com.battleship.adapter.persistence;

import com.battleship.model.Fleet;
import com.battleship.model.FleetRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InMemoryFleetRepository implements FleetRepository {

    private final Map<UUID, Fleet> fleets;

    public InMemoryFleetRepository() {
        this.fleets = new HashMap<>();
    }

    @Override
    public Fleet getById(UUID fleetId) {
        return fleets.get(fleetId);
    }

    @Override
    public void add(Fleet fleet) {
        fleets.put(fleet.getId(), fleet);
    }
}
