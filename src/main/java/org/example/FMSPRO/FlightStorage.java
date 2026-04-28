package org.example.FMSPRO;

import java.util.Hashtable;

public class FlightStorage implements IFlightStorage {
    private final Hashtable<String, IFlight> flights = new Hashtable<>();

    @Override
    public IFlight getFlightById(String id) {
        return flights.get(id);
    }

    @Override
    public void updateFlightStatus(String id, FlightStatuses status) {
        IFlight flight = flights.getOrDefault(id, null);

        if (flight == null)
            throw new IndexOutOfBoundsException("Invalid id");

        flight.setStatus(status);
    }

    @Override
    public void deleteFlight(String id) {
        flights.remove(id);
    }

    @Override
    public void addFlight(IFlight f) {
        flights.put(f.getNumber(), f);
    }

    @Override
    public IFlight getFlight(String id) {
        return flights.get(id);
    }
}