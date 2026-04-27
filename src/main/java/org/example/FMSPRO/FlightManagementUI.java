package org.example.FMSPRO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FlightManagementUI implements Runnable {
    private static final int FRAME_WIDTH = 900;
    private static final int FRAME_HEIGHT = 600;
    private static final String FRAME_TITLE = "SKY-TRACK PRO | Flight Management";
    private static final String LABEL = "AIRPORT CONTROL DASHBOARD";
    private static final String FLIGHT_ID_COLUMN = "Flight ID";
    private static final String DESTINATION_COLUMN = "Destination";
    private static final String PRIORITY_COLUMN = "Priority Level";
    private static final String FONT = "SansSerif";
    private static final String ADD_LABEL = "Log New Flight";
    private static final String LAND_LABEL = "Clear Next Landing";
    private static final Color MAIN_COLOR = new Color(255, 98, 0);
    private static final Color SECOND_COLOR = new Color(0, 150, 255);
    private static final Color BACKGROUND_COLOR = new Color(18, 18, 18);
    private static final String EMERGENCY = "Emergency";
    private static final String URGENT = "Urgent";
    private static final String ROUTINE = "Routine";
    private static final String SELECT_PRIORITY = "Select Priority:";
    private static final String FLIGHT_ID_PROMT = "Enter Flight Number:";
    private static final String DESTINATION_PROMPT = "Enter Destination:";

    private static final Map<String, Integer> PRIORITIES = new HashMap<>() {{
        put(ROUTINE, 1);
        put(URGENT, 2);
        put(EMERGENCY, 3);
    }};

    private final LandingRequests runway;
    private DefaultTableModel dataModel;
    private JFrame frame;

    public FlightManagementUI(LandingRequests runaway) {
        this.runway = runaway;
    }

    public void createAndShowGUI() {
        setupFrame();
        setupDataModel();

        JPanel header = createHeader();
        JTable table = createTable();
        JScrollPane scrollPane = createScrollPane(table);
        JPanel controls = setupControls();

        addWidgets(header, scrollPane, controls);
        frame.setVisible(true);
    }

    private JPanel setupControls() {
        JPanel controls = new JPanel(new FlowLayout());
        controls.setBackground(Color.BLACK);
        addButton(ADD_LABEL, MAIN_COLOR, controls, this::onAddAction);
        addButton(LAND_LABEL, SECOND_COLOR, controls, this::onLandAction);
        return controls;
    }

    private void addButton(String label, Color color, JPanel controls, ActionListener listener) {
        JButton btnAdd = createStyledButton(label, color);
        controls.add(btnAdd);
        btnAdd.addActionListener(listener);
    }

    private void setupDataModel() {
        String[] columns = {FLIGHT_ID_COLUMN, DESTINATION_COLUMN, PRIORITY_COLUMN};
        dataModel = new DefaultTableModel(columns, 0);
    }

    private void setupFrame() {
        frame = new JFrame(FRAME_TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.getContentPane().setBackground(BACKGROUND_COLOR);
    }

    private static JScrollPane createScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(BACKGROUND_COLOR);
        return scrollPane;
    }

    private JTable createTable() {
        JTable table = new JTable(dataModel);
        table.setBackground(BACKGROUND_COLOR);
        table.setForeground(Color.WHITE);
        table.setGridColor(MAIN_COLOR);
        table.setRowHeight(25);
        return table;
    }

    private static JPanel createHeader() {
        JPanel header = new JPanel();
        header.setBackground(Color.BLACK);
        JLabel label = new JLabel(LABEL);
        label.setForeground(MAIN_COLOR);
        label.setFont(new Font(FONT, Font.BOLD, 22));
        header.add(label);
        return header;
    }

    private void addWidgets(JPanel header, JScrollPane scrollPane, JPanel controls) {
        frame.add(header, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);
    }

    private void onLandAction(ActionEvent event) {
        if (runway.hasFlights()) {
            Flight landed = runway.processNext();
            JOptionPane.showMessageDialog(frame, "LANDING CLEARED: " + landed.getNumber());
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(frame, "No flights in queue.");
        }

    }

    private void onAddAction(ActionEvent event) {
        String id = JOptionPane.showInputDialog(frame, FLIGHT_ID_PROMT);
        String destination = JOptionPane.showInputDialog(frame, DESTINATION_PROMPT);
        String[] priorities = PRIORITIES.keySet().toArray(new String[0]);
        String priority = (String) JOptionPane.showInputDialog(frame, SELECT_PRIORITY, SELECT_PRIORITY,
                JOptionPane.QUESTION_MESSAGE, null, priorities, ROUTINE);

        if (id == null || destination == null || priority == null) {
            throw new NullPointerException();
        }

        Integer index = PRIORITIES.getOrDefault(priority, null);

        if (index == null)
            throw new IndexOutOfBoundsException();

        Flight flight = new Flight(id, destination, index);
        runway.addFlight(flight);
        refreshTable();
    }

    private void refreshTable() {
        dataModel.setRowCount(0);
        for (Iterator<Flight> it = runway.getFlights(); it.hasNext(); ) {
            Flight flight = it.next();
            addRow(flight);
        }
    }

    private void addRow(Flight flight) {
        dataModel.addRow(new Object[]{
                flight.getNumber(),
                flight.getDestination(),
                flight.getPriorityScore()
        });
    }

    private JButton createStyledButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setBackground(color);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        return b;
    }

    @Override
    public void run() {
        createAndShowGUI();
    }
}
