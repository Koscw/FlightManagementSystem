package org.example.FMSPRO;

import java.util.HashMap;

public class FlightManager {
    private final HashMap<String, Flight> registry = new HashMap<>();

    public void addFlight(Flight f) { registry.put(f.getNumber(), f); }
    public Flight getFlight(String id) { return registry.get(id); }
}