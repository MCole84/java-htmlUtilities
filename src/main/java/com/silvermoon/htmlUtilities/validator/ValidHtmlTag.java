package com.Silvermoon.HtmlUtilities.validator;

import java.util.ArrayList;

/**
 * Created by Michael Cole on 6/21/2016.
 * Represents a valid HTML tag including its name, all the attributes that it requires and a list of the optional attributes
 * that can be included
 */
class ValidHtmlTag {

    String elementName;
    private ArrayList<String> _allowedAtts;

    boolean isDepricated;

    public ValidHtmlTag(String name, boolean isDeprecated, ArrayList<String> allowedAttributes) {
        _allowedAtts=allowedAttributes;
        this.isDepricated = isDeprecated;
    }

    public ArrayList<String> allowedAttributes(){
        return _allowedAtts;
    }
}