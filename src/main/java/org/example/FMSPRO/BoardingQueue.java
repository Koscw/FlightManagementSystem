package org.example.FMSPRO;

import java.util.LinkedList;
import java.util.Queue;

public class BoardingQueue {
    private final Queue<String> passengers = new LinkedList<>();

    public String boardNext() {
        return passengers.poll();
    }

    public void addPassenger(String name) {
        passengers.add(name);
    }
}