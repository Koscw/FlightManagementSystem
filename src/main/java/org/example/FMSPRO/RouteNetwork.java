package org.example.FMSPRO;

import java.util.*;

public class RouteNetwork {
    private Map<String, List<Connection>> routes = new HashMap<>();

    public void addRoute(String from, String to, int time) {
        routes.computeIfAbsent(from, k -> new ArrayList<>()).add(new Connection(to, time));
    }

    public void analyzeDelay(String city, int delayMinutes) {
        System.out.println("Alert: Flights from " + city + " are delayed by " + delayMinutes + "m.");
    }
}

class Connection {
    String destination;
    int time;

    Connection(String destination, int time) {
        this.destination = destination;
        this.time = time;
    }
}