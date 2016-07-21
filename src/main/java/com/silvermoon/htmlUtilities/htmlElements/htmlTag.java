package com.silvermoon.htmlUtilities.htmlElements;

import java.util.ArrayList;

/**
 * Created by Michael Cole on 7/19/2016.
 */
public class htmlTag {
    private int lineNumber;
    private String elementName;
    private ArrayList<attribute> attributes;

    public String getElementName() {
        return elementName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public ArrayList<attribute> getAttributes() {
        return attributes;
    }

    public htmlTag(String name, ArrayList<attribute> attributes,int lineNumber) {
        elementName = name;
        this.attributes = new ArrayList<attribute>();
        for (attribute att : attributes) {
            this.attributes.add(att);
        }
        this.lineNumber=lineNumber;

    }

}
