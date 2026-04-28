package org.example.FMSPRO;

import org.example.FMSPRO.Runway.RunwayRequests;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FlightManagementUI(new RunwayRequests()));
    }
}