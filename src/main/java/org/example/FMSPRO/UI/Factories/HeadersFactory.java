package org.example.FMSPRO.UI.Factories;

import javax.swing.*;
import java.awt.*;

public class HeadersFactory {
    private static final Color MAIN_COLOR = new Color(255, 98, 0);
    private static final String FONT = "SansSerif";


    public static JPanel createHeader(String label_text) {
        JPanel header = new JPanel();
        header.setBackground(Color.BLACK);
        JLabel label = new JLabel(label_text);
        label.setForeground(MAIN_COLOR);
        label.setFont(new Font(FONT, Font.BOLD, 22));
        header.add(label);
        return header;
    }

}
