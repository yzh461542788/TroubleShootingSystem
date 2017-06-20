package com.fudan.ooad.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zihao on 2017/6/19.
 */
public class DateUtil {
    public static Date getCurrentDate() {
        return Date.from(Calendar.getInstance().toInstant());
    }

    public static Date getDateFromCurrentDate(int year, int month, int day, int hour, int min, int second) {
        return Date.from(getCurrentDate().toInstant().plusSeconds(
                (((year * 365 + month * 12 + day) * 24 + hour) * 60 + min) * 60 + second
        ));
    }
}
