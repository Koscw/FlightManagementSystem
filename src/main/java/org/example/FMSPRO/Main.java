package org.example.FMSPRO;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new FlightManagementUI(new LandingRequests()));
    }
}