package org.example.FMSPRO.Storage;

import org.example.FMSPRO.Boarding.IBoardingFlight;

public interface StorageListener {
    void flightRemoved(IBoardingFlight flight);
}