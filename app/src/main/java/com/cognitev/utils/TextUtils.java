package com.cognitev.utils;


import android.support.annotation.StringRes;
import android.util.Patterns;

import com.cognitev.App;

import java.util.regex.Matcher;

/**
 * Created by Eslam Hussein on a10/30/2016.
 */

public class TextUtils {

    private static final String EMPTY_STRING_PATTERN = "^$|\\s+";


    private static Matcher matcher;

    public static String getString(@StringRes int resId) {
        return App.get().getString(resId);
    }

    public static boolean validateEmail(String email) {

        matcher = Patterns.EMAIL_ADDRESS.matcher(email);
        return matcher.matches();

    }


    public static boolean validatePassword(String password) {
        return password.length() >= 8
                && password.length() <= 20;
    }


    public static boolean isEmptyString(String str) {
        if (str == null || str.length() == 0 ||
                str.matches(EMPTY_STRING_PATTERN)) {
            return true;
        }
        return false;
    }

}
