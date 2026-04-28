package org.example.FMSPRO.Runway;

import java.util.*;

public class RunwayRequests {
    private final PriorityQueue<IPrioritizedFlight> queue = new PriorityQueue<>();

    public void addFlight(IPrioritizedFlight f) {
        queue.add(f);
    }

    public IPrioritizedFlight processNext() {
        return queue.poll();
    }

    public boolean isNumberExists(String name) {
        Iterator<IPrioritizedFlight> flights = getFlights();

        while (flights.hasNext()) {
            if (Objects.equals(flights.next().getNumber(), name))
                return true;
        }

        return false;
    }

    public boolean hasFlights() {
        return !queue.isEmpty();
    }

    public Iterator<IPrioritizedFlight> getFlights() {
        return queue.iterator();
    }
}