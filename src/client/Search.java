package src.client;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Search {

    public void showFilteredResults(DefaultTableModel model, JCheckBox checkBox52, JCheckBox checkBox53,
            JCheckBox checkBox54, JCheckBox checkBox63, JCheckBox checkBoxPower, JCheckBox checkBoxLargeDesk,
            JComboBox<String> dayComboBox, JCheckBox[] timeCheckBoxes) {
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
                timeFilters.add(RowFilter.regexFilter(selectedDay + (i + 1) + "限", 5));
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

    public void showFilteredResultsForTimeSlot(DefaultTableModel model, JCheckBox checkBox52, JCheckBox checkBox53,
            JCheckBox checkBox54, JCheckBox checkBox63, JCheckBox checkBoxPower, JCheckBox checkBoxLargeDesk,
            String timeSlot) {
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

        String selectedDay = new SimpleDateFormat("EEEE").format(new Date());
        List<RowFilter<Object, Object>> timeFilters = new ArrayList<>();
        timeFilters.add(RowFilter.regexFilter(selectedDay + timeSlot, 4));

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

        String[] columnNames = { "Name", "Location", "Seats", "Outlets", "Desk Size" };
        DefaultTableModel filteredModel = new DefaultTableModel(columnNames, 0);

        for (Object[] row : filteredData) {
            Object[] newRow = new Object[5];
            for (int j = 0; j < newRow.length; j++) {
                newRow[j] = row[j];
            }
            filteredModel.addRow(newRow);
        }

        JTable filteredTable = new JTable(filteredModel);
        JScrollPane scrollPane = new JScrollPane(filteredTable);

        JButton backButton = new JButton("戻る");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultFrame.dispose();
            }
        });

        resultFrame.add(scrollPane, BorderLayout.CENTER);
        resultFrame.add(backButton, BorderLayout.SOUTH);
        resultFrame.setVisible(true);
    }
}
