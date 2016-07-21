package com.silvermoon.htmlUtilities.htmlElements;

import java.util.ArrayList;

/**
 * Created by Michael Cole on 7/19/2016.
 */
public class HtmlTag {
    private int lineNumber;
    private String elementName;
    private ArrayList<Attribute> attributes;

    public String getElementName() {
        return elementName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public ArrayList<Attribute> getAttributes() {
        return attributes;
    }

    public HtmlTag(String name, ArrayList<Attribute> attributes, int lineNumber) {
        elementName = name;
        this.attributes = new ArrayList<Attribute>();
        for (Attribute att : attributes) {
            this.attributes.add(att);
        }
        this.lineNumber=lineNumber;

    }

}
