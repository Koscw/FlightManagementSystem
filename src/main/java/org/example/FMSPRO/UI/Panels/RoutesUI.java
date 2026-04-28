package org.example.FMSPRO.UI.Panels;

import org.example.FMSPRO.Constants;
import org.example.FMSPRO.UI.Factories.ButtonsFactory;
import org.example.FMSPRO.UI.IPanelUI;
import org.example.FMSPRO.UI.PanelsIds;

import javax.swing.*;
import java.awt.*;

public class RoutesUI implements IPanelUI {

    private static final PanelsIds PANEL_ID = PanelsIds.Routes;
    private JPanel panel;

    @Override
    public void setupGUI() {
        panel = new JPanel(new BorderLayout());
        JPanel buttons = new JPanel();
        ButtonsFactory.addButton(buttons, "Add Route", Constants.SECOND_COLOR, e -> {});
//        buttons.add(ButtonsFactory)
//        TODO: impl
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

