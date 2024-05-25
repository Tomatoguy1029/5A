package src.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClassroomPostPage extends JFrame {
    private JFrame Postframe;
    private JPanel inputPanel;
    private JComboBox<String> roomComboBox;
    private JButton submitButton, returnButton;
    private JTextArea resultArea;
    private JSlider congestionSlider;
    private JSlider internetSlider;

    public ClassroomPostPage() {
        Postframe = new JFrame("投稿画面");
        Postframe.setSize(400, 500);
        Postframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 入力パネルの作成
        inputPanel = new JPanel(new GridBagLayout());
        Postframe.add(inputPanel, BorderLayout.NORTH);

        //GridBagContraintsの導入
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 余白を設定
        gbc.fill = GridBagConstraints.HORIZONTAL; // コンポーネントが余白を埋めるようにする


        // 「教室」ラベルの作成
        JLabel roomLabel = new JLabel("教室");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        inputPanel.add(roomLabel, gbc);

        // 教室選択リストの作成
        String[] rooms = getClassroomName();
        roomComboBox = new JComboBox<>(rooms);
        gbc.gridx = 1;
        inputPanel.add(roomComboBox, gbc);

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
        JLabel internetLabel = new JLabel("ネット環境");
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

        //ボタンの作成
        gbc.gridwidth = 1; // 1列分の幅を持つ

        // 投稿ボタンの作成
        gbc.gridy = 5;
        submitButton = new JButton("投稿");
        inputPanel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        // 検索結果表示エリアの作成
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        String defaultText = "投稿内容:\n\nまだ何も投稿されていません。\n";
        resultArea.setText(defaultText);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        Postframe.add(scrollPane, BorderLayout.CENTER);

        //戻るボタンの作成
        returnButton = new JButton("戻る");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Postframe.dispose();
            }
        });
        Postframe.add(returnButton, BorderLayout.SOUTH);

        Postframe.setVisible(true);
    }

    private void performSearch() {
        // ここに検索ロジックを実装する
        String room = (String) roomComboBox.getSelectedItem();
        int congestionLevel = congestionSlider.getValue();
        int internetLevel = internetSlider.getValue();
        String searchText = room + "\n混雑度: " + congestionLevel + "\nネット環境: " + internetLevel;
        // 投稿内容の表示
        String result = "投稿内容:\n\n " + searchText + 
                            "\n\nご協力ありがとうございました。\n" + 
                            "「戻る」ボタンを押して検索画面へお戻りください。\n";
        resultArea.setText(result);

        submitButton.setVisible(false);
    }

    private String[] getClassroomName(){
        Object[][] info = new SampleData().getSampleData();
        String[] ClassroomName = new String[info.length];
        for (int i=0; i<info.length;i++){
            ClassroomName[i] = (String)info[i][0];
        }
        return ClassroomName;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClassroomPostPage();
            }
        });
    }
}

