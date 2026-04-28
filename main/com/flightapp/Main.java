package main.com.flightapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.LineBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

// Mocking models to ensure the code is "Ready to Run"
class Flight {
    private String id;
    private String destination;
    private int priority;

    public Flight(String id, String destination, int priority) {
        this.id = id;
        this.destination = destination;
        this.priority = priority;
    }

    public String getId() { return id; }
    public String getDestination() { return destination; }
    public int getPriority() { return priority; }
}

public class Main {
    // Functional Components
    private ArrayList<Flight> flightList = new ArrayList<>();
    private PriorityQueue<Flight> runwayQueue = new PriorityQueue<>(Comparator.comparingInt(Flight::getPriority));
    private DefaultTableModel tableModel;

    // Design Palette
    private final Color PITCH_BLACK = new Color(0, 0, 0);
    private final Color SURFACE_DARK = new Color(12, 12, 12);
    private final Color CYBER_BLUE = new Color(0, 150, 255);
    private final Color WHITE_GLOW = new Color(255, 255, 255);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("SKY-TRACK PRO | FLIGHT_OPS");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 650);
        frame.getContentPane().setBackground(PITCH_BLACK);
        frame.setLayout(new BorderLayout(0, 0));

        // --- HEADER ---
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(PITCH_BLACK);
        header.setPreferredSize(new Dimension(1000, 80));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(40, 40, 40)));

        JLabel title = new JLabel("  FLIGHT CONTROL UNIT // SYSTEM_READY");
        title.setForeground(WHITE_GLOW);
        title.setFont(new Font("Monospaced", Font.BOLD, 18));
        header.add(title, BorderLayout.WEST);

        // --- TABLE (Data Box) ---
        String[] columns = {"ID_CODE", "DESTINATION_NODE", "PRIORITY_LVL"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        
        table.setBackground(PITCH_BLACK);
        table.setForeground(new Color(220, 220, 220));
        table.setGridColor(new Color(25, 25, 25));
        table.setRowHeight(40);
        table.setFont(new Font("SansSerif", Font.PLAIN, 14));
        table.setSelectionBackground(new Color(20, 20, 20));
        table.setSelectionForeground(CYBER_BLUE);
        table.setShowVerticalLines(false);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setBackground(PITCH_BLACK);
        tableHeader.setForeground(CYBER_BLUE);
        tableHeader.setFont(new Font("Monospaced", Font.BOLD, 12));
        tableHeader.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, CYBER_BLUE));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(PITCH_BLACK);
        scrollPane.setBorder(new EmptyBorder(25, 25, 25, 25));

        // --- FOOTER (The Clear Professional Buttons) ---
        JPanel footer = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 25));
        footer.setBackground(PITCH_BLACK);
        footer.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, SURFACE_DARK));

        JButton btnAdd = createStyledButton("ADD NEW ENTRY", CYBER_BLUE);
        JButton btnLand = createStyledButton("CLEAR LANDING", WHITE_GLOW);
        
        footer.add(btnLand);
        footer.add(btnAdd);

        // --- BUTTON LOGIC ---
        btnAdd.addActionListener(e -> {
            JTextField idField = new JTextField();
            JTextField destField = new JTextField();
            String[] priorities = {"1 - Emergency", "2 - Urgent", "3 - Routine"};
            JComboBox<String> pCombo = new JComboBox<>(priorities);

            Object[] message = { "Flight ID:", idField, "Destination:", destField, "Priority:", pCombo };

            int option = JOptionPane.showConfirmDialog(frame, message, "Log Flight Data", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                int pVal = Character.getNumericValue(pCombo.getSelectedItem().toString().charAt(0));
                Flight f = new Flight(idField.getText(), destField.getText(), pVal);
                flightList.add(f);
                runwayQueue.add(f);
                refreshTable();
            }
        });

        btnLand.addActionListener(e -> {
            if (!runwayQueue.isEmpty()) {
                Flight landed = runwayQueue.poll();
                flightList.remove(landed);
                JOptionPane.showMessageDialog(frame, "LANDING EXECUTED: " + landed.getId());
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "RUNWAY_QUEUE_EMPTY");
            }
        });

        frame.add(header, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(footer, BorderLayout.SOUTH);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0); // Reset the "Box"
        for (Flight f : flightList) {
            tableModel.addRow(new Object[]{f.getId(), f.getDestination(), "LVL_" + f.getPriority()});
        }
    }

    private JButton createStyledButton(String text, Color accent) {
        JButton b = new JButton(text);
        b.setFont(new Font("Monospaced", Font.BOLD, 12));
        b.setForeground(accent);
        b.setBackground(PITCH_BLACK);
        b.setFocusPainted(false);
        b.setContentAreaFilled(false);
        b.setOpaque(true);
        b.setBorder(new LineBorder(accent, 1));
        b.setPreferredSize(new Dimension(190, 42));
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));

        b.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                b.setBackground(accent);
                b.setForeground(PITCH_BLACK);
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                b.setBackground(PITCH_BLACK);
                b.setForeground(accent);
            }
        });
        return b;
    }
}