package org.example.FMSPRO;

import java.util.*;

public class RunwayRequests {
    private final PriorityQueue<IPrioritizedFlight> queue = new PriorityQueue<>();

    public void addFlight(IPrioritizedFlight f) {
        queue.add(f);
    }

    public IPrioritizedFlight processNext() {
        return queue.poll();
    }

    public boolean hasFlights() {
        return !queue.isEmpty();
    }

    public Iterator<IPrioritizedFlight> getFlights() {
        return queue.iterator();
    }
}