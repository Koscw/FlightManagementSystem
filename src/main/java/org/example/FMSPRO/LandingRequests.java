package org.example.FMSPRO;

import java.util.*;

public class LandingRequests {
    private final PriorityQueue<Flight> queue = new PriorityQueue<>();

    public void addFlight(Flight f) {
        queue.add(f);
    }

    public Flight processNext() {
        return queue.poll();
    }

    public boolean hasFlights() {
        return !queue.isEmpty();
    }

    public Iterator<Flight> getFlights() {
        return queue.iterator();
    }
}