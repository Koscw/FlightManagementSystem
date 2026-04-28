package org.example.FMSPRO;

public class PrioritizedFlight extends Flight implements Comparable<PrioritizedFlight>, IPrioritizedFlight {
    private final int priorityScore;

    public PrioritizedFlight(String flightNumber, String destination, int priorityScore) {
        super(flightNumber, destination);
        this.priorityScore = priorityScore;

    }

    @Override
    public int getPriorityScore() {
        return priorityScore;
    }

    @Override
    public int compareTo(PrioritizedFlight o) {
        return this.priorityScore - o.getPriorityScore();
    }
}
