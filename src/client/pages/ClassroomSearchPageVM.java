package src.client.pages;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import src.client.service.Query;
import src.client.service.QueryType;
import src.client.service.QueryParameter;
import src.client.service.DayTime;
import src.client.Main;

public class ClassroomSearchPageVM {
    private static ClassroomSearchPageVM instance = null;
    private Query query;
    private String generatedQuery;


    public ClassroomSearchPageVM(ClassroomSearchPage page) {
        this.query = new Query();
        DayTime day = new DayTime();
        this.generatedQuery = null;

        // System.out.println("Initializing ClassroomSearchPageVM");

        ActionListener searchActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // System.out.println("Search button clicked"); // デバッグメッセージ
                try {
                    query.setType(QueryType.SEARCH);
                    // クエリのパラメータを設定
                    String building = (String) page.buildingComboBox.getSelectedItem();
                    int buildingNumber = Integer.parseInt(building.replace("号館", ""));
                    query.setParameter(QueryParameter.BUILDING, String.valueOf(buildingNumber));

                    query.setParameter(QueryParameter.DAY, (String) page.dayComboBox.getSelectedItem());
                    for (int i = 0; i < page.timeCheckBoxes.length; i++) {
                        if (page.timeCheckBoxes[i].isSelected()) {
                            query.setParameter(QueryParameter.valueOf("TIME" + (i + 1)), "1");
                        }
                    }
                    if (page.checkBoxPower.isSelected())
                        query.setParameter(QueryParameter.OUTLETS, "1");
                    if (page.checkBoxLargeDesk.isSelected())
                        query.setParameter(QueryParameter.DESK_SIZE, "1");
                    if (page.checkBoxQuiet.isSelected())
                        query.setParameter(QueryParameter.CROWDEDNESS, "1");
                    if (page.checkBoxNetwork.isSelected())
                        query.setParameter(QueryParameter.NETWORK, "1");

                    synchronized (Main.lock) {
                        generatedQuery = query.getQuery();
                        Main.lock.notify();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        // System.out.println("Adding search button action listener");
        page.searchButton.addActionListener(searchActionListener);

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
                        query.setParameter(QueryParameter.OUTLETS, "1");
                    if (page.checkBoxLargeDesk.isSelected())
                        query.setParameter(QueryParameter.DESK_SIZE, "1");
                    if (page.checkBoxQuiet.isSelected())
                        query.setParameter(QueryParameter.CROWDEDNESS, "1");
                    if (page.checkBoxNetwork.isSelected())
                        query.setParameter(QueryParameter.NETWORK, "1");

                    synchronized (Main.lock) {
                        generatedQuery = query.getQuery();
                        Main.lock.notify();
                    }
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
                        query.setParameter(QueryParameter.DAY, day.getCurrentDayOfWeek());

                        query.setParameter(QueryParameter.valueOf(currentTimeSlot), "1");

                        synchronized (Main.lock) {
                            generatedQuery = query.getQuery();
                            System.out.println("Generated Query: " + generatedQuery); // デバッグメッセージ
                            Main.lock.notify();
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(page, "現在はお休みです", "お知らせ",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    public String getGeneratedQuery() {
        return generatedQuery;
    }

    public void clearGeneratedQuery() {
        generatedQuery = null;
    }

    public void clearQueryParameters() {
        query = new Query();
    }

    public static ClassroomSearchPageVM getInstance(ClassroomSearchPage page) {
        if (instance == null) {
            instance = new ClassroomSearchPageVM(page);
        }
        return instance;
    }
}
