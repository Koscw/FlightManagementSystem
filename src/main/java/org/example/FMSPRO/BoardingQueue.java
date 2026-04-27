package org.example.FMSPRO;

import java.util.PriorityQueue;

public class BoardingQueue {
    private final PriorityQueue<String> passengers = new PriorityQueue<>();

    public void addPassenger(String name) { passengers.add(name); }
    public String boardNext() { return passengers.poll(); }
}