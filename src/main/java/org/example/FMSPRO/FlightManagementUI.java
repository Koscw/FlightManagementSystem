package org.example.FMSPRO;

import org.example.FMSPRO.UI.*;
import org.example.FMSPRO.UI.Factories.ButtonsFactory;
import org.example.FMSPRO.UI.Panels.BoardingUI;
import org.example.FMSPRO.UI.Panels.RunwayUI;
import org.example.FMSPRO.UI.Panels.StorageUI;

import javax.swing.*;
import java.awt.*;


public class FlightManagementUI implements Runnable {
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "SKY-TRACK PRO | Flight Management";
    private JFrame frame;

    private final IPanelUI boardingUI;
    private final IPanelUI storageUI;
    private final IPanelUI runwayUI;
    private PanelsControl panels;

    public FlightManagementUI(RunwayRequests runaway) {
        FlightStorage storage = new FlightStorage();
        runwayUI = new RunwayUI(runaway);
        storageUI = new StorageUI(storage);
        boardingUI = new BoardingUI();
    }

    public void createAndShowGUI() {
        setupFrame();

        panels = new PanelsControl(new CardLayout());

        JPanel controls = new JPanel();
        addPanelWithButton(runwayUI, controls);
        addPanelWithButton(storageUI, controls);
        addPanelWithButton(boardingUI, controls);

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
