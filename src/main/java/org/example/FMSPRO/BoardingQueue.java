package org.example.FMSPRO;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class BoardingQueue {
    private PriorityQueue<String> passengers = new PriorityQueue<>();

    public void addPassenger(String name) { passengers.add(name); }
    public String boardNext() { return passengers.poll(); }
}