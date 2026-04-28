package org.example.FMSPRO;

public interface IFlight {
    String getNumber();

    String getDestination();

    void setStatus(FlightStatuses status);
}
