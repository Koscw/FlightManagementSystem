package org.example.FMSPRO.UI.Panels;

import org.example.FMSPRO.*;
import org.example.FMSPRO.Runway.IPrioritizedFlight;
import org.example.FMSPRO.Runway.PrioritizedFlight;
import org.example.FMSPRO.Runway.RunwayRequests;
import org.example.FMSPRO.UI.Factories.ButtonsFactory;
import org.example.FMSPRO.UI.Factories.HeadersFactory;
import org.example.FMSPRO.UI.Factories.ScrollPaneFactory;
import org.example.FMSPRO.UI.Factories.TablesFactory;
import org.example.FMSPRO.UI.IPanelUI;
import org.example.FMSPRO.UI.PanelsIds;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

import static org.example.FMSPRO.Constants.AIRPORT_NAME;

public class RunwayUI implements IPanelUI {

    private static final PanelsIds PANEL_ID = PanelsIds.Runaway;
    private static final String LABEL = "RUNWAY CONTROL DASHBOARD";
    private static final String FLIGHT_ID_COLUMN = "Flight ID";
    private static final String DESTINATION_COLUMN = "Destination";
    private static final String SOURCE_COLUMN = "Source";
    private static final String PRIORITY_COLUMN = "Priority Level";
    private static final String ADD_LABEL = "Log New Flight";
    private static final String LAND_LABEL = "Clear Next Landing";
    private static final String SELECT_PRIORITY = "Select Priority:";
    private static final String FLIGHT_ID_PROMPT = "Enter Flight Number:";
    private static final String SOURCE_PROMPT = "Enter Source:";

    private static final List<Priority> PRIORIIES = new ArrayList<>() {{
        add(Priority.EMERGENCY);
        add(Priority.URGENT);
        add(Priority.ROUTINE);
    }};

    public static final String MISSING_FLIGHTS = "No flights in queue.";

    private final RunwayRequests runway;
    private DefaultTableModel dataModel;
    private JPanel panel;

    public RunwayUI(RunwayRequests runway) {
        this.runway = runway;
    }

    @Override
    public void setupGUI() {
        setupDataModel();

        panel = new JPanel(new BorderLayout());

        JPanel header = HeadersFactory.createHeader(LABEL);
        JTable table = TablesFactory.createTable(dataModel);
        JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(table);
        JPanel controls = setupControls();

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(controls, BorderLayout.SOUTH);
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


    private JPanel setupControls() {
        JPanel controls = new JPanel(new FlowLayout());
        controls.setBackground(Color.BLACK);
        ButtonsFactory.addButton(controls, ADD_LABEL, Constants.SECOND_COLOR, this::onAddAction);
        ButtonsFactory.addButton(controls, LAND_LABEL, Constants.SECOND_COLOR, this::onLandAction);
        return controls;
    }


    private void setupDataModel() {
        String[] columns = {FLIGHT_ID_COLUMN, DESTINATION_COLUMN, SOURCE_COLUMN, PRIORITY_COLUMN};
        dataModel = new DefaultTableModel(columns, 0);
    }


    private void onLandAction(ActionEvent event) {
        if (runway.hasFlights()) {
            IFlight landed = runway.processNext();
            JOptionPane.showMessageDialog(panel, "Landed: " + landed.getNumber());
            refreshTable();
        } else {
            JOptionPane.showMessageDialog(panel, MISSING_FLIGHTS);
        }
    }

    private void onAddAction(ActionEvent event) {
        String id = JOptionPane.showInputDialog(panel, FLIGHT_ID_PROMPT);

        if (runway.isNumberExists(id)) {
            JOptionPane.showMessageDialog(panel, "Error. This ID already in use.");
            return;
        }

        String source = JOptionPane.showInputDialog(panel, SOURCE_PROMPT);
        Object[] priorities = PRIORIIES.stream().map(Priority::getLabel).toArray();
        String priority = (String) JOptionPane.showInputDialog(panel, SELECT_PRIORITY, SELECT_PRIORITY,
                JOptionPane.QUESTION_MESSAGE, null, priorities, Priority.ROUTINE);

        if (id == null || source == null || priority == null) {
            throw new NullPointerException();
        }

        Optional<Priority> score = PRIORIIES.stream()
                .filter(p -> Objects.equals(p.getLabel(), priority))
                .findAny();

        if (score.isEmpty())
            throw new IndexOutOfBoundsException();

        IPrioritizedFlight flight = new PrioritizedFlight(id, source, AIRPORT_NAME, score.get());
        runway.addFlight(flight);
        System.out.println("has flights: " + runway.hasFlights());
        System.out.println("iterator has next: " + runway.getFlights().hasNext());
        refreshTable();
    }

    private void refreshTable() {
        dataModel.setRowCount(0);
        for (Iterator<IPrioritizedFlight> it = runway.getFlights(); it.hasNext(); ) {
            IPrioritizedFlight flight = it.next();
            addRow(flight);
        }
    }

    private void addRow(IPrioritizedFlight flight) {
        dataModel.addRow(new Object[]{
                flight.getNumber(),
                flight.getSource(),
                flight.getDestination(),
                flight.getPriority().getLabel()
        });
    }
}
