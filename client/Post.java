package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Post extends JFrame {
    private JComboBox<String> buildingComboBox;
    private JTextField roomField;
    private JButton submitButton;
    private JTextArea resultArea;
    private JSlider congestionSlider;
    private JSlider internetSlider;

    public Post() {
        setTitle("投稿");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // レイアウトの設定
        setLayout(new BorderLayout());

        // 入力パネルの作成
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // 余白を設定

        // 号館選択リストの作成
        String[] buildings = {"", "54号館", "55号館", "56号館"};
        buildingComboBox = new JComboBox<>(buildings);
        inputPanel.add(buildingComboBox, gbc);

        // 「号室」ラベルの作成
        JLabel roomLabel = new JLabel("号室");
        gbc.gridx = 1;
        inputPanel.add(roomLabel, gbc);

        // 「号室」テキストフィールドの作成
        gbc.gridx = 2;
        roomField = new JTextField(10); // 10はテキストフィールドの幅を示します
        inputPanel.add(roomField, gbc);

        // 混雑度ラベルの作成
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // 2列分の幅を持つ
        JLabel congestionLabel = new JLabel("混雑度");
        inputPanel.add(congestionLabel, gbc);

        // 混雑度スライダーの作成
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平方向に拡張
        gbc.gridwidth = 2; // 2列分の幅を持つ
        congestionSlider = new JSlider(JSlider.HORIZONTAL, 0, 4, 2); // 最小値:0, 最大値:4, 初期値:2
        congestionSlider.setMajorTickSpacing(1); // メジャーティックの間隔
        congestionSlider.setPaintTicks(true); // ティックを描画する
        congestionSlider.setPaintLabels(true); // ラベルを描画する
        inputPanel.add(congestionSlider, gbc);

        // ネット環境ラベルの作成
        gbc.gridy = 3;
        gbc.gridwidth = 2; // 2列分の幅を持つ
        JLabel internetLabel = new JLabel("ネット環境");
        inputPanel.add(internetLabel, gbc);

        // ネット環境スライダーの作成
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平方向に拡張
        gbc.gridwidth = 2; // 2列分の幅を持つ
        internetSlider = new JSlider(JSlider.HORIZONTAL, 0, 4, 2); // 最小値:0, 最大値:4, 初期値:2
        internetSlider.setMajorTickSpacing(1); // メジャーティックの間隔
        internetSlider.setPaintTicks(true); // ティックを描画する
        internetSlider.setPaintLabels(true); // ラベルを描画する
        inputPanel.add(internetSlider, gbc);

        // 投稿ボタンの作成
        gbc.gridy = 5;
        gbc.gridwidth = 2; // 2列分の幅を持つ
        submitButton = new JButton("投稿");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });
        inputPanel.add(submitButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        // 検索結果表示エリアの作成
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void performSearch() {
        // ここに検索ロジックを実装する
        String building = (String) buildingComboBox.getSelectedItem();
        String room = roomField.getText();
        int congestionLevel = congestionSlider.getValue();
        int internetLevel = internetSlider.getValue();
        String searchText = "号館: " + building + ", 号室: " + room + ", 混雑度: " + congestionLevel + ", ネット環境: " + internetLevel;
        // 仮の検索結果
        String result = "投稿内容: " + searchText + "\nご協力ありがとうございました。\n";
        resultArea.setText(result);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Post post = new Post();
                post.setVisible(true);
            }
        });
    }
}

