package src.client;

import javax.swing.*;
import javax.swing.table.*;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

public class Search {
    private DefaultTableModel model;
    private JCheckBox checkBox52, checkBox53, checkBox54, checkBox63;
    private JCheckBox checkBoxPower, checkBoxLargeDesk;
    private JComboBox<String> dayComboBox;
    private JCheckBox[] timeCheckBoxes;

    public Search(DefaultTableModel model, JCheckBox checkBox52, JCheckBox checkBox53, JCheckBox checkBox54,
            JCheckBox checkBox63, JCheckBox checkBoxPower, JCheckBox checkBoxLargeDesk, JComboBox<String> dayComboBox,
            JCheckBox[] timeCheckBoxes) {
        this.model = model;
        this.checkBox52 = checkBox52;
        this.checkBox53 = checkBox53;
        this.checkBox54 = checkBox54;
        this.checkBox63 = checkBox63;
        this.checkBoxPower = checkBoxPower;
        this.checkBoxLargeDesk = checkBoxLargeDesk;
        this.dayComboBox = dayComboBox;
        this.timeCheckBoxes = timeCheckBoxes;
    }

    public void showFilteredResults() {
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        List<RowFilter<Object, Object>> locationFilters = new ArrayList<>();
        if (checkBox52.isSelected()) {
            locationFilters.add(RowFilter.regexFilter("52号館", 1));
        }
        if (checkBox53.isSelected()) {
            locationFilters.add(RowFilter.regexFilter("53号館", 1));
        }
        if (checkBox54.isSelected()) {
            locationFilters.add(RowFilter.regexFilter("54号館", 1));
        }
        if (checkBox63.isSelected()) {
            locationFilters.add(RowFilter.regexFilter("63号館", 1));
        }
        if (!locationFilters.isEmpty()) {
            filters.add(RowFilter.orFilter(locationFilters));
        }

        if (checkBoxPower.isSelected()) {
            filters.add(RowFilter.regexFilter("電源あり", 2));
        }
        if (checkBoxLargeDesk.isSelected()) {
            filters.add(RowFilter.regexFilter("机が広い", 3));
        }

        String selectedDay = (String) dayComboBox.getSelectedItem();
        List<RowFilter<Object, Object>> timeFilters = new ArrayList<>();
        for (int i = 0; i < timeCheckBoxes.length; i++) {
            if (timeCheckBoxes[i].isSelected()) {
                timeFilters.add(RowFilter.regexFilter(selectedDay + (i + 1) + "限", 4));
            }
        }
        if (!timeFilters.isEmpty()) {
            filters.add(RowFilter.andFilter(timeFilters));
        }

        RowFilter<Object, Object> compoundRowFilter = RowFilter.andFilter(filters);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        sorter.setRowFilter(compoundRowFilter);

        List<Object[]> filteredData = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            if (sorter.getViewRowCount() > i) {
                int modelRow = sorter.convertRowIndexToModel(i);
                Object[] row = new Object[model.getColumnCount()];
                for (int j = 0; j < model.getColumnCount(); j++) {
                    row[j] = model.getValueAt(modelRow, j);
                }
                filteredData.add(row);
            }
        }

        displayInNewWindow(filteredData);
    }

    private void displayInNewWindow(List<Object[]> filteredData) {
        JFrame resultFrame = new JFrame("Filtered Results");
        resultFrame.setSize(800, 600);

        String[] columnNames = { "Name", "Location", "Power", "Large Desk" };
        DefaultTableModel filteredModel = new DefaultTableModel(columnNames, 0);

        for (Object[] row : filteredData) {
            Object[] newRow = new Object[4];
            for (int j = 0; j < newRow.length; j++) {
                newRow[j] = row[j];
            }
            filteredModel.addRow(newRow);
        }

        JTable filteredTable = new JTable(filteredModel);
        JScrollPane scrollPane = new JScrollPane(filteredTable);

        JButton backButton = new JButton("戻る");
        backButton.addActionListener(e -> {
            resultFrame.dispose();
        });

        resultFrame.add(scrollPane, BorderLayout.CENTER);
        resultFrame.add(backButton, BorderLayout.SOUTH);
        resultFrame.setVisible(true);
    }
}
