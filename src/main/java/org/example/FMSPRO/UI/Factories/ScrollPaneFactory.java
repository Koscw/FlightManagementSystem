package org.example.FMSPRO.UI.Factories;

import javax.swing.*;
import java.awt.*;

public class ScrollPaneFactory {

    public static JScrollPane createScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }
}
