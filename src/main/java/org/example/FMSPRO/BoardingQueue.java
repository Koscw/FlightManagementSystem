package org.example.FMSPRO;

import java.util.LinkedList;
import java.util.Queue;

public class BoardingQueue {
    private Queue<String> passengers = new LinkedList<>();

    public void addPassenger(String name) { passengers.add(name); }
    public String boardNext() { return passengers.poll(); }
}