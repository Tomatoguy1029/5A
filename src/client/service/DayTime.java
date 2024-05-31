package src.client.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.Timer;

public class DayTime {
    JLabel day;// 曜日
    JLabel time;// 時間

    public String getCurrentDateTime() {// 現在の日時を取得
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        return dateFormat.format(new Date());
    }

    public String getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "日曜";
            case Calendar.MONDAY:
                return "月曜";
            case Calendar.TUESDAY:
                return "火曜";
            case Calendar.WEDNESDAY:
                return "水曜";
            case Calendar.THURSDAY:
                return "木曜";
            case Calendar.FRIDAY:
                return "金曜";
            case Calendar.SATURDAY:
                return "土曜";
            default:
                return "";
        }
    }

    public String getCurrentTimeSlot() {// 現在の時間帯を取得
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        if (dayOfWeek == Calendar.SUNDAY) {
            return "お休み";
        }

        int totalMinutes = hour * 60 + minute;

        if (totalMinutes >= 530 && totalMinutes < 630) {
            return "TIME1";
        } else if (totalMinutes >= 630 && totalMinutes < 740) {
            return "TIME2";
        } else if (totalMinutes >= 740 && totalMinutes < 890) {
            return "TIME3";
        } else if (totalMinutes >= 890 && totalMinutes < 1005) {
            return "TIME4";
        } else if (totalMinutes >= 1005 && totalMinutes < 1120) {
            return "TIME5";
        } else if (totalMinutes >= 1120 && totalMinutes < 1235) {
            return "TIME6";
        } else if (totalMinutes >= 1245 && totalMinutes < 1295) {
            return "TIME7";
        } else {
            return "お休み";
        }
    }

    private String convertTimeSlotToPeriod(String timeSlot) {
        switch (timeSlot) {
            case "TIME1":
                return "1限";
            case "TIME2":
                return "2限";
            case "TIME3":
                return "3限";
            case "TIME4":
                return "4限";
            case "TIME5":
                return "5限";
            case "TIME6":
                return "6限";
            case "TIME7":
                return "7限";
            default:
                return "お休み";
        }
    }

    public void TimeUpdate(JLabel text) { // 1分ごとに現在の日時と時間帯を更新
        Timer timer = new Timer(60000, e -> {
            String currentTimeSlot = getCurrentTimeSlot();
            String formattedTimeSlot = convertTimeSlotToPeriod(currentTimeSlot);
            text.setText(getCurrentDateTime() + " (" + getCurrentDayOfWeek() + ") " + formattedTimeSlot);
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public String formatCurrentDateTime() {
        String currentTimeSlot = getCurrentTimeSlot();
        String formattedTimeSlot = convertTimeSlotToPeriod(currentTimeSlot);
        return getCurrentDateTime() + " (" + getCurrentDayOfWeek() + ") " + formattedTimeSlot;
    }
}
