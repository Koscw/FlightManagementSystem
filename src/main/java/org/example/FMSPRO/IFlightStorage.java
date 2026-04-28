package org.example.FMSPRO;

public interface IFlightStorage {
    IFlight getFlightById(String id);

    void updateFlightStatus(String id, FlightStatuses status);

    void deleteFlight(String id);

    void addFlight(IFlight f);

    IFlight getFlight(String id);
}
