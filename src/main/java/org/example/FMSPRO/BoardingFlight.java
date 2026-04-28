package org.example.FMSPRO;

public class BoardingFlight extends Flight implements IBoardingFlight{

    private final String gate;
    private BoardingStatuses status;

    public BoardingFlight(String flightNumber, String source, String destination, String gate) {
        super(flightNumber, source, destination);

        this.gate = gate;
        this.status = BoardingStatuses.Closed;
    }

    @Override
    public String getGate() {
        return gate;
    }

    @Override
    public BoardingStatuses getStatus() {
        return status;
    }

    public void setStatus(BoardingStatuses status) {
        this.status = status;
    }

}
