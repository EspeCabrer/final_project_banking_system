package com.ironhack.banksystem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;
import static java.util.Calendar.DATE;

public class Utils {

    public static int getYearBetweenToDates(Date date1, Date date2) {
        Calendar a = getCalendar(date1);
        Calendar b = getCalendar(date2);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }


    public static Calendar getCalendar(Date date) {
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTime(date);
            return cal;
        }

}
