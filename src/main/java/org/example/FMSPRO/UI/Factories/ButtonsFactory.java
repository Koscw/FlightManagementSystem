package org.example.FMSPRO.UI.Factories;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ButtonsFactory {

    private static final Font BUTTON_FONT = new Font("SansSerif", Font.BOLD, 14);

    public static void addButton(JPanel controls, String label, Color color, ActionListener listener) {
        JButton btnAdd = createStyledButton(label, color);
        controls.add(btnAdd);
        btnAdd.addActionListener(listener);
    }

    private static JButton createStyledButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setBackground(color);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setFont(BUTTON_FONT);
        return b;
    }
}
