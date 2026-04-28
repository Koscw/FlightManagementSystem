package org.example.FMSPRO.Runway;

import org.example.FMSPRO.Flight;
import org.example.FMSPRO.Priority;

public class PrioritizedFlight extends Flight implements Comparable<PrioritizedFlight>, IPrioritizedFlight {
    private final Priority priority;

    public PrioritizedFlight(String flightNumber, String source, String destination, Priority priority) {
        super(flightNumber, source, destination);
        this.priority = priority;

    }

    @Override
    public Priority getPriority() {
        return priority;
    }

    @Override
    public int compareTo(PrioritizedFlight o) {
        return this.priority.getScore() - o.getPriority().getScore();
    }
}
