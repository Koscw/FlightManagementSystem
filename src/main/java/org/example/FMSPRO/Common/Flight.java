package org.example.FMSPRO.Common;

public class Flight implements IFlight {
    private final String number;
    private final String destination;
    private final String source;

    public Flight(String flightNumber, String source, String destination) {
        this.number = flightNumber;
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String toString() {
        return String.format("[%s] to %s | Priority: %d", number, destination);
    }
}

