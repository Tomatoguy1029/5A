import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.Timer;

public class Front extends JFrame {
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private JLabel normalTextLabel;
    private JLabel placeLabel;
    private JLabel conditionLabel;
    private JCheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7;
    private JButton switchToAnotherPageButtonA; // ボタンA
    private JButton switchToAnotherPageButtonB; // ボタンB
    private JButton switchToLoginPageButtonC; // ボタンC
    private JSeparator separator; // セパレータ

    public Front() {
        setTitle("検索画面");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500);

        cardLayout = new CardLayout();
        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        // 最初の画面のコンテンツ
        JPanel initialPage = new JPanel();
        initialPage.setLayout(null); // 自由な配置のためにレイアウトマネージャーを無効にする

        // 教室検索テキスト
        normalTextLabel = new JLabel("教室検索");
        normalTextLabel.setBounds(50, 50, 300, 50);
        initialPage.add(normalTextLabel);

        placeLabel = new JLabel("・場所");
        placeLabel.setBounds(50, 50, 300, 50);
        initialPage.add(placeLabel);

        conditionLabel = new JLabel("・条件");
        conditionLabel.setBounds(50, 50, 300, 50);
        initialPage.add(conditionLabel);

        // チェックボックス付きの箇条書き
        checkBox1 = new JCheckBox("52号館");
        checkBox1.setBounds(50, 100, 300, 30);
        initialPage.add(checkBox1);

        checkBox2 = new JCheckBox("53号館");
        checkBox2.setBounds(50, 140, 300, 30);
        initialPage.add(checkBox2);

        checkBox3 = new JCheckBox("54号館");
        checkBox3.setBounds(50, 180, 300, 30);
        initialPage.add(checkBox3);

        checkBox4 = new JCheckBox("63号館");
        checkBox4.setBounds(50, 100, 300, 30);
        initialPage.add(checkBox4);

        checkBox5 = new JCheckBox("電源あり");
        checkBox5.setBounds(50, 140, 300, 30);
        initialPage.add(checkBox5);

        checkBox6 = new JCheckBox("机が広い");
        checkBox6.setBounds(50, 180, 300, 30);
        initialPage.add(checkBox6);

        checkBox7 = new JCheckBox("長時間利用可");
        checkBox7.setBounds(50, 100, 300, 30);
        initialPage.add(checkBox7);

        // ボタンA
        switchToAnotherPageButtonA = new JButton(getCurrentDateTime()); // 初期状態で現在の日付と時刻を表示
        switchToAnotherPageButtonA.setBounds(100, 100, 200, 70); // ボタンの位置とサイズを設定
        switchToAnotherPageButtonA.setFont(new Font("Arial", Font.PLAIN, 14)); // フォントの設定
        switchToAnotherPageButtonA.addActionListener(new ActionListener() { // ボタンAのアクションリスナー
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "anotherPage");
            }
        });
        initialPage.add(switchToAnotherPageButtonA);

        // セパレータ
        separator = new JSeparator();
        separator.setBounds(100, 180, 400, 10); // セパレータの位置とサイズを設定
        initialPage.add(separator);

        // ボタンB
        switchToAnotherPageButtonB = new JButton("検索");
        switchToAnotherPageButtonB.setBounds(100, 200, 200, 70); // ボタンの位置とサイズを設定
        switchToAnotherPageButtonB.setFont(new Font("Arial", Font.PLAIN, 14)); // フォントの設定
        switchToAnotherPageButtonB.addActionListener(new ActionListener() { // ボタンBのアクションリスナー
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "anotherPage");
            }
        });
        initialPage.add(switchToAnotherPageButtonB);

        // ボタンC
        switchToLoginPageButtonC = new JButton("ログイン・会員登録");
        switchToLoginPageButtonC.setBounds(350, 10, 30, 30); // ボタンCの位置とサイズを設定
        switchToLoginPageButtonC.setFont(new Font("Arial", Font.PLAIN, 12)); // フォントの設定
        switchToLoginPageButtonC.addActionListener(new ActionListener() { // ボタンCのアクションリスナー
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "loginPage");
            }
        });
        initialPage.add(switchToLoginPageButtonC);

        // もう一つのページのコンテンツ
        JPanel anotherPage = new JPanel();
        anotherPage.add(new JLabel("Another Page"));
        JButton backToInitialPageButton = new JButton("Back to Initial Page");
        backToInitialPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "initialPage");
            }
        });
        anotherPage.add(backToInitialPageButton);

        // ログインページのコンテンツ
        JPanel loginPage = new JPanel();
        loginPage.add(new JLabel("Login Page"));
        JButton backToInitialFromLoginButton = new JButton("Back to Initial Page");
        backToInitialFromLoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "initialPage");
            }
        });
        loginPage.add(backToInitialFromLoginButton);

        cardPanel.add(initialPage, "initialPage");
        cardPanel.add(anotherPage, "anotherPage");
        cardPanel.add(loginPage, "loginPage");

        getContentPane().add(cardPanel);

        // ウィンドウのサイズ変更イベントをリスナーで検知してボタンのサイズと位置を調整
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeComponents();
            }
        });

        setVisible(true);

        // ボタンAのテキストを1分ごとに更新するためのタイマーを開始
        startButtonUpdateTimer(switchToAnotherPageButtonA);
    }

    // ボタンのテキストを更新するタイマーを開始するメソッド
    private void startButtonUpdateTimer(JButton button) {
        Timer timer = new Timer(60000, new ActionListener() { // 1分ごとに更新
            @Override
            public void actionPerformed(ActionEvent e) {
                if (button == switchToAnotherPageButtonA) {
                    button.setText(getCurrentDateTime()); // ボタンAの場合、現在の日付と時刻を設定
                }
            }
        });
        timer.setInitialDelay(0); // タイマーの初期遅延を0ミリ秒に設定
        timer.start();
    }

    // 現在の日付と時刻を取得するメソッド
    private String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(new Date());
    }

    // ウィンドウのサイズが変更された際にコンポーネントのサイズと位置を調整するメソッド
    private void resizeComponents() {
        // ウィンドウの新しいサイズを取得
        int width = getWidth();
        int height = getHeight();
        int startx = width / 5;
        int separatorheight = height / 4;

        // 教室検索の文字の新しい位置とサイズ
        normalTextLabel.setBounds(startx + 15, separatorheight + 15, 300, 50);
        normalTextLabel.setFont(new Font("Arial", Font.PLAIN, 20)); // フォントの設定

        int fontsize = 15; // 基本の文字サイズ

        // 場所
        placeLabel.setBounds(startx, separatorheight + 50, 300, 50);
        placeLabel.setFont(new Font("Arial", Font.PLAIN, fontsize + 2)); // フォントの設定
        checkBox1.setBounds(startx + 75, separatorheight + 60, 300, 30);
        checkBox1.setFont(new Font("Arial", Font.PLAIN, fontsize)); // フォントの設定
        checkBox2.setBounds(startx + 75, separatorheight + 90, 300, 30);
        checkBox2.setFont(new Font("Arial", Font.PLAIN, fontsize)); // フォントの設定
        checkBox3.setBounds(startx + 75, separatorheight + 120, 300, 30);
        checkBox3.setFont(new Font("Arial", Font.PLAIN, fontsize)); // フォントの設定
        checkBox4.setBounds(startx + 75, separatorheight + 150, 300, 30);
        checkBox4.setFont(new Font("Arial", Font.PLAIN, fontsize)); // フォントの設定

        // 条件
        conditionLabel.setBounds(startx, separatorheight + 200, 300, 50);
        conditionLabel.setFont(new Font("Arial", Font.PLAIN, fontsize + 2)); // フォントの設定
        checkBox5.setBounds(startx + 75, separatorheight + 210, 300, 30);
        checkBox5.setFont(new Font("Arial", Font.PLAIN, fontsize)); // フォントの設定
        checkBox6.setBounds(startx + 75, separatorheight + 240, 300, 30);
        checkBox6.setFont(new Font("Arial", Font.PLAIN, fontsize)); // フォントの設定
        checkBox7.setBounds(startx + 75, separatorheight + 270, 300, 30);
        checkBox7.setFont(new Font("Arial", Font.PLAIN, fontsize)); // フォントの設定

        // ボタンAの新しい位置とサイズを計算して設定
        switchToAnotherPageButtonA.setBounds(width / 6, height / 9, width * 2 / 3, height / 8);
        // ボタンAのフォントと新しいサイズを設定
        switchToAnotherPageButtonA.setFont(new Font("Arial", Font.PLAIN, width / 15)); // フォントの設定

        // セパレータの新しい位置とサイズを計算して設定
        separator.setBounds(0, separatorheight, width, 10);

        // ボタンBの新しい位置とサイズを計算して設定
        switchToAnotherPageButtonB.setBounds(width * 7 / 9, separatorheight + 20, width / 8, height / 10);

        // ボタンBのフォントと新しいサイズを設定
        switchToAnotherPageButtonB.setFont(new Font("Arial", Font.PLAIN, width / 40)); // フォントの設定

        // ボタンCの新しい位置とサイズを計算して設定
        switchToLoginPageButtonC.setBounds(width * 7 / 9, 10, width / 7, height / 30);

        // ボタンCのフォントと新しいサイズを設定
        switchToLoginPageButtonC.setFont(new Font("Arial", Font.PLAIN, width / 80)); // フォントの設定
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Front();
            }
        });
    }
}
