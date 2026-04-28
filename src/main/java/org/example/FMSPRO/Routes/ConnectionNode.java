package org.example.FMSPRO.Routes;

public class ConnectionNode {
    private final String destination;
    private final int millage;

    ConnectionNode(String destination, int millage) {
        this.destination = destination;
        this.millage = millage;
    }


    public String getDestination() {
        return destination;
    }

    public int getMillage() {
        return millage;
    }
}
