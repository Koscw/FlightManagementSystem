package org.example.FMSPRO;

import org.example.FMSPRO.Flight;
import java.util.HashMap;

public class FlightManager {
    private HashMap<String, Flight> registry = new HashMap<>();

    public void addFlight(Flight f) { registry.put(f.getFlightNumber(), f); }
    public Flight getFlight(String id) { return registry.get(id); }
}