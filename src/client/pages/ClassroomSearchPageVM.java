package src.client.pages;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.client.service.Query;
import src.client.service.QueryType;
import src.client.service.QueryParameter;
import src.client.service.DayTime;

public class ClassroomSearchPageVM {
    private ClassroomSearchPage page;
    private Query query;

    public ClassroomSearchPageVM(ClassroomSearchPage page) {
        this.page = page;
        this.query = new Query();
        DayTime day = new DayTime();

        // System.out.println("Initializing ClassroomSearchPageVM");

        page.searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Search button clicked"); // デバッグメッセージ
                try {
                    query.setType(QueryType.SEARCH);
                    // クエリのパラメータを設定
                    query.setParameter(QueryParameter.BUILDING, (String) page.buildingComboBox.getSelectedItem());
                    query.setParameter(QueryParameter.DAY, (String) page.dayComboBox.getSelectedItem());
                    for (int i = 0; i < page.timeCheckBoxes.length; i++) {
                        if (page.timeCheckBoxes[i].isSelected()) {
                            query.setParameter(QueryParameter.valueOf("TIME" + (i + 1)), "1");
                        }
                    }
                    if (page.checkBoxPower.isSelected())
                        query.setParameter(QueryParameter.POWER, "1");
                    if (page.checkBoxLargeDesk.isSelected())
                        query.setParameter(QueryParameter.LARGE_DESK, "1");
                    if (page.checkBoxQuiet.isSelected())
                        query.setParameter(QueryParameter.QUIET, "1");
                    if (page.checkBoxNetwork.isSelected())
                        query.setParameter(QueryParameter.NETWORK, "1");

                    query.sendQueryToServer();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        page.addroomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    query.setType(QueryType.ADD_CLASSROOM_INFO);
                    // クエリのパラメータを設定
                    query.setParameter(QueryParameter.BUILDING, (String) page.buildingComboBox.getSelectedItem());
                    query.setParameter(QueryParameter.DAY, (String) page.dayComboBox.getSelectedItem());
                    for (int i = 0; i < page.timeCheckBoxes.length; i++) {
                        if (page.timeCheckBoxes[i].isSelected()) {
                            query.setParameter(QueryParameter.valueOf("TIME" + (i + 1)), "1");
                        }
                    }
                    if (page.checkBoxPower.isSelected())
                        query.setParameter(QueryParameter.POWER, "1");
                    if (page.checkBoxLargeDesk.isSelected())
                        query.setParameter(QueryParameter.LARGE_DESK, "1");
                    if (page.checkBoxQuiet.isSelected())
                        query.setParameter(QueryParameter.QUIET, "1");
                    if (page.checkBoxNetwork.isSelected())
                        query.setParameter(QueryParameter.NETWORK, "1");

                    query.sendQueryToServer();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        page.timeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String currentTimeSlot = day.getCurrentTimeSlot();
                if (!currentTimeSlot.equals("お休み")) {
                    try {
                        query.setType(QueryType.SEARCH);
                        // クエリのパラメータを設定
                        query.setParameter(QueryParameter.BUILDING, (String) page.buildingComboBox.getSelectedItem());
                        query.setParameter(QueryParameter.DAY, day.getCurrentDayOfWeek());
                        query.setParameter(QueryParameter.valueOf(currentTimeSlot), "1");
                        query.sendQueryToServer();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(page, "現在はお休みです", "お知らせ", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }
}
