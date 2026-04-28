package org.example.FMSPRO.Storage;

import org.example.FMSPRO.Boarding.BoardingStatuses;
import org.example.FMSPRO.Boarding.IBoardingFlight;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class FlightManager implements IFlightManager {
    private final Hashtable<String, IBoardingFlight> flights = new Hashtable<>();
    private final List<StorageListener> listeners = new ArrayList<>();

    @Override
    public void addListener(StorageListener storageListener) {
        listeners.add(storageListener);
    }

    @Override
    public void updateFlightStatus(String id, BoardingStatuses status) {
        IBoardingFlight flight = flights.getOrDefault(id, null);

        if (flight == null)
            throw new IndexOutOfBoundsException("Invalid id");

        flight.setStatus(status);
    }

    @Override
    public void deleteFlight(String id) {
        IBoardingFlight flight = flights.get(id);
        flights.remove(id);

        for (StorageListener listener : listeners) {
            listener.flightRemoved(flight);
        }
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