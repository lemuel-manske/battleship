package com.battleship.application;

import com.battleship.model.Fleet;
import com.battleship.model.FleetRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FleetRepositoryStub implements FleetRepository {

    private final List<Fleet> fleets;

    public FleetRepositoryStub() {
        this.fleets = new ArrayList<>();
    }

    @Override
    public Fleet getById(UUID fleetId) {
        return fleets.stream()
            .filter(fleet -> fleet.getId().equals(fleetId))
            .findFirst()
            .orElseThrow();
    }

    @Override
    public void add(Fleet fleet) {
        fleets.add(fleet);
    }
}
