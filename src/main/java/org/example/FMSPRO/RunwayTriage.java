package org.example.FMSPRO;


import org.example.FMSPRO.Flight;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class RunwayTriage {
    private PriorityQueue<Flight> heap = new PriorityQueue<>();

    public void requestLanding(Flight f) { heap.add(f); }
    public Flight processNext() { return heap.poll(); }
    public boolean hasFlights() { return !heap.isEmpty(); }

    public List<Flight> getQueue(){
        List<Flight> list = new ArrayList<>(heap);
        list.sort(null);
        return list;
    }
}