package com.battleship.application;

import com.battleship.model.FleetRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class ServiceTest {

    public Session session;
    public FleetRepository fleetRepository;

    @BeforeAll
    void setUpAll() {
        session = new SessionStub();
        fleetRepository = new InMemoryFleetRepository();
    }
}
