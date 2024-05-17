
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
    private JCheckBox checkBox52, checkBox53, checkBox54, checkBox63;
    private JCheckBox checkBoxPower, checkBoxLargeDesk;
    private JComboBox<String> dayComboBox;
    private JCheckBox[] timeCheckBoxes;
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
        //各コンポーネントにsetBounds(x, y, width, height) 


        // 教室検索テキスト
        normalTextLabel = new JLabel("教室検索");
        normalTextLabel.setBounds(50, 50, 300, 50);
        initialPage.add(normalTextLabel);

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

        JButton backToInitialPageButton = new JButton("Back to Initial Page");
        backToInitialPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "initialPage");
            }
        });

        JPanel anotherPage = new JPanel();
        anotherPage.add(new JLabel("Another Page"));
        JButton backToInitialFromAnotherButton = new JButton("Back to Initial Page");
        backToInitialFromAnotherButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "initialPage");
            }
        });
        anotherPage.add(backToInitialFromAnotherButton);

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
     

    
    
     // 現在の日付と時刻を取得するメソッド
     private String getCurrentDateTime() {
     

    }

    // ウィンドウのサイズが変更された際にコンポーネントのサイズと位置を調整するメソッド
    private void resizeComponents() {
        // ウィンドウの新しいサイズを取得
        int width = getWidth();
        int height = getHeight();
        int separatorheight = height / 4;

        // 教室検索の文字の新しい位置とサイズ

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
