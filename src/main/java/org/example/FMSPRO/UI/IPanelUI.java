package org.example.FMSPRO.UI;

import javax.swing.*;

public interface IPanelUI {
    void setupGUI();

    JPanel getPanel();

    PanelsIds getPanelId();

    void onShow();
}
