package com.github.funyoung.looker.util;

import android.content.Context;

import com.sdsmdg.tastytoast.TastyToast;

/**
 * Created by yangfeng on 16-10-20.
 */

public class ToastUtil {
    public static void show(Context context, String text, int length, int type) {
        TastyToast.makeText(context, text, length, type);
    }
    public static void show(Context context, String text) {
        show(context, text, TastyToast.LENGTH_SHORT, TastyToast.DEFAULT);
    }
    public static void showLong(Context context, String text) {
        show(context, text, TastyToast.LENGTH_LONG, TastyToast.DEFAULT);
    }
    public static void showInfo(Context context, String text) {
        show(context, text, TastyToast.LENGTH_SHORT, TastyToast.INFO);
    }
}
