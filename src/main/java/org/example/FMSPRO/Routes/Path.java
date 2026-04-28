package org.example.FMSPRO.Routes;

import java.util.List;

public class Path {
    private final List<String> nodes;
    private final int distance;

    public Path(List<String> nodes, int distance) {
        this.nodes = List.copyOf(nodes);
        this.distance = distance;
    }

    public List<String> getNodes() {
        return nodes;
    }

    public int getDistance() {
        return distance;
    }

    public boolean exists() {
        return !nodes.isEmpty();
    }
}
