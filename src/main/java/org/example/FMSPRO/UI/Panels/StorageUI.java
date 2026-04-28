package org.example.FMSPRO.UI.Panels;

import org.example.FMSPRO.*;
import org.example.FMSPRO.Storage.IFlightManager;
import org.example.FMSPRO.UI.Factories.ButtonsFactory;
import org.example.FMSPRO.UI.Factories.HeadersFactory;
import org.example.FMSPRO.UI.Factories.ScrollPaneFactory;
import org.example.FMSPRO.UI.Factories.TablesFactory;
import org.example.FMSPRO.UI.IPanelUI;
import org.example.FMSPRO.UI.PanelsIds;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Objects;

import static org.example.FMSPRO.Constants.AIRPORT_NAME;

public class StorageUI implements IPanelUI {

    private static final PanelsIds PANEL_ID = PanelsIds.Storage;
    private static final String LABEL = "STORAGE DASHBOARD";
    private static final String FLIGHT_ID_COLUMN = "Flight ID";
    private static final String DESTINATION_COLUMN = "Destination";
    private static final String GATE_COLUMN = "Gate";
    public static final String STATUS_COLUMN = "Status";

    private JPanel panel;
    private DefaultTableModel dataModel;
    private final IFlightManager storage;

    public StorageUI(IFlightManager storage) {
        this.storage = storage;
    }

    @Override
    public void setupGUI() {
        setupDataModel();
        panel = new JPanel(new BorderLayout());

        JPanel header = HeadersFactory.createHeader(LABEL);
        JPanel buttons = new JPanel();
        ButtonsFactory.addButton(buttons, "Schedule Flight", Constants.SECOND_COLOR, this::OnAddAction);
        JTable table = TablesFactory.createTable(dataModel);

        setTableEditors(table);

        JScrollPane scrollPane = ScrollPaneFactory.createScrollPane(table);

        panel.add(header, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttons, BorderLayout.SOUTH);
    }

    private static void setTableEditors(JTable table) {
        JComboBox<String> statusBox = new JComboBox<>(
                new String[]{BoardingStatuses.Closed.name(), BoardingStatuses.Delayed.name(), BoardingStatuses.Boarding.name()}
        );

        table.getColumn(FLIGHT_ID_COLUMN).setCellEditor(null);
        table.getColumn(DESTINATION_COLUMN).setCellEditor(null);
        table.getColumn(GATE_COLUMN).setCellEditor(null);
        table.getColumn(STATUS_COLUMN).setCellEditor(new DefaultCellEditor(statusBox));
    }


    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public PanelsIds getPanelId() {
        return PANEL_ID;
    }

    public static PanelsIds GetPanelId() {
        return PANEL_ID;
    }

    @Override
    public void onShow() {
        refreshTable();
    }

    private void setupDataModel() {
        String[] columns = {FLIGHT_ID_COLUMN, DESTINATION_COLUMN, GATE_COLUMN, STATUS_COLUMN};
        dataModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return getColumnName(column).equals(STATUS_COLUMN);
            }
        };

        dataModel.addTableModelListener(this::onTableEdited);
    }

    private void onTableEdited(TableModelEvent event) {
        if (event.getType() != TableModelEvent.UPDATE) {
            return;
        }

        if (!dataModel.getColumnName(event.getColumn()).equals(STATUS_COLUMN))
            throw new IllegalStateException();

        int row = event.getFirstRow();
        int column = event.getColumn();
        String flightId = (String) dataModel.getValueAt(row, getColumnId(FLIGHT_ID_COLUMN));
        String status = (String) dataModel.getValueAt(row, column);

        if (status == null || status.isEmpty()) {
            refreshTable();
            return;
        }

        storage.updateFlightStatus(flightId, BoardingStatuses.valueOf(status));
        refreshTable();
    }

    private int getColumnId(String name) {
        for (int i = 0; i < dataModel.getColumnCount(); i++) {
            if (Objects.equals(dataModel.getColumnName(i), name))
                return i;
        }

        return -1;
    }

    private void refreshTable() {
        dataModel.setRowCount(0);
        for (IBoardingFlight flight : storage.getFlights()) {
            addRow(flight);
        }
    }

    private void OnAddAction(ActionEvent event) {
        String number = JOptionPane.showInputDialog(panel, "Enter Number:");

        if (storage.isFlightExists(number)) {
            JOptionPane.showMessageDialog(panel, "Error. Such id already registered");
            return;
        }

        String destination = JOptionPane.showInputDialog(panel, "Enter Destination:");
        String gate = JOptionPane.showInputDialog(panel, "Enter Gate:");

        if (storage.isGateInUse(gate)) {
            JOptionPane.showMessageDialog(panel, "Error. The gate already busy");
            return;
        }

        if (number == null || destination == null || gate == null) {
            throw new IndexOutOfBoundsException();
        }

        storage.addFlight(new BoardingFlight(number, AIRPORT_NAME, destination, gate));

        refreshTable();
    }

    private void addRow(IBoardingFlight flight) {
        dataModel.addRow(new Object[]{
                flight.getNumber(),
                flight.getDestination(),
                flight.getGate(),
                flight.getStatus().name(),
        });
    }

}