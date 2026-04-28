package org.example.FMSPRO;

public class Flight implements IFlight {
    private final String number;
    private final String destination;
    private FlightStatuses status;

    public Flight(String flightNumber, String destination) {
        this.number = flightNumber;
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
    public void setStatus(FlightStatuses status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("[%s] to %s | Priority: %d", number, destination);
    }
}

