package com.assignment.Utility;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Calendar;

public class Utility {
    public static Boolean IsTollFreeDate(XMLGregorianCalendar date)
    {
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        int dayOfMonth = date.getDay();

        if (day == Calendar.SATURDAY || day == Calendar.SUNDAY) return true;

        if (year == 2013)
        {
            if ((month == 1 && dayOfMonth == 1) ||
                    (month == 3 && (dayOfMonth == 28 || dayOfMonth == 29)) ||
                    (month == 4 && (dayOfMonth == 1 || dayOfMonth == 30)) ||
                    (month == 5 && (dayOfMonth == 1 || dayOfMonth == 8 || dayOfMonth == 9)) ||
                    (month == 6 && (dayOfMonth == 5 || dayOfMonth == 6 || dayOfMonth == 21)) ||
                    (month == 7) ||
                    (month == 11 && dayOfMonth == 1) ||
                    (month == 12 && (dayOfMonth == 24 || dayOfMonth == 25 || dayOfMonth == 26 || dayOfMonth == 31)))
            {
                return true;
            }
        }
        return false;
    }
}
