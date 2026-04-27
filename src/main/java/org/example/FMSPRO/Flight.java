package org.example.FMSPRO;

public class Flight {
    private final String number;
    private final String destination;
    private final int priorityScore;

    public Flight(String flightNumber, String destination, int priorityScore) {
        this.number = flightNumber;
        this.destination = destination;
        this.priorityScore = priorityScore;
    }

    public String getNumber() { return number; }
    public String getDestination() { return destination; }
    public int getPriorityScore() { return priorityScore; }

    @Override
    public String toString() {
        return String.format("[%s] to %s | Priority: %d", number, destination, priorityScore);
    }
}