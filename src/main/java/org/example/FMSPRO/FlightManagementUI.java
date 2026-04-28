package org.example.FMSPRO;

import org.example.FMSPRO.Boarding.BoardingManager;
import org.example.FMSPRO.Common.Constants;
import org.example.FMSPRO.Runway.RunwayRequests;
import org.example.FMSPRO.Storage.FlightManager;
import org.example.FMSPRO.UI.*;
import org.example.FMSPRO.UI.Factories.ButtonsFactory;
import org.example.FMSPRO.UI.Panels.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class FlightManagementUI implements Runnable {
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "Airport Flight Management And Scheduling System";
    private JFrame frame;

    private final IPanelUI routesUI;
    private final IPanelUI boardingUI;
    private final IPanelUI storageUI;
    private final IPanelUI runwayUI;
    private final IPanelUI checkinUI;

    private final List<IPanelUI> userInterfaces = new ArrayList<>();
    private PanelsControl panels;


    public FlightManagementUI(RunwayRequests runaway) {
        FlightManager storage = new FlightManager();
        BoardingManager boardingManager = new BoardingManager();
        storage.addListener(boardingManager);
        runwayUI = new RunwayUI(runaway);
        storageUI = new StorageUI(storage);
        routesUI = new RoutesUI();
        checkinUI = new CheckInUI(boardingManager, storage);
        boardingUI = new BoardingUI(storage, boardingManager);
        userInterfaces.addAll(List.of(new IPanelUI[]{runwayUI, storageUI, routesUI, checkinUI, boardingUI}));
    }

    public void createAndShowGUI() {
        setupFrame();

        panels = new PanelsControl(new CardLayout());

        JPanel controls = new JPanel();
        for (IPanelUI panel : userInterfaces) {
            addPanelWithButton(panel, controls);
        }

        addWidgets(controls);
        panels.toggle(PanelsIds.Runaway);
        frame.setVisible(true);
    }

    private void addWidgets(JPanel controls) {
        frame.add(panels.getRootPanel(), BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);
    }

    private void addPanelWithButton(IPanelUI panel, JPanel buttons) {
        panel.setupGUI();
        addPanel(panel);
        getAddButton(buttons, panel.getPanelId());
    }

    private void addPanel(IPanelUI panel) {
        panels.add(panel.getPanelId(), panel.getPanel(), panel::onShow);
    }

    private void getAddButton(JPanel buttons, PanelsIds panelId) {
        ButtonsFactory.addButton(buttons, panelId.name(), Constants.MAIN_COLOR, e -> panels.toggle(panelId));
    }

    private void setupFrame() {
        frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
    }

    @Override
    public void run() {
        createAndShowGUI();
    }
}
