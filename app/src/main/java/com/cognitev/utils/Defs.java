package com.cognitev.utils;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Eslam Hussein on 10/14/17.
 */

public class Defs {
    public static final int SNACK_BAR = 1;
    public static final int IN_SCREEN = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            SNACK_BAR,
            IN_SCREEN
    })
    public @interface ErrorsDisplayTypes {
    }

}
