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
}
