package com.alexandre.crychat.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
        return formatter.format(new Date(System.currentTimeMillis()));
    }
    public static String getDateFromTimeStamp(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyy HH:mm:ss");
        return formatter.format(new Date(timestamp));
    }
}
