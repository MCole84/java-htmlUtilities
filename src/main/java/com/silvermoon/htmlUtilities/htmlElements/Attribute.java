package com.silvermoon.htmlUtilities.htmlElements;

/**
 * Created by Michael Cole on 6/21/2016.
 * represents an Attribute of an HTML tag and the value that is assigned to that Attribute
 */
public class Attribute {
    private String name;
    private String value;

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }
}