package com.cognitev.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by Eslam Hussein on 10/12/17.
 */

public class DateUtils {

    public static String getCurrentDate() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("YYYYMMDD");
        String formattedDate = df.format(c.getTime());
        return formattedDate;

    }
}
