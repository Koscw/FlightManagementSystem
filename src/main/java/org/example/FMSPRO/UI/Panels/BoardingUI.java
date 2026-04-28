package org.example.FMSPRO.UI.Panels;

import org.example.FMSPRO.Boarding.BoardingManager;
import org.example.FMSPRO.Boarding.BoardingStatuses;
import org.example.FMSPRO.Boarding.IBoardingFlight;
import org.example.FMSPRO.Common.Constants;
import org.example.FMSPRO.Common.IFlight;
import org.example.FMSPRO.Storage.IFlightManager;
import org.example.FMSPRO.UI.Factories.ButtonsFactory;
import org.example.FMSPRO.UI.Factories.HeadersFactory;
import org.example.FMSPRO.UI.IPanelUI;
import org.example.FMSPRO.UI.PanelsIds;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class BoardingUI implements IPanelUI {

    private static final PanelsIds PANEL_ID = PanelsIds.Boarding;
    private JPanel panel;

    private final IFlightManager flightManager;
    private final BoardingManager boardingManager;

    public BoardingUI(IFlightManager flightManager, BoardingManager boardingManager) {
        this.flightManager = flightManager;
        this.boardingManager = boardingManager;
    }

    @Override
    public void setupGUI() {
        panel = new JPanel(new BorderLayout());

        JPanel buttons = new JPanel();
        ButtonsFactory.addButton(buttons, "Boarding", Constants.SECOND_COLOR, this::onBoardingCLicked);
        panel.add(buttons, BorderLayout.CENTER);
        panel.add(HeadersFactory.createHeader("BOARDING DASHBOARD"), BorderLayout.NORTH);
    }

    private void onBoardingCLicked(ActionEvent actionEvent) {
        String[] flights = Arrays.stream(flightManager.getFlights())
                .filter(f -> f.getStatus() == BoardingStatuses.Boarding)
                .map(IFlight::getNumber)
                .toArray(String[]::new);

        if (flights.length == 0) {
            JOptionPane.showMessageDialog(panel, String.format("No flights with status \"%s\" are exist. Try to create using \"%s\"",
                    BoardingStatuses.Boarding.name(), StorageUI.GetPanelId()));
            return;
        }

        String flightId = (String) JOptionPane.showInputDialog(
                panel,
                "Choose the flight id:",
                "Flight selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                flights,
                flights[0]
        );

        if (!boardingManager.hasPassengers(flightId)) {
            JOptionPane.showMessageDialog(panel, String.format("No passengers to board. Try to check in using \"%s\"",
                    CheckInUI.GetPanelId()));
            return;
        }
        IBoardingFlight flight = flightManager.getFlight(flightId);
        keepBoardPeople(flightId, flight);
        JOptionPane.showMessageDialog(panel, "All the passengers got boarded. Deleting the flight.");
        flightManager.deleteFlight(flightId);


    }

    private void keepBoardPeople(String flightId, IBoardingFlight flight) {
        while (boardingManager.hasPassengers(flightId)) {
            String name = boardingManager.boardNextPassenger(flightId);

            JOptionPane.showMessageDialog(panel, String.format("Please ask to proceed %s to the gate #%s",
                    name, flight.getGate()));
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public PanelsIds getPanelId() {
        return PANEL_ID;
    }

    @Override
    public void onShow() {

    }
}

