package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassroomPostPage extends JFrame {
    private JComboBox<String> buildingComboBox;
    private JTextField roomField;
    private JButton submitButton;
    private JTextArea resultArea;
    private JSlider congestionSlider;
    private JSlider internetSlider;

    public ClassroomPostPage() {
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
        String[] buildings = {"","52号館", "53号館", "54号館", "63号館"};
        buildingComboBox = new JComboBox<>(buildings);
        inputPanel.add(buildingComboBox, gbc);

        // 「号室」テキストフィールドの作成
        gbc.gridx = 1;
        roomField = new JTextField(5); // 10はテキストフィールドの幅を示します
        inputPanel.add(roomField, gbc);

        // 「号室」ラベルの作成
        JLabel roomLabel = new JLabel("号室");
        gbc.gridx = 2;
        inputPanel.add(roomLabel, gbc);

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
        congestionSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 3); // 最小値:0, 最大値:4, 初期値:2
        congestionSlider.setMajorTickSpacing(1); // メジャーティックの間隔
        congestionSlider.setPaintTicks(true); // ティックを描画する
        congestionSlider.setPaintLabels(true); // ラベルを描画する
        inputPanel.add(congestionSlider, gbc);

        // ネット環境ラベルの作成
        gbc.gridy = 3;
        gbc.gridwidth = 2; // 2列分の幅を持つ
        JLabel internetLabel = new JLabel("ネット環境の良さ");
        inputPanel.add(internetLabel, gbc);

        // ネット環境スライダーの作成
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL; // 水平方向に拡張
        gbc.gridwidth = 2; // 2列分の幅を持つ
        internetSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 3); // 最小値:0, 最大値:4, 初期値:2
        internetSlider.setMajorTickSpacing(1); // メジャーティックの間隔
        internetSlider.setPaintTicks(true); // ティックを描画する
        internetSlider.setPaintLabels(true); // ラベルを描画する
        inputPanel.add(internetSlider, gbc);

        // 投稿ボタンの作成
        gbc.gridy = 5;
        gbc.gridwidth = 1; // 2列分の幅を持つ
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
        String searchText = building + room + "号室\n混雑度: " + congestionLevel + "\nネット環境: " + internetLevel;
        // 仮の検索結果
        String newresult = "投稿内容:\n\n " + searchText + "\n\nご協力ありがとうございました。\n\n";
        resultArea.append(newresult);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ClassroomPostPage post = new ClassroomPostPage();
                post.setVisible(true);
            }
        });
    }
}

