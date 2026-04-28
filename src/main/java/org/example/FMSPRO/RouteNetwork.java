package org.example.FMSPRO;

import java.util.*;

public class RouteNetwork {
    private final Map<String, List<ConnectionNode>> routes = new HashMap<>();

    public void addRoute(String from, String to, int time) {
        routes.computeIfAbsent(from, k -> new ArrayList<>()).add(new ConnectionNode(to, time));
    }

    public void analyzeDelay(String city, int delayMinutes) {
        System.out.println("Alert: Flights from " + city + " are delayed by " + delayMinutes + "m.");
    }
}

