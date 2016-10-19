package com.github.funyoung.looker.util;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Andy on 2016/3/21.
 */
public final class TimeUtil {
    
    private TimeUtil() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    /**
     * @return "HH:mm:ss"
     */
    private static String getCurTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * @return "yyyy-MM-dd HH:mm:ss"
     */
    public static String getCurDayTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date currentTime = new Date();
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * @return "HH:mm"
     */
    @NonNull
    public static String getTime() {
        String timeString = getCurTime();
        String[] timeArr = timeString.split(":");
        return timeArr[0] + ":" + timeArr[1];
    }
}
