package com.silvermoon.htmlUtilities.logging;

/**
 * Created by mcole on 7/19/2016.
 */
public class HtmlTagError implements IError {
    private String message;

    public String getError() {
        return message;
    }

    public HtmlTagError(String errorMessage) {
        message = errorMessage;
    }
}
