package org.example.FMSPRO.Boarding;

import org.example.FMSPRO.Common.IFlight;

public interface IBoardingFlight extends IFlight {
    String getGate();

    BoardingStatuses getStatus();

    void setStatus(BoardingStatuses status);
}
