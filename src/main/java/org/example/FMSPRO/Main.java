package org.example.FMSPRO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.example.FMSPRO.Flight;
import org.example.FMSPRO.RunwayTriage;


public class Main {
    private FlightManager manager = new FlightManager();
    private RunwayTriage runway = new RunwayTriage();
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    public void createAndShowGUI() {
        // Main Frame Setup
        JFrame frame = new JFrame("SKY-TRACK PRO | Flight Management");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.getContentPane().setBackground(new Color(18, 18, 18)); 


        JPanel header = new JPanel();
        header.setBackground(new Color(0, 0, 0));
        JLabel title = new JLabel("AIRPORT CONTROL DASHBOARD");
        title.setForeground(new Color(255, 98, 0));
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        header.add(title);


        String[] columns = {"Flight ID", "Destination", "Priority Level"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        table.setBackground(new Color(30, 30, 30));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(255, 98, 0));
        table.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(18, 18, 18));


        JPanel controls = new JPanel(new FlowLayout());
        controls.setBackground(new Color(0, 0, 0));

        JButton btnAdd = createStyledButton("Log New Flight", new Color(255, 98, 0));
        JButton btnLand = createStyledButton("Clear Next Landing", new Color(0, 150, 255));
        
        controls.add(btnAdd);
        controls.add(btnLand);


        btnAdd.addActionListener(e -> {
            String id = JOptionPane.showInputDialog(frame, "Enter Flight Number:");
            String dest = JOptionPane.showInputDialog(frame, "Enter Destination:");
            String[] priorities = {"1 - Emergency", "2 - Urgent", "3 - Routine"};
            String pStr = (String) JOptionPane.showInputDialog(frame, "Select Priority:", 
                          "Priority", JOptionPane.QUESTION_MESSAGE, null, priorities, priorities[2]);
            
            if (id != null && dest != null && pStr != null) {
                int p = Character.getNumericValue(pStr.charAt(0));
                Flight f = new Flight(id, dest, p);
                manager.addFlight(f);
                runway.requestLanding(f);
                refreshTable();
            }
        });

        btnLand.addActionListener(e -> {
            if (runway.hasFlights()) {
                Flight landed = runway.processNext();
                JOptionPane.showMessageDialog(frame, "LANDING CLEARED: " + landed.getFlightNumber());
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "No flights in queue.");
            }
        });

        frame.add(header, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(controls, BorderLayout.SOUTH);
        frame.setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for(Flight f: runway.getQueue()){
            tableModel.addRow(new Object[]{
                    f.getFlightNumber(),
                    f.getDestination(),
                    f.getPriorityScore()
            });
        }



    }

    private JButton createStyledButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setBackground(color);
        b.setForeground(Color.BLACK);
        b.setFocusPainted(false);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        return b;
    }
}