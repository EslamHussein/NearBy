package com.cognitev.base.error;

import android.support.annotation.DrawableRes;

import com.cognitev.utils.Defs;

/**
 * Created by Eslam Hussein on 10/14/17.
 */

public class UiError {
    private
    @Defs.ErrorsDisplayTypes
    int errorsDisplayTypes;
    private String message;
    private
    @DrawableRes
    int image;

    public int getErrorsDisplayTypes() {
        return errorsDisplayTypes;
    }

    public void setErrorsDisplayTypes(@Defs.ErrorsDisplayTypes int errorsDisplayTypes) {
        this.errorsDisplayTypes = errorsDisplayTypes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getImage() {
        return image;
    }

    public void setImage(@DrawableRes int image) {
        this.image = image;
    }
}
