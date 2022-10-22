package com.ironhack.banksystem;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static java.util.Calendar.*;
import static java.util.Calendar.DATE;

public class Utils {

    public static int getYearsBetweenToDates(Date date1, Date date2) {
        Calendar calendar1 = getCalendar(date1);
        Calendar calendar2 = getCalendar(date2);
        int diff = calendar2.get(YEAR) - calendar1.get(YEAR);
        if (calendar1.get(MONTH) > calendar2.get(MONTH) ||
                (calendar1.get(MONTH) == calendar2.get(MONTH) && calendar1.get(DATE) > calendar2.get(DATE))) {
            diff--;
        }
        return diff;
    }

    public static int getMonthsBetweenToDates(Date date1, Date date2) {
        Calendar calendar1 = getCalendar(date1);
        Calendar calendar2 = getCalendar(date2);
        int yearsInBetween = calendar2.get(Calendar.YEAR) -
                calendar1.get(Calendar.YEAR);

        int monthsDiff = calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH);
                return yearsInBetween*12 + monthsDiff;



    }


    public static Calendar getCalendar(Date date) {
            Calendar cal = Calendar.getInstance(Locale.getDefault());
            cal.setTime(date);
            return cal;
        }

}
