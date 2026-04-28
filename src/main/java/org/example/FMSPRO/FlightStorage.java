package org.example.FMSPRO;

import java.util.Hashtable;

public class FlightStorage implements IFlightStorage {
    private final Hashtable<String, IBoardingFlight> flights = new Hashtable<>();

    @Override
    public void updateFlightStatus(String id, BoardingStatuses status) {
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
    public void addFlight(IBoardingFlight f) {
        flights.put(f.getNumber(), f);
    }

    @Override
    public IBoardingFlight getFlight(String id) {
        return flights.get(id);
    }

    @Override
    public Boolean isFlightExists(String id) {
        return flights.containsKey(id);
    }

    @Override
    public Boolean isGateInUse(String gate) {
        for (IBoardingFlight flight : flights.values()) {
            if (flight.getGate().equals(gate))
                return true;
        }
        return false;
    }

    @Override
    public IBoardingFlight[] getFlights() {
        return flights.values().toArray(new IBoardingFlight[0]);
    }
}