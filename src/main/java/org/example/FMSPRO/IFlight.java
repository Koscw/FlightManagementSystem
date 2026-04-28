package org.example.FMSPRO;

public interface IFlight {
    String getNumber();

    String getDestination();
    String getSource();

    void setStatus(BoardingStatuses status);
}

