package org.example.FMSPRO;

public interface IFlightStorage {
    void updateFlightStatus(String id, BoardingStatuses status);

    void deleteFlight(String id);

    void addFlight(IBoardingFlight f);

    IBoardingFlight getFlight(String id);
    Boolean isFlightExists(String id);
    Boolean isGateInUse(String gate);

    IBoardingFlight[] getFlights();

}

