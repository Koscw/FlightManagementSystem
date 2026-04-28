package main.com.flightapp.models;

public class Flight implements Comparable<Flight> {
    private String flightNumber;
    private String destination;
    private int priorityScore; 

    public Flight(String flightNumber, String destination, int priorityScore) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.priorityScore = priorityScore;
    }

    public String getFlightNumber() { return flightNumber; }
    public String getDestination() { return destination; }
    public int getPriorityScore() { return priorityScore; }

    @Override
    public int compareTo(Flight other) {
        
        return Integer.compare(this.priorityScore, other.priorityScore);
    }

    @Override
    public String toString() {
        return String.format("[%s] to %s | Priority: %d", flightNumber, destination, priorityScore);
    }
}