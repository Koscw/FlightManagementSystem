package org.example.FMSPRO.Boarding;

import org.example.FMSPRO.Storage.StorageListener;

import java.util.*;

public class BoardingManager implements ICheckinPassenger, StorageListener {
    private final Map<String, Queue<String>> boardingQueues = new HashMap<>();

    @Override
    public void addPassenger(String flightId, String passengerName) {
        boardingQueues
                .computeIfAbsent(flightId, key -> new LinkedList<>())
                .add(passengerName);
    }

    public String boardNextPassenger(String flightId) {
        Queue<String> queue = boardingQueues.get(flightId);

        if (queue == null || queue.isEmpty()) {
            return null;
        }

        return queue.poll();
    }

    public boolean hasPassengers(String flightId) {
        Queue<String> queue = boardingQueues.get(flightId);

        if (queue == null) {
            return false;
        }

        return !queue.isEmpty();
    }

    @Override
    public void flightRemoved(IBoardingFlight flight) {
        String number = flight.getNumber();
        if (boardingQueues.get(number) != null)
            boardingQueues.remove(number);
    }
}