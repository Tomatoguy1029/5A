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
                { "Room A", "52号館", "電源あり", "机が広い", "月曜2限,月曜3限,火曜4限" },
                { "Room B", "52号館", "電源あり", "", "火曜1限,火曜2限" },
                { "Room C", "52号館", "", "机が広い", "水曜3限,水曜4限" },
                { "Room D", "54号館", "電源あり", "机が広い", "木曜2限,木曜3限" },
                { "Room E", "63号館", "", "", "金曜1限,金曜2限" },
                { "Room F", "52号館", "電源あり", "机が広い", "月曜2限,金曜4限" },
                { "Room G", "53号館", "電源あり", "", "火曜3限,金曜1限" },
                { "Room H", "54号館", "", "机が広い", "木曜5限,木曜6限" },
                { "Room I", "63号館", "電源あり", "", "土曜1限,土曜2限" },
                { "Room J", "52号館", "", "", "月曜2限,月曜3限" },
                { "Room A", "52号館", "電源あり", "机が広い", "月曜2限,月曜3限,火曜4限" },
                { "Room B", "52号館", "電源あり", "", "火曜1限,火曜2限" },
                { "Room C", "52号館", "", "机が広い", "水曜3限,水曜4限" },
                { "Room D", "54号館", "電源あり", "机が広い", "木曜2限,木曜3限" },
                { "Room E", "63号館", "", "", "金曜1限,金曜2限" },
                { "Room F", "52号館", "電源あり", "机が広い", "月曜2限,金曜4限" },
                { "Room G", "53号館", "電源あり", "", "火曜3限,金曜1限" },
                { "Room H", "54号館", "", "机が広い", "木曜5限,木曜6限" },
                { "Room I", "63号館", "電源あり", "", "土曜1限,土曜2限" },
                { "Room J", "52号館", "", "", "月曜2限,月曜3限" }
        };
        String[] columnNames = { "Name", "Location", "Power", "Large Desk", "Time Slots" };

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
