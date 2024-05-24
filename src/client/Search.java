package src.client;

import javax.swing.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Search {
    private DefaultTableModel model;

public Search(){
    initializeTableModel();
}

    public List<Object[]> showFilteredResults(JCheckBox checkBox52, JCheckBox checkBox53,
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

        return filteredData;
    }

    public List<Object[]> showFilteredResultsForTimeSlot(JCheckBox checkBox52, JCheckBox checkBox53,
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

        return filteredData;
    }

    

    private void initializeTableModel() {// データの初期化 TODO: データをサーバーから取得
        String[] columnNames = { "Name", "Location", "Seats", "Outlets", "Desk Size", "Available", "Edit" };
        Object[][] data = getSampleData();
        model = new DefaultTableModel(data, columnNames);
    }

    private Object[][] getSampleData() {
        return new Object[][] {
                // 52号館
                { "52-102", 52, 50, 20, 100, "月曜1限,月曜3限,火曜5限,火曜6限,水曜1限,木曜6限,金曜3限", "Edit" },
                { "52-103", 52, 50, 20, 100, "月曜1限,木曜4限,木曜5限,木曜6限,金曜6限", "Edit" },
                { "52-104", 52, 50, 20, 100, "月曜5限,火曜3限,火曜5限,水曜5限,木曜1限,木曜4限,木曜5限,金曜1限,金曜2限,金曜5限", "Edit" },
                { "52-201", 52, 50, 20, 100, "月曜6限,火曜6限,水曜3限,水曜6限,木曜3限,木曜4限,金曜4限,金曜6限", "Edit" },
                { "52-202", 52, 50, 20, 100, "月曜6限,火曜5限,火曜6限,水曜1限,水曜5限,水曜6限,木曜5限,金曜4限,金曜5限,金曜6限", "Edit" },
                { "52-204", 52, 50, 20, 100, "月曜2限,月曜5限,月曜6限,火曜1限,火曜5限,火曜6限,水曜6限,木曜1限,木曜5限,木曜6限,金曜4限,金曜5限,金曜6限",
                        "Edit" },
                { "52-302", 52, 50, 20, 100, "月曜5限,月曜6限,火曜5限,火曜6限,水曜4限,水曜5限,水曜6限,木曜1限,木曜4限,木曜5限,木曜6限,金曜6限", "Edit" },
                { "52-303", 52, 50, 20, 100, "月曜1限,月曜3限,月曜6限,火曜5限,火曜6限,水曜1限,水曜3限,木曜3限,木曜4限,木曜5限,木曜6限,金曜2限,金曜6限",
                        "Edit" },
                { "52-304", 52, 50, 20, 100, "月曜1限,月曜5限,月曜6限,火曜1限,火曜5限,火曜6限,水曜1限,木曜3限,木曜4限,木曜5限,木曜6限", "Edit" },
                // 53号館
                { "53-B01", 53, 0, 0, 0, "月曜6限,火曜5限,火曜6限,水曜6限,木曜5限,木曜6限,金曜6限", "Edit" },
                { "53-B03", 53, 0, 0, 0, "月曜6限,火曜5限,火曜6限,水曜6限,木曜1限,木曜2限,木曜6限,金曜6限", "Edit" },
                { "53-B04", 53, 0, 0, 0, "月曜3限,月曜6限,火曜5限,火曜6限,水曜5限,水曜6限,木曜1限,木曜3限,木曜4限,木曜5限,木曜6限,金曜4限,金曜5限,金曜6限",
                        "Edit" },
                { "53-101", 53, 0, 0, 0, "月曜3限,月曜6限,火曜6限,水曜4限,木曜2限,金曜5限,金曜6限", "Edit" },
                { "53-103", 53, 0, 0, 0, "月曜1限,月曜3限,月曜6限,火曜4限,火曜6限,水曜6限,木曜6限,金曜4限,金曜6限", "Edit" },
                { "53-104", 53, 0, 0, 0, "月曜6限,火曜2限,火曜6限,水曜4限,水曜6限,木曜3限,木曜4限,木曜5限,木曜6限,金曜4限,金曜5限,金曜6限", "Edit" },
                { "53-201", 53, 0, 0, 0, "月曜1限,月曜6限,火曜6限,水曜5限,木曜4限,木曜6限,金曜6限", "Edit" },
                { "53-203", 53, 0, 0, 0, "月曜1限,月曜5限,月曜6限,火曜6限,水曜1限,水曜6限,金曜4限,金曜6限", "Edit" },
                { "53-204", 53, 0, 0, 0, "月曜1限,月曜6限,水曜6限,木曜6限,金曜5限,金曜6限", "Edit" },
                { "53-301", 53, 0, 0, 0, "月曜1限,月曜6限,火曜1限,火曜6限,水曜1限,水曜6限,木曜4限,木曜6限,金曜6限", "Edit" },
                { "53-303", 53, 0, 0, 0, "月曜1限,火曜6限,水曜6限,木曜4限,木曜5限,木曜6限,金曜6限", "Edit" },
                { "53-304", 53, 0, 0, 0, "月曜6限,火曜3限,火曜6限,水曜6限,木曜4限,金曜3限,金曜4限,金曜5限,金曜6限", "Edit" },
                { "53-401", 53, 0, 0, 0, "月曜1限,月曜6限,火曜1限,火曜6限,水曜1限,水曜6限,木曜6限,金曜4限,金曜5限,金曜6限", "Edit" },
                { "53-403", 53, 0, 0, 0, "月曜1限,月曜6限,火曜4限,火曜6限,水曜1限,木曜4限,木曜5限,木曜6限,金曜6限", "Edit" },
                { "53-404", 53, 0, 0, 0,
                        "月曜1限,月曜4限,月曜6限,火曜4限,火曜5限,火曜6限,水曜1限,水曜5限,水曜6限,木曜1限,木曜4限,木曜6限,金曜3限,金曜4限,金曜5限,金曜6限", "Edit" },
                // 54号館
                { "54-B01", 54, 0, 0, 0, "月曜2限,月曜3限", "Edit" },
                { "54-B02", 54, 0, 0, 0, "月曜2限,月曜3限,火曜4限", "Edit" },
                { "54-B03", 54, 0, 0, 0, "火曜1限,火曜2限", "Edit" },
                { "54-B04", 54, 0, 0, 0, "水曜3限,水曜4限", "Edit" },
                { "54-101", 54, 0, 0, 0, "月曜2限,月曜3限", "Edit" },
                { "54-102", 54, 0, 0, 0, "月曜2限,月曜3限,火曜4限", "Edit" },
                { "54-103", 54, 0, 0, 0, "火曜1限,火曜2限", "Edit" },
                { "54-104", 54, 0, 0, 0, "水曜3限,水曜4限", "Edit" },
                { "54-201", 54, 0, 0, 0, "月曜2限,月曜3限", "Edit" },
                { "54-202", 54, 0, 0, 0, "月曜2限,月曜3限,火曜4限", "Edit" },
                { "54-203", 54, 0, 0, 0, "火曜1限,火曜2限", "Edit" },
                { "54-204", 54, 0, 0, 0, "水曜3限,水曜4限", "Edit" },
                { "54-301", 54, 0, 0, 0, "月曜2限,月曜3限", "Edit" },
                { "54-302", 54, 0, 0, 0, "月曜2限,月曜3限,火曜4限", "Edit" },
                { "54-303", 54, 0, 0, 0, "火曜1限,火曜2限", "Edit" },
                { "54-304", 54, 0, 0, 0, "水曜3限,水曜4限", "Edit" },
                { "54-401", 54, 0, 0, 0, "月曜2限,月曜3限", "Edit" },
                { "54-402", 54, 0, 0, 0, "月曜2限,月曜3限,火曜4限", "Edit" },
                { "54-403", 54, 0, 0, 0, "火曜1限,火曜2限", "Edit" },
                { "54-404", 54, 0, 0, 0, "水曜3限,水曜4限", "Edit" },
                // 63号館
                { "PCルームA", 63, 0, 0, 0,
                        "月曜3限,月曜4限,月曜5限,月曜6限,月曜7限,火曜3限,火曜5限,火曜6限,火曜7限,水曜2限,水曜3限,水曜4限,水曜6限,水曜7限,木曜2限,木曜3限,木曜4限,木曜5限,木曜6限,木曜7限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限",
                        "Edit" },
                { "PCルームB", 63, 0, 0, 0,
                        "月曜5限,月曜7限,火曜3限,火曜4限,火曜5限,火曜6限,火曜7限,水曜2限,水曜3限,水曜4限,水曜7限,木曜2限,木曜3限,木曜6限,木曜7限,金曜4限,金曜5限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限",
                        "Edit" },
                { "PCルームC", 63, 0, 0, 0,
                        "月曜2限,月曜6限,月曜7限,火曜1限,火曜2限,火曜5限,火曜6限,火曜7限,水曜1限,水曜4限,水曜7限,木曜3限,木曜5限,木曜6限,木曜7限,金曜1限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限",
                        "Edit" },
                { "PCルームD", 63, 0, 0, 0,
                        "月曜1限,月曜5限,月曜6限,月曜7限,火曜1限,火曜2限,火曜3限,火曜4限,火曜5限,火曜6限,火曜7限,水曜2限,水曜3限,水曜4限,水曜5限,水曜6限,水曜7限,木曜1限,木曜2限,木曜3限,木曜4限,木曜5限,木曜6限,木曜7限,金曜3限,金曜4限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限",
                        "Edit" },
                { "PCルームE", 63, 0, 0, 0,
                        "月曜1限,月曜2限,月曜3限,月曜4限,月曜6限,月曜7限,火曜3限,火曜4限,火曜5限,火曜6限,火曜7限,水曜2限,水曜3限,水曜4限,水曜5限,水曜6限,水曜7限,木曜1限,木曜4限,木曜5限,木曜6限,木曜7限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限",
                        "Edit" },
                { "PCルームF", 63, 0, 0, 0,
                        "月曜1限,月曜3限,月曜6限,月曜7限,火曜1限,火曜5限,火曜6限,火曜7限,水曜1限,水曜4限,水曜5限,水曜6限,水曜7限,木曜1限,木曜3限,木曜4限,木曜5限,木曜6限,木曜7限,金曜4限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限",
                        "Edit" },
                { "PCルームG", 63, 0, 0, 0,
                        "月曜1限,月曜2限,月曜6限,月曜7限,火曜3限,火曜4限,火曜5限,火曜6限,火曜7限,水曜1限,水曜3限,水曜7限,木曜1限,木曜2限,木曜3限,木曜6限,木曜7限,金曜3限,金曜4限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限",
                        "Edit" }
        };

    }
}
