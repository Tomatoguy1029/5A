import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.swing.Timer;

public class EnhancedFilterTableExample extends JFrame {
    private JFrame frame;
    private JPanel filterPanel, sidePanel, timePanel;
    private DefaultTableModel model;
    private JCheckBox checkBox52, checkBox53, checkBox54, checkBox63;
    private JCheckBox checkBoxPower, checkBoxLargeDesk;
    private JComboBox<String> dayComboBox;
    private JCheckBox[] timeCheckBoxes;
    private JButton searchButton;
    private JButton addroomButton;
    private JButton timeButton;
    private List<RowFilter<Object, Object>> filters;

    private JLabel time, a, b, c;

    public EnhancedFilterTableExample() {
        // メインフレームの設定
        frame = new JFrame("検索画面");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(null);

        // サンプルデータの作成（場所、条件、曜日、時間）
        String[] columnNames = { "Name", "Location", "Seats", "Outlets", "Desk Size", "Available", "Edit" };
        Object[][] data = {
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

        // テーブルモデルの設定
        model = new DefaultTableModel(data, columnNames);
        // JScrollPane scrollPane = new JScrollPane(table);

        filterPanel = new JPanel(null);
        filterPanel.setBackground(Color.LIGHT_GRAY); // ここで色を設定
        sidePanel = new JPanel(null);
        sidePanel.setBackground(Color.GRAY); // ここで色を設定
        timePanel = new JPanel(null);
        timePanel.setBackground(Color.PINK); // ここで色を設定

        time = new JLabel(getCurrentDateTime() + " - " + getCurrentTimeSlot());
        time.setBounds(50, 5, 300, 20);
        time.setFont(new Font("Arial", Font.PLAIN, 40));
        timePanel.add(time);

        a = new JLabel("教室検索");
        a.setBounds(50, 10, 300, 20);
        a.setFont(new Font("Arial", Font.PLAIN, 20));
        filterPanel.add(a);

        // 曜日と時間フィルタの設定
        dayComboBox = new JComboBox<>(new String[] { "月曜", "火曜", "水曜", "木曜", "金曜", "土曜" });
        timeCheckBoxes = new JCheckBox[7];
        dayComboBox.setBounds(40, 40, 100, 25);
        filterPanel.add(dayComboBox);
        for (int i = 0; i < 7; i++) {
            timeCheckBoxes[i] = new JCheckBox((i + 1) + "限");
            timeCheckBoxes[i].setBounds(40 + i * 80, 70, 70, 15);
            filterPanel.add(timeCheckBoxes[i]);
        }

        b = new JLabel("場所");
        b.setBounds(40, 140, 300, 20);
        b.setFont(new Font("Arial", Font.PLAIN, 15));
        filterPanel.add(b);

        // 場所と条件フィルタの設定
        checkBox52 = new JCheckBox("52号館");
        checkBox52.setBounds(40, 170, 100, 15);
        checkBox53 = new JCheckBox("53号館");
        checkBox53.setBounds(140, 170, 100, 15);
        checkBox54 = new JCheckBox("54号館");
        checkBox54.setBounds(240, 170, 100, 15);
        checkBox63 = new JCheckBox("63号館");
        checkBox63.setBounds(340, 170, 100, 15);
        checkBoxPower = new JCheckBox("電源あり");

        c = new JLabel("条件");
        c.setBounds(40, 220, 300, 20);
        c.setFont(new Font("Arial", Font.PLAIN, 15));
        filterPanel.add(c);

        checkBoxPower.setBounds(40, 250, 100, 15);
        checkBoxLargeDesk = new JCheckBox("机が広い");
        checkBoxLargeDesk.setBounds(140, 250, 100, 15);

        filterPanel.add(checkBox52);
        filterPanel.add(checkBox53);
        filterPanel.add(checkBox54);
        filterPanel.add(checkBox63);
        filterPanel.add(checkBoxPower);
        filterPanel.add(checkBoxLargeDesk);

        // 検索
        // 検索ボタンの作成
        searchButton = new JButton("Search");
        searchButton.setBounds(500, 250, 100, 50);
        filterPanel.add(searchButton);

        // 検索ボタンにアクションリスナーを追加
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFilteredResults(); // フィルタ結果を表示
            }
        });

        // 追加ボタンの作成
        addroomButton = new JButton("投稿");
        addroomButton.setBounds(50, 120, 100, 100);
        sidePanel.add(addroomButton);

        // 追加ボタンにアクションリスナーを追加
        addroomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showFilteredResults(); // フィルタ結果を表示
            }
        });

        // 時間ボタンの作成
        timeButton = new JButton("現在の空き教室を検索");
        timeButton.setBounds(400, 150, 200, 50);
        timePanel.add(timeButton);

        timeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String currentTimeSlot = getCurrentTimeSlot();
                if (!currentTimeSlot.equals("お休み")) {
                    showFilteredResultsForTimeSlot(currentTimeSlot); // 現在の時限でフィルタリング
                } else {
                    JOptionPane.showMessageDialog(frame, "お休みです");
                }
            }
        });

        // フレームにフィルタパネルとスクロールペインを追加
        filterPanel.setBounds(0, 200, 700, 500);
        frame.add(filterPanel);
        sidePanel.setBounds(700, 200, 300, 500);
        frame.add(sidePanel);
        timePanel.setBounds(0, 0, 1000, 200);
        frame.add(timePanel);

        // ウィンドウのサイズ変更イベントをリスナーで検知してボタンのサイズと位置を調整
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });

        frame.setVisible(true);

        // startButtonUpdateTimer(timeButton);
        TimeUpdate(time);

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
                timeFilters.add(RowFilter.regexFilter(selectedDay + (i + 1) + "限", 5));
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

    private void showFilteredResultsForTimeSlot(String timeSlot) {
        filters = new ArrayList<>();

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
        String selectedDay = new SimpleDateFormat("EEEE").format(new Date()); // 現在の曜日を取得
        List<RowFilter<Object, Object>> timeFilters = new ArrayList<>();
        timeFilters.add(RowFilter.regexFilter(selectedDay + timeSlot, 4));

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
        String[] columnNames = { "Name", "Location", "Seats", "Outlets", "Desk Size" };
        DefaultTableModel filteredModel = new DefaultTableModel(columnNames, 0);

        // フィルタされたデータを新しいテーブルモデルに追加
        for (Object[] row : filteredData) {
            Object[] newRow = new Object[5]; // Time Slots 列を除外して新しい配列を作成
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

    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(new Date());
    }

    private String getCurrentTimeSlot() {
        // 現在の日時を取得
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // 日曜日かどうかをチェック
        if (dayOfWeek == Calendar.SUNDAY) {
            return "お休み";
        }

        // 現在の時間を分単位に変換
        int totalMinutes = hour * 60 + minute;

        // 時限の判定
        if (totalMinutes >= 530 && totalMinutes < 630) { // 8:50 - 10:30
            return "1限";
        } else if (totalMinutes >= 630 && totalMinutes < 740) { // 10:30 - 12:20
            return "2限";
        } else if (totalMinutes >= 740 && totalMinutes < 890) { // 12:20 - 14:50
            return "3限";
        } else if (totalMinutes >= 890 && totalMinutes < 1005) { // 14:50 - 16:45
            return "4限";
        } else if (totalMinutes >= 1005 && totalMinutes < 1120) { // 16:45 - 18:40
            return "5限";
        } else if (totalMinutes >= 1120 && totalMinutes < 1235) { // 18:40 - 20:35
            return "6限";
        } else if (totalMinutes >= 1245 && totalMinutes < 1295) { // 20:45 - 21:35
            return "7限";
        } else {
            return "お休み";
        }
    }

    private void TimeUpdate(JLabel text) {
        Timer timer = new Timer(60000, new ActionListener() { // 1分ごとに更新
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text == time) {
                    text.setText(getCurrentDateTime() + " - " + getCurrentTimeSlot()); // 現在の日付と時限を設定
                }
            }
        });
        timer.setInitialDelay(0); // タイマーの初期遅延を0ミリ秒に設定
        timer.start();
    }

    private void resizeComponents() {
        // フレームの新しいサイズを取得
        int width = frame.getWidth();
        int height = frame.getHeight();

        timePanel.setBounds(0, 0, width, height / 3);
        time.setBounds(width / 4, height / 12, width / 2, height / 6);
        timeButton.setBounds(width * 3 / 8, height * 4 / 17, width / 4, height / 15);
        filterPanel.setBounds(width / 3, height / 3, width * 2 / 3, height * 2 / 3);
        sidePanel.setBounds(0, height / 3, width / 3, height * 2 / 3);
        addroomButton.setBounds(width / 9, 100, 100, 100);
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
