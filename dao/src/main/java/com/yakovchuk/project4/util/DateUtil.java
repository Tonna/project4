package com.yakovchuk.project4.util;

public class DateUtil {
    public static String getCurrentDateTime() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        int year = calendar.get(calendar.YEAR);
        int month = calendar.get(calendar.MONTH);
        int dayOfMonth = calendar.get(calendar.DAY_OF_MONTH);
        int hour = calendar.get(calendar.HOUR_OF_DAY);
        int minute = calendar.get(calendar.MINUTE);

        return year + "-" + month + "-" + dayOfMonth + " " + hour + ":" + minute;
    }

}
