package org.example.FMSPRO.UI.Panels;

import org.example.FMSPRO.Boarding.BoardingStatuses;
import org.example.FMSPRO.Boarding.ICheckinPassenger;
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

public class CheckInUI implements IPanelUI {

    private static final PanelsIds PANEL_ID = PanelsIds.CheckIn;
    public static final String LABEL = "CHECK IN DASHBOARD";
    private JPanel panel;
    private final ICheckinPassenger boardingManager;
    private final IFlightManager flightManager;

    public CheckInUI(ICheckinPassenger boardingManager, IFlightManager flightManager) {
        this.boardingManager = boardingManager;
        this.flightManager = flightManager;
    }

    @Override
    public void setupGUI() {
        panel = new JPanel(new BorderLayout());

        JPanel buttons = new JPanel();
        ButtonsFactory.addButton(buttons, "Check In", Constants.SECOND_COLOR, this::onCheckInClicked);
        panel.add(HeadersFactory.createHeader(LABEL), BorderLayout.NORTH);
        panel.add(buttons, BorderLayout.CENTER);
    }

    private void onCheckInClicked(ActionEvent actionEvent) {
        String[] flights = Arrays.stream(flightManager.getFlights())
                .filter(f -> f.getStatus() == BoardingStatuses.Closed || f.getStatus() == BoardingStatuses.Delayed)
                .map(IFlight::getNumber)
                .toArray(String[]::new);

        if (flights.length == 0) {
            JOptionPane.showMessageDialog(panel, String.format("No flights with status \"%s\" or \"%s\" are exist. Try to create using \"%s\"",
                    BoardingStatuses.Closed.name(), BoardingStatuses.Delayed.name(), StorageUI.GetPanelId()));
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

        String name = JOptionPane.showInputDialog(panel, "Enter your full name:");

        if (name == null || flightId == null)
            return;

        boardingManager.addPassenger(flightId, name);
        JOptionPane.showMessageDialog(panel, "Successfully checked in!");
    }


    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public PanelsIds getPanelId() {
        return PANEL_ID;
    }

    public static PanelsIds GetPanelId() {
        return PANEL_ID;
    }

    @Override
    public void onShow() {

    }
}

