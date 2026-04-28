package org.example.FMSPRO;

public interface IFlight {
    String getNumber();

    String getDestination();

    FlightStatuses getStatus();

    void setStatus(FlightStatuses status);
}
