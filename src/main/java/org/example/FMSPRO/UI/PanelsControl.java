package org.example.FMSPRO.UI;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class PanelsControl {
    private final HashMap<PanelsIds, String> names = new HashMap<>();
    private final HashMap<PanelsIds, Runnable> callbacks = new HashMap<>();

    private final CardLayout cardLayout;
    private final JPanel rootPanel;

    public PanelsControl(CardLayout cardLayout) {
        this.cardLayout = cardLayout;
        rootPanel = new JPanel(cardLayout);
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public void add(PanelsIds panelId, JPanel panel, Runnable onShow) {
        names.put(panelId, panelId.name());
        rootPanel.add(panel, panelId.name());
        callbacks.put(panelId, onShow);
    }

    public void toggle(PanelsIds panelId) {
        String name = names.get(panelId);
        Runnable callback = callbacks.get(panelId);

        cardLayout.show(rootPanel, name);
        callback.run();
    }
}

