package org.example.FMSPRO.UI.Panels;

import org.example.FMSPRO.UI.IPanelUI;
import org.example.FMSPRO.UI.PanelsIds;

import javax.swing.*;
import java.awt.*;

public class BoardingUI implements IPanelUI {

    private static final PanelsIds PANEL_ID = PanelsIds.Boarding;
    private JPanel panel;

    @Override
    public void setupGUI() {
        panel = new JPanel(new BorderLayout());
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

