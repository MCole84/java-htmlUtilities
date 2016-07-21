package com.silvermoon.htmlUtilities.logging;

/**
 * Created by mcole on 7/19/2016.
 */
public class HtmlTagWarning implements IWarning {
    private String message;

    public String getWarning() {
        return message;
    }

    public HtmlTagWarning(String warningMessage) {
        message = warningMessage;
    }
}
