package main.com.flightapp.modules;

import java.util.*;

public class RouteNetwork {
    private Map<String, List<Connection>> adjList = new HashMap<>();

    public void addRoute(String from, String to, int time) {
        adjList.computeIfAbsent(from, k -> new ArrayList<>()).add(new Connection(to, time));
    }

    // Delay Propagation: If 'A' is delayed by X mins, all routes from 'A' are affected
    public void analyzeDelay(String city, int delayMinutes) {
        System.out.println("Alert: Flights from " + city + " are delayed by " + delayMinutes + "m.");
    }
}

class Connection {
    String to;
    int weight;
    Connection(String to, int w) { this.to = to; this.weight = w; }
}