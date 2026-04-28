package org.example.FMSPRO.UI.Factories;

import org.example.FMSPRO.Common.Constants;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;


public class TablesFactory {

    public static JTable createTable(TableModel dataModel) {
        JTable table = new JTable(dataModel);
        table.setBackground(Constants.BACKGROUND_COLOR);
        table.setForeground(Color.WHITE);
        table.setGridColor(Constants.MAIN_COLOR);
        table.setRowHeight(25);
        return table;
    }

}
