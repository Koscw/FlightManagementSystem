package org.example.FMSPRO.UI.Panels;

import org.example.FMSPRO.Constants;
import org.example.FMSPRO.Routes.ConnectionNode;
import org.example.FMSPRO.Routes.RouteNetwork;
import org.example.FMSPRO.UI.Factories.ButtonsFactory;
import org.example.FMSPRO.UI.Factories.ScrollPaneFactory;
import org.example.FMSPRO.UI.Factories.TablesFactory;
import org.example.FMSPRO.UI.IPanelUI;
import org.example.FMSPRO.UI.PanelsIds;
import org.example.FMSPRO.Routes.Path;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class RoutesUI implements IPanelUI {

    private static final PanelsIds PANEL_ID = PanelsIds.Routes;
    private JPanel panel;
    private DefaultTableModel dataModel;
    private RouteNetwork routeNetwork;

    @Override
    public void setupGUI() {
        setupDataModel();
        routeNetwork = new RouteNetwork();
        panel = new JPanel(new BorderLayout());
        JPanel buttons = getButtons();
        JTable table = TablesFactory.createTable(dataModel);
        JScrollPane pane = ScrollPaneFactory.createScrollPane(table);

        panel.add(pane, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);
    }

    private JPanel getButtons() {
        JPanel buttons = new JPanel();
        ButtonsFactory.addButton(buttons, "Add Route", Constants.SECOND_COLOR, this::onAddClicked);
        ButtonsFactory.addButton(buttons, "Find shortest", Constants.SECOND_COLOR, this::onFindShortestClicked);
        ButtonsFactory.addButton(buttons, "Get all connections", Constants.SECOND_COLOR, this::onGetAllClicked);
        return buttons;
    }

    private void onGetAllClicked(ActionEvent actionEvent) {
        Map<String, List<ConnectionNode>> routes = routeNetwork.GetRoutes();
        if (routes.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "No routs exist.");
            return;
        }

        String[] keys = routes.keySet().toArray(new String[0]);
        String route = (String) JOptionPane.showInputDialog(
                panel,
                "Choose the airport:",
                "Airport selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                keys,
                keys[0]
        );

        if (route == null)
            return;

        JOptionPane.showMessageDialog(panel, getFormatedConnections(routes, route));

    }

    private static String getFormatedConnections(Map<String, List<ConnectionNode>> routes, String route) {
        List<ConnectionNode> nodes = routes.get(route);

        StringBuilder builder = new StringBuilder();
        builder.append(route);
        builder.append(" has connections with: ");

        for (ConnectionNode node : nodes) {
            builder.append(node.getDestination());
            builder.append('(');
            builder.append(node.getMillage());
            builder.append("miles) ");
        }

        return builder.toString();
    }

    private void onFindShortestClicked(ActionEvent actionEvent) {
        Map<String, List<ConnectionNode>> routes = routeNetwork.GetRoutes();
        if (routes.isEmpty()) {
            JOptionPane.showMessageDialog(panel, "No routs exist.");
            return;
        }

        String[] keys = routes.keySet().toArray(new String[0]);
        String source = (String) JOptionPane.showInputDialog(
                panel,
                "Point A:",
                "Airport selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                keys,
                keys[0]
        );

        String destination = (String) JOptionPane.showInputDialog(
                panel,
                "Point B:",
                "Airport selection",
                JOptionPane.QUESTION_MESSAGE,
                null,
                keys,
                keys[0]
        );

        if (source == null | destination == null)
            return;

        if (Objects.equals(source, destination)) {
            JOptionPane.showMessageDialog(panel, "Error. src can't be the same as dst.");
            return;
        }

        Path path = routeNetwork.findShortestPath(source, destination);
        if (!path.exists()) {
            JOptionPane.showMessageDialog(panel, "Error. Path does not exists");
            return;
        }

        JOptionPane.showMessageDialog(panel, getFormatedPath(path));
    }

    private static String getFormatedPath(Path path) {
        StringBuilder builder = new StringBuilder();
        List<String> nodes = path.getNodes();
        int nodesSize = nodes.size();
        for (int i = 0; i < nodesSize; i++) {
            builder.append(nodes.get(i));
            if (i + 1 < nodesSize) {
                builder.append(" -> ");
            }
        }

        builder.append(" Total millage: ");
        builder.append(path.getDistance());

        return builder.toString();
    }

    private void onAddClicked(ActionEvent actionEvent) {
        String source = JOptionPane.showInputDialog(panel, "Enter point A:");
        String destination = JOptionPane.showInputDialog(panel, "Enter point B:");
        Integer millage = getMillageFromUser();

        if (routeNetwork.hasDirectPath(source, destination)) {
            JOptionPane.showMessageDialog(panel, "Error. Path already exist");
            return;
        }

        routeNetwork.addRoute(source, destination, millage);
        refreshTable();
    }

    private Integer getMillageFromUser() {
        int millage = 0;
        boolean millageEntered = false;

        while (!millageEntered) {
            String input = JOptionPane.showInputDialog(panel, "Enter millage:");
            try {
                millage = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(panel, "Error. Not a number");
                continue;
            }

            if (millage <= 0) {
                JOptionPane.showMessageDialog(panel, "Error. Invalid range");
                continue;
            }

            millageEntered = true;
        }

        return millage;
    }

    private void setupDataModel() {
        String[] columns = {"POINT A", "POINT B", "Distance (Miles)"};
        dataModel = new DefaultTableModel(columns, 0);
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
        refreshTable();
    }

    private void refreshTable() {
        dataModel.setRowCount(0);
        Map<String, List<ConnectionNode>> routes = routeNetwork.GetRoutes();
        Set<String> keys = routes.keySet();
        for (String key : keys) {
            List<ConnectionNode> nodes = routes.get(key);
            for (ConnectionNode node : nodes) {
                dataModel.addRow(new Object[]{key, node.getDestination(), node.getMillage()});
            }
        }
    }

}

