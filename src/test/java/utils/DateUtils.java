package utils;

import java.util.Calendar;

public class DateUtils {

    private static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    public static int getCurrentYear(){
        return getCalendar().get(Calendar.YEAR);
    }
    public static int getCurrentMonth(){
        return getCalendar().get(Calendar.MONTH);
    }

    public static int getCurrentDate(){
        return getCalendar().get(Calendar.DATE);
    }
}
