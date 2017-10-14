package com.cognitev.utils;


import android.support.annotation.StringRes;

import com.cognitev.App;

/**
 * Created by Eslam Hussein on a10/30/2016.
 */

public class TextUtils {
    public static String getString(@StringRes int resId) {
        return App.get().getString(resId);
    }
}
