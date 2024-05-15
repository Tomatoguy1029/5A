import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class EnhancedFilterTableExample {
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;
    private JCheckBox checkBox52, checkBox53, checkBox54, checkBox63;
    private JCheckBox checkBoxPower, checkBoxLargeDesk;
    private JComboBox<String> dayComboBox;
    private JCheckBox[] timeCheckBoxes;
    private JButton searchButton;
    private List<RowFilter<Object, Object>> filters;

    public EnhancedFilterTableExample() {
        // メインフレームの設定
        frame = new JFrame("Enhanced Filter Table Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        // サンプルデータの作成（場所、条件、曜日、時間）
        Object[][] data = {
                // 52号館
                { "52-102", "52号館", "電源あり", "", "月曜1限,月曜3限,火曜5限,火曜6限,水曜1限,木曜6限,金曜3限" },
                { "52-103", "52号館", "電源あり", "", "月曜1限,木曜4限,木曜5限,木曜6限,金曜6限" },
                { "52-104", "52号館", "電源あり", "", "月曜5限,火曜3限,火曜5限,水曜5限,木曜1限,木曜4限,木曜5限,金曜1限,金曜2限,金曜5限" },
                { "52-201", "52号館", "電源あり", "", "月曜6限,火曜6限,水曜3限,水曜6限,木曜3限,木曜4限,金曜4限,金曜6限" },
                { "52-202", "52号館", "電源あり", "", "月曜6限,火曜5限,火曜6限,水曜1限,水曜5限,水曜6限,木曜5限,金曜4限,金曜5限,金曜6限" },
                { "52-204", "52号館", "電源あり", "", "月曜2限,月曜5限,月曜6限,火曜1限,火曜5限,火曜6限,水曜6限,木曜1限,木曜5限,木曜6限,金曜4限,金曜5限,金曜6限" },
                { "52-302", "52号館", "電源あり", "", "月曜5限,月曜6限,火曜5限,火曜6限,水曜4限,水曜5限,水曜6限,木曜1限,木曜4限,木曜5限,木曜6限,金曜6限" },
                { "52-303", "52号館", "電源あり", "", "月曜1限,月曜3限,月曜6限,火曜5限,火曜6限,水曜1限,水曜3限,木曜3限,木曜4限,木曜5限,木曜6限,金曜2限,金曜6限" },
                { "52-304", "52号館", "電源あり", "", "月曜1限,月曜5限,月曜6限,火曜1限,火曜5限,火曜6限,水曜1限,木曜3限,木曜4限,木曜5限,木曜6限" },
                // 53号館
                { "53-B01", "53号館", "", "", "月曜6限,火曜5限,火曜6限,水曜6限,木曜5限,木曜6限,金曜6限" },
                { "53-B03", "53号館", "", "", "月曜6限,火曜5限,火曜6限,水曜6限,木曜1限,木曜2限,木曜6限,金曜6限" },
                { "53-B04", "53号館", "", "", "月曜3限,月曜6限,火曜5限,火曜6限,水曜5限,水曜6限,木曜1限,木曜3限,木曜4限,木曜5限,木曜6限,金曜4限,金曜5限,金曜6限" },
                { "53-101", "53号館", "", "", "月曜3限,月曜6限,火曜6限,水曜4限,木曜2限,金曜5限,金曜6限" },
                { "53-103", "53号館", "", "", "月曜1限,月曜3限,月曜6限,火曜4限,火曜6限,水曜6限,木曜6限,金曜4限,金曜6限" },
                { "53-104", "53号館", "", "", "月曜6限,火曜2限,火曜6限,水曜4限,水曜6限,木曜3限,木曜4限,木曜5限,木曜6限,金曜4限,金曜5限,金曜6限" },
                { "53-201", "53号館", "", "", "月曜1限,月曜6限,火曜6限,水曜5限,木曜4限,木曜6限,金曜6限" },
                { "53-203", "53号館", "", "", "月曜1限,月曜5限,月曜6限,火曜6限,水曜1限,水曜6限,金曜4限,金曜6限" },
                { "53-204", "53号館", "", "", "月曜1限,月曜6限,水曜6限,木曜6限,金曜5限,金曜6限" },
                { "53-301", "53号館", "", "", "月曜1限,月曜6限,火曜1限,火曜6限,水曜1限,水曜6限,木曜4限,木曜6限,金曜6限" },
                { "53-303", "53号館", "", "", "月曜1限,火曜6限,水曜6限,木曜4限,木曜5限,木曜6限,金曜6限" },
                { "53-304", "53号館", "", "", "月曜6限,火曜3限,火曜6限,水曜6限,木曜4限,金曜3限,金曜4限,金曜5限,金曜6限" },
                { "53-401", "53号館", "", "", "月曜1限,月曜6限,火曜1限,火曜6限,水曜1限,水曜6限,木曜6限,金曜4限,金曜5限,金曜6限" },
                { "53-403", "53号館", "", "", "月曜1限,月曜6限,火曜4限,火曜6限,水曜1限,木曜4限,木曜5限,木曜6限,金曜6限" },
                { "53-404", "53号館", "", "", "月曜1限,月曜4限,月曜6限,火曜4限,火曜5限,火曜6限,水曜1限,水曜5限,水曜6限,木曜1限,木曜4限,木曜6限,金曜3限,金曜4限,金曜5限,金曜6限" },
                // 54号館
                { "54-B01", "54号館", "", "", "月曜2限,月曜3限" },
                { "54-B02", "54号館", "", "", "月曜2限,月曜3限,火曜4限" },
                { "54-B03", "54号館", "", "", "火曜1限,火曜2限" },
                { "54-B04", "54号館", "", "", "水曜3限,水曜4限" },
                { "54-101", "54号館", "", "", "月曜2限,月曜3限" },
                { "54-102", "54号館", "", "", "月曜2限,月曜3限,火曜4限" },
                { "54-103", "54号館", "", "", "火曜1限,火曜2限" },
                { "54-104", "54号館", "", "", "水曜3限,水曜4限" },
                { "54-201", "54号館", "", "", "月曜2限,月曜3限" },
                { "54-202", "54号館", "", "", "月曜2限,月曜3限,火曜4限" },
                { "54-203", "54号館", "", "", "火曜1限,火曜2限" },
                { "54-204", "54号館", "", "", "水曜3限,水曜4限" },
                { "54-301", "54号館", "", "", "月曜2限,月曜3限" },
                { "54-302", "54号館", "", "", "月曜2限,月曜3限,火曜4限" },
                { "54-303", "54号館", "", "", "火曜1限,火曜2限" },
                { "54-304", "54号館", "", "", "水曜3限,水曜4限" },
                { "54-401", "54号館", "", "", "月曜2限,月曜3限" },
                { "54-402", "54号館", "", "", "月曜2限,月曜3限,火曜4限" },
                { "54-403", "54号館", "", "", "火曜1限,火曜2限" },
                { "54-404", "54号館", "", "", "水曜3限,水曜4限" },
                // 63号館
                { "PCルームA", "63号館", "電源あり", "机が広い",
                        "月曜3限,月曜4限,月曜5限,月曜6限,月曜7限,火曜3限,火曜5限,火曜6限,火曜7限,水曜2限,水曜3限,水曜4限,水曜6限,水曜7限,木曜2限,木曜3限,木曜4限,木曜5限,木曜6限,木曜7限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限" },
                { "PCルームB", "63号館", "電源あり", "机が広い",
                        "月曜5限,月曜7限,火曜3限,火曜4限,火曜5限,火曜6限,火曜7限,水曜2限,水曜3限,水曜4限,水曜7限,木曜2限,木曜3限,木曜6限,木曜7限,金曜4限,金曜5限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限" },
                { "PCルームC", "63号館", "電源あり", "机が広い",
                        "月曜2限,月曜6限,月曜7限,火曜1限,火曜2限,火曜5限,火曜6限,火曜7限,水曜1限,水曜4限,水曜7限,木曜3限,木曜5限,木曜6限,木曜7限,金曜1限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限" },
                { "PCルームD", "63号館", "電源あり", "机が広い",
                        "月曜1限,月曜5限,月曜6限,月曜7限,火曜1限,火曜2限,火曜3限,火曜4限,火曜5限,火曜6限,火曜7限,水曜2限,水曜3限,水曜4限,水曜5限,水曜6限,水曜7限,木曜1限,木曜2限,木曜3限,木曜4限,木曜5限,木曜6限,木曜7限,金曜3限,金曜4限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限" },
                { "PCルームE", "63号館", "電源あり", "机が広い",
                        "月曜1限,月曜2限,月曜3限,月曜4限,月曜6限,月曜7限,火曜3限,火曜4限,火曜5限,火曜6限,火曜7限,水曜2限,水曜3限,水曜4限,水曜5限,水曜6限,水曜7限,木曜1限,木曜4限,木曜5限,木曜6限,木曜7限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限" },
                { "PCルームF", "63号館", "電源あり", "机が広い",
                        "月曜1限,月曜3限,月曜6限,月曜7限,火曜1限,火曜5限,火曜6限,火曜7限,水曜1限,水曜4限,水曜5限,水曜6限,水曜7限,木曜1限,木曜3限,木曜4限,木曜5限,木曜6限,木曜7限,金曜4限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限" },
                { "PCルームG", "63号館", "電源あり", "机が広い",
                        "月曜1限,月曜2限,月曜6限,月曜7限,火曜3限,火曜4限,火曜5限,火曜6限,火曜7限,水曜1限,水曜3限,水曜7限,木曜1限,木曜2限,木曜3限,木曜6限,木曜7限,金曜3限,金曜4限,金曜5限,金曜6限,金曜7限,土曜1限,土曜2限,土曜3限,土曜4限,土曜5限,土曜6限,土曜7限" },
        };
        String[] columnNames = { "教室名", "Location", "Power", "Large Desk", "Time Slots" };

        // テーブルモデルの設定
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // フィルタパネルの設定
        JPanel filterPanel = new JPanel(new BorderLayout());
        JPanel locationConditionPanel = new JPanel(new FlowLayout());
        JPanel timePanel = new JPanel(new FlowLayout());

        // 場所と条件フィルタの設定
        checkBox52 = new JCheckBox("52号館");
        checkBox53 = new JCheckBox("53号館");
        checkBox54 = new JCheckBox("54号館");
        checkBox63 = new JCheckBox("63号館");
        checkBoxPower = new JCheckBox("電源あり");
        checkBoxLargeDesk = new JCheckBox("机が広い");
        locationConditionPanel.add(checkBox52);
        locationConditionPanel.add(checkBox53);
        locationConditionPanel.add(checkBox54);
        locationConditionPanel.add(checkBox63);
        locationConditionPanel.add(checkBoxPower);
        locationConditionPanel.add(checkBoxLargeDesk);

        // 曜日と時間フィルタの設定
        dayComboBox = new JComboBox<>(new String[] { "月曜", "火曜", "水曜", "木曜", "金曜", "土曜" });
        timeCheckBoxes = new JCheckBox[7];
        for (int i = 0; i < 7; i++) {
            timeCheckBoxes[i] = new JCheckBox((i + 1) + "限");
            timePanel.add(timeCheckBoxes[i]);
        }
        timePanel.add(dayComboBox);

        // 検索
        // 検索ボタンの作成
        searchButton = new JButton("Search");

        // パネルにコンポーネントを追加
        filterPanel.add(locationConditionPanel, BorderLayout.NORTH);
        filterPanel.add(timePanel, BorderLayout.CENTER);
        filterPanel.add(searchButton, BorderLayout.SOUTH);

        // 検索ボタンにアクションリスナーを追加
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFilteredResults(); // フィルタ結果を表示
            }
        });

        // フレームにフィルタパネルとスクロールペインを追加
        frame.add(filterPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void showFilteredResults() {
        filters = new ArrayList<>(); // フィルタのリストを初期化

        // 場所のフィルタ条件をまとめて追加
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

        // 条件のフィルタ条件を追加
        if (checkBoxPower.isSelected()) {
            filters.add(RowFilter.regexFilter("電源あり", 2));
        }
        if (checkBoxLargeDesk.isSelected()) {
            filters.add(RowFilter.regexFilter("机が広い", 3));
        }

        // 曜日と時間のフィルタ条件を追加
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

        // 複数のフィルタを結合
        RowFilter<Object, Object> compoundRowFilter = RowFilter.andFilter(filters);
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        sorter.setRowFilter(compoundRowFilter);

        // フィルタされたデータを収集
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

        // フィルタ結果を新しいウィンドウに表示
        displayInNewWindow(filteredData);
    }

    private void displayInNewWindow(List<Object[]> filteredData) {
        // 新しいフレームの作成
        JFrame resultFrame = new JFrame("Filtered Results");
        resultFrame.setSize(800, 600);

        // 新しいテーブルモデルの作成
        String[] columnNames = { "Name", "Location", "Power", "Large Desk" };
        DefaultTableModel filteredModel = new DefaultTableModel(columnNames, 0);

        // フィルタされたデータを新しいテーブルモデルに追加
        for (Object[] row : filteredData) {
            Object[] newRow = new Object[4]; // Time Slots 列を除外して新しい配列を作成
            for (int j = 0; j < newRow.length; j++) {
                newRow[j] = row[j]; // Time Slots を除外したデータを新しい行に追加
            }
            filteredModel.addRow(newRow);
        }

        // 新しいテーブルとスクロールペインの作成
        JTable filteredTable = new JTable(filteredModel);
        JScrollPane scrollPane = new JScrollPane(filteredTable);

        // 戻るボタンの作成
        JButton backButton = new JButton("戻る");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultFrame.dispose(); // 結果フレームを閉じる
                frame.setVisible(true); // メインフレームを再表示
            }
        });

        // 結果フレームにコンポーネントを追加
        resultFrame.add(scrollPane, BorderLayout.CENTER);
        resultFrame.add(backButton, BorderLayout.SOUTH);
        resultFrame.setVisible(true);

        // メインフレームを非表示にする
        frame.setVisible(false);
    }

    public static void main(String[] args) {
        // メインスレッドでSwingアプリケーションを開始
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EnhancedFilterTableExample(); // フィルタテーブル例を起動
            }
        });
    }
}
