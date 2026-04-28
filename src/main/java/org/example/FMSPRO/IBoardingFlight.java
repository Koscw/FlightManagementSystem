package org.example.FMSPRO;

public interface IBoardingFlight extends IFlight {
    String getGate();

    BoardingStatuses getStatus();
}
