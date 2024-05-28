package src.client.pages;

import javax.swing.*;

import javax.swing.table.DefaultTableModel;

import src.client.service.DayTime;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class ClassroomSearchPage extends JFrame {
    JFrame frame;
    JPanel filtersPanel, sidePanel, timePanel;
    DefaultTableModel model;
    public JComboBox<String> buildingComboBox;
    public JComboBox<String> dayComboBox;
    public JCheckBox checkBoxPower, checkBoxLargeDesk, checkBoxQuiet, checkBoxNetwork;
    public JCheckBox[] timeCheckBoxes;
    JButton searchButton;
    JButton addroomButton;
    JButton timeButton;
    JLabel time, a, b, c;

    DayTime day = new DayTime();
    Sample sample = new Sample();

    public ClassroomSearchPage() {// コンストラクタ
        initializeFrame();
        initializePanels();
        initializeLabels();
        initializeFilters();
        initializeButtons();
        initializeTableModel();
        initializeResizeListener();

        new ClassroomSearchPageVM(this);

        frame.setVisible(true);
        day.TimeUpdate(time);
    }

    public void initializeFrame() {
        this.frame = new JFrame("検索画面");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(1000, 700);
        this.frame.setLayout(new BorderLayout());
    }

    public void initializePanels() {
        this.filtersPanel = new JPanel(new GridBagLayout());
        this.filtersPanel.setBackground(Color.LIGHT_GRAY);
        this.filtersPanel.setSize(this.frame.getWidth() / 2, this.frame.getHeight() / 2);

        this.sidePanel = new JPanel(new GridBagLayout());
        this.sidePanel.setBackground(Color.GRAY);
        this.sidePanel.setSize(this.frame.getWidth() / 2, this.frame.getHeight() / 2);

        this.timePanel = new JPanel(new GridBagLayout());
        this.timePanel.setBackground(Color.PINK);
        this.timePanel.setSize(this.frame.getWidth(), this.frame.getHeight() / 2);

        this.frame.add(this.filtersPanel, BorderLayout.EAST);
        this.frame.add(this.sidePanel, BorderLayout.WEST);
        this.frame.add(this.timePanel, BorderLayout.NORTH);
    }

    public void initializeLabels() {
        this.time = new JLabel(day.formatCurrentDateTime());
        this.time.setFont(new Font("Serif", Font.PLAIN, 40));

        this.a = new JLabel("教室検索");
        this.a.setFont(new Font("Serif", Font.PLAIN, 20));

        this.b = new JLabel("場所");
        this.b.setFont(new Font("Serif", Font.PLAIN, 15));

        this.c = new JLabel("条件");
        this.c.setFont(new Font("Serif", Font.PLAIN, 15));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.filtersPanel.add(this.a, gbc);

        gbc.gridy = 1;
        this.filtersPanel.add(this.b, gbc);

        gbc.gridy = 2;
        this.filtersPanel.add(this.c, gbc);

        gbc.gridy = 0;
        gbc.gridx = 0;
        this.timePanel.add(this.time, gbc);
    }

    public void initializeFilters() {
        this.dayComboBox = new JComboBox<>(new String[] { "月曜", "火曜", "水曜", "木曜", "金曜", "土曜" });

        JPanel timeCheckBoxPanel = new JPanel(new GridLayout(1, 7, 10, 10));
        this.timeCheckBoxes = new JCheckBox[7];
        for (int i = 0; i < 7; i++) {
            this.timeCheckBoxes[i] = new JCheckBox((i + 1) + "限");
            timeCheckBoxPanel.add(this.timeCheckBoxes[i]);
        }

        this.buildingComboBox = new JComboBox<>(new String[] { "52号館", "53号館", "54号館", "63号館" });

        JPanel conditionCheckBoxPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        this.checkBoxPower = new JCheckBox("電源あり");
        this.checkBoxLargeDesk = new JCheckBox("机が広い");
        this.checkBoxQuiet = new JCheckBox("静かなところ");
        this.checkBoxNetwork = new JCheckBox("ネット環境が良い");

        conditionCheckBoxPanel.add(this.checkBoxPower);
        conditionCheckBoxPanel.add(this.checkBoxLargeDesk);
        conditionCheckBoxPanel.add(this.checkBoxQuiet);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.filtersPanel.add(this.dayComboBox, gbc);

        gbc.gridy = 1;
        this.filtersPanel.add(timeCheckBoxPanel, gbc);

        gbc.gridy = 2;
        this.filtersPanel.add(this.buildingComboBox, gbc);

        gbc.gridy = 3;
        this.filtersPanel.add(conditionCheckBoxPanel, gbc);
    }

    public void initializeButtons() {
        this.searchButton = new JButton("Search");
        this.addroomButton = new JButton("投稿");
        this.timeButton = new JButton("現在の空き教室を検索");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        this.filtersPanel.add(this.searchButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(0, 0, 20, 20); // 余白の調整
        this.sidePanel.add(this.addroomButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.timePanel.add(this.timeButton, gbc);
    }

    public void initializeTableModel() {
        String[] columnNames = { "Name", "Location", "Seats", "Outlets", "Desk Size", "Available", "Edit" };
        Object[][] data = sample.getSampleData();
        this.model = new DefaultTableModel(data, columnNames);
    }

    public void initializeResizeListener() {
        this.frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });
    }

    public void resizeComponents() {// コンポーネントのサイズを調整
        int width = frame.getWidth();
        int height = frame.getHeight();

        timePanel.setBounds(0, 0, width, height / 3);
        time.setBounds(width / 4, height / 12, width / 2, height / 6);
        timeButton.setBounds(width * 3 / 8, height * 4 / 17, width / 4, height / 15);
        filtersPanel.setBounds(width / 3, height / 3, width * 2 / 3, height * 2 / 3);
        sidePanel.setBounds(0, height / 3, width / 3, height * 2 / 3);
        addroomButton.setBounds(width / 9, 100, 100, 100);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ClassroomSearchPage::new);
    }
}
