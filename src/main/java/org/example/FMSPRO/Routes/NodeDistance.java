package org.example.FMSPRO.Routes;

public class NodeDistance {
    private final String node;
    private final int distance;

    public NodeDistance(String node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    public String getNode() {
        return node;
    }

    public int getDistance() {
        return distance;
    }
}
