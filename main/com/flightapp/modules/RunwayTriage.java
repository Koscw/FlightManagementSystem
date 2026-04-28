package main.com.flightapp.modules;

import main.com.flightapp.models.Flight;
import java.util.PriorityQueue;

public class RunwayTriage {
    private PriorityQueue<Flight> heap = new PriorityQueue<>();

    public void requestLanding(Flight f) { heap.add(f); }
    public Flight processNext() { return heap.poll(); }
    public boolean hasFlights() { return !heap.isEmpty(); }
}