package org.example.FMSPRO.Storage;

import org.example.FMSPRO.BoardingStatuses;
import org.example.FMSPRO.StorageListener;
import org.example.FMSPRO.IBoardingFlight;

public interface IFlightManager {
    void addListener(StorageListener storageListener);

    void updateFlightStatus(String id, BoardingStatuses status);

    void deleteFlight(String id);

    void addFlight(IBoardingFlight f);

    IBoardingFlight getFlight(String id);
    Boolean isFlightExists(String id);
    Boolean isGateInUse(String gate);

    IBoardingFlight[] getFlights();

}

