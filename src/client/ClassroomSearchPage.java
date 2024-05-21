package src.client;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class ClassroomSearchPage extends JFrame {
    private JFrame frame;
    private JPanel filterPanel, sidePanel, timePanel;
    public DefaultTableModel model;
    private JCheckBox checkBox52, checkBox53, checkBox54, checkBox63;
    private JCheckBox checkBoxPower, checkBoxLargeDesk;
    private JComboBox<String> dayComboBox;
    private JCheckBox[] timeCheckBoxes;
    private JButton searchButton;
    private JButton addroomButton;
    private JButton timeButton;

    private JLabel time, a, b, c;

    public ClassroomSearchPage() {
        initializeFrame();
        initializePanels();
        initializeLabels();
        initializeFilters();
        initializeButtons();
        initializeTableModel();
        initializeResizeListener();

        frame.setVisible(true);
        TimeUpdate(time);
    }

    private void initializeFrame() {// フレームの初期化
        frame = new JFrame("検索画面");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(null);
    }

    private void initializePanels() {// パネルの初期化
        filterPanel = new JPanel(null);
        filterPanel.setBackground(Color.LIGHT_GRAY);
        filterPanel.setBounds(0, 200, 700, 500);
        frame.add(filterPanel);

        sidePanel = new JPanel(null);
        sidePanel.setBackground(Color.GRAY);
        sidePanel.setBounds(700, 200, 300, 500);
        frame.add(sidePanel);

        timePanel = new JPanel(null);
        timePanel.setBackground(Color.PINK);
        timePanel.setBounds(0, 0, 1000, 200);
        frame.add(timePanel);
    }

    private void initializeLabels() {// ラベルの初期化
        time = new JLabel(getCurrentDateTime() + " - " + getCurrentTimeSlot());
        time.setBounds(50, 5, 300, 20);
        time.setFont(new Font("Arial", Font.PLAIN, 40));
        timePanel.add(time);

        a = new JLabel("教室検索");
        a.setBounds(50, 10, 300, 20);
        a.setFont(new Font("Arial", Font.PLAIN, 20));
        filterPanel.add(a);

        b = new JLabel("場所");
        b.setBounds(40, 140, 300, 20);
        b.setFont(new Font("Arial", Font.PLAIN, 15));
        filterPanel.add(b);

        c = new JLabel("条件");
        c.setBounds(40, 220, 300, 20);
        c.setFont(new Font("Arial", Font.PLAIN, 15));
        filterPanel.add(c);
    }

    private void initializeFilters() {// フィルターの初期化
        dayComboBox = new JComboBox<>(new String[] { "月曜", "火曜", "水曜", "木曜", "金曜", "土曜" });
        dayComboBox.setBounds(40, 40, 100, 25);
        filterPanel.add(dayComboBox);

        timeCheckBoxes = new JCheckBox[7];
        for (int i = 0; i < 7; i++) {
            timeCheckBoxes[i] = new JCheckBox((i + 1) + "限");
            timeCheckBoxes[i].setBounds(40 + i * 80, 70, 70, 15);
            filterPanel.add(timeCheckBoxes[i]);
        }

        checkBox52 = new JCheckBox("52号館");
        checkBox52.setBounds(40, 170, 100, 15);
        filterPanel.add(checkBox52);

        checkBox53 = new JCheckBox("53号館");
        checkBox53.setBounds(140, 170, 100, 15);
        filterPanel.add(checkBox53);

        checkBox54 = new JCheckBox("54号館");
        checkBox54.setBounds(240, 170, 100, 15);
        filterPanel.add(checkBox54);

        checkBox63 = new JCheckBox("63号館");
        checkBox63.setBounds(340, 170, 100, 15);
        filterPanel.add(checkBox63);

        checkBoxPower = new JCheckBox("電源あり");
        checkBoxPower.setBounds(40, 250, 100, 15);
        filterPanel.add(checkBoxPower);

        checkBoxLargeDesk = new JCheckBox("机が広い");
        checkBoxLargeDesk.setBounds(140, 250, 100, 15);
        filterPanel.add(checkBoxLargeDesk);
    }

    private void initializeButtons() {// ボタンの初期化
        searchButton = new JButton("Search");
        searchButton.setBounds(500, 250, 100, 50);
        filterPanel.add(searchButton);
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Search search = new Search();
                search.showFilteredResults(model, checkBox52, checkBox53, checkBox54, checkBox63, checkBoxPower,
                        checkBoxLargeDesk, dayComboBox, timeCheckBoxes);
            }
        });

        addroomButton = new JButton("投稿");
        addroomButton.setBounds(50, 120, 100, 100);
        sidePanel.add(addroomButton);
        addroomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Search search = new Search();
                search.showFilteredResults(model, checkBox52, checkBox53, checkBox54, checkBox63, checkBoxPower,
                        checkBoxLargeDesk, dayComboBox, timeCheckBoxes);
            }
        });

        timeButton = new JButton("現在の空き教室を検索");
        timeButton.setBounds(400, 150, 200, 50);
        timePanel.add(timeButton);
        timeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String currentTimeSlot = getCurrentTimeSlot();
                if (!currentTimeSlot.equals("お休み")) {
                    Search search = new Search();
                    search.showFilteredResultsForTimeSlot(model, checkBox52, checkBox53, checkBox54, checkBox63,
                            checkBoxPower, checkBoxLargeDesk, currentTimeSlot);
                } else {
                    JOptionPane.showMessageDialog(frame, "お休みです");
                }
            }
        });
    }

    private void initializeTableModel() {// データの初期化 TODO: データをサーバーから取得
        String[] columnNames = { "Name", "Location", "Seats", "Outlets", "Desk Size", "Available", "Edit" };
        Object[][] data = getSampleData();
        model = new DefaultTableModel(data, columnNames);
    }

    private void initializeResizeListener() {// ウィンドウサイズの変更を検知するリスナーの初期化
        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });
    }

    private void resizeComponents() {// ウィンドウサイズに合わせてコンポーネントの位置を調整
        int width = frame.getWidth();
        int height = frame.getHeight();

        timePanel.setBounds(0, 0, width, height / 3);
        time.setBounds(width / 4, height / 12, width / 2, height / 6);
        timeButton.setBounds(width * 3 / 8, height * 4 / 17, width / 4, height / 15);
        filterPanel.setBounds(width / 3, height / 3, width * 2 / 3, height * 2 / 3);
        sidePanel.setBounds(0, height / 3, width / 3, height * 2 / 3);
        addroomButton.setBounds(width / 9, 100, 100, 100);
    }

    private String getCurrentDateTime() {// 現在の日時を取得
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(new Date());
    }

    private String getCurrentTimeSlot() {// 現在の時限を取得
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (dayOfWeek == Calendar.SUNDAY) {
            return "お休み";
        }

        int totalMinutes = hour * 60 + minute;

        if (totalMinutes >= 530 && totalMinutes < 630) {
            return "1限";
        } else if (totalMinutes >= 630 && totalMinutes < 740) {
            return "2限";
        } else if (totalMinutes >= 740 && totalMinutes < 890) {
            return "3限";
        } else if (totalMinutes >= 890 && totalMinutes < 1005) {
            return "4限";
        } else if (totalMinutes >= 1005 && totalMinutes < 1120) {
            return "5限";
        } else if (totalMinutes >= 1120 && totalMinutes < 1235) {
            return "6限";
        } else if (totalMinutes >= 1245 && totalMinutes < 1295) {
            return "7限";
        } else {
            return "お休み";
        }
    }

    private void TimeUpdate(JLabel text) {// 1分ごとに現在時刻を更新
        Timer timer = new Timer(60000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (text == time) {
                    text.setText(getCurrentDateTime() + " - " + getCurrentTimeSlot());
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClassroomSearchPage();
            }
        });
    }
}
