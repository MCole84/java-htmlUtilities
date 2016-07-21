package com.silvermoon.htmlUtilities.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import com.silvermoon.htmlUtilities.htmlElements.*;

/**
 * Created by Michael Cole on 7/19/2016.
 * Class to parse out the html tags from a specified source
 */
public class Parser {

    protected ArrayList<HtmlTag> tags;

    /**
     * Searches a source file and extracts the html tags
     * @param source the File that contains the html source code
     * @return ArrayList of HtmlTag items
     */
    public ArrayList<HtmlTag> Parse(File source) {
        tags = new ArrayList<HtmlTag>();

        return tags;
    }
//region static methods


// endregion
//region Tag element methods

    public ArrayList<HtmlTag> getTags(){
        return tags;
    }
    /**
     * locates and stores the HtmlTag information without validating it
     */
    public void lookForHtmlTags(String html) {
        tags = getTags(html);

        if (tags.isEmpty()) {
            System.out.println("no tags found");
        } else {
            System.out.println("Found tags");
        }
    }

    /**
     * locates and stores the HtmlTag information without validating it
     */
    public void lookForHtmlTags(File html) {
        try {
            Scanner scanner = new Scanner(html);
            StringBuilder source = new StringBuilder();
            while (scanner.hasNext()) {
                source.append(scanner.nextLine() + "\n");
            }
            lookForHtmlTags(source.toString());
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
    }

    public ArrayList<HtmlTag> getTags(String html) {
        int currentLine = 1;
        ArrayList<HtmlTag> tags = new ArrayList<HtmlTag>();
        HtmlTag lastTagAdded;

        int index = 0;
        int openIndex = 0;
        int closeIndex = 0;
        while (index < html.length()) {
            if (html.charAt(index) == '<') {
                openIndex = index;
            } else {
                if (html.charAt(index) == '>') {
                    closeIndex = index + 1;
                    //add the tag, if it is a closing tag skip attributes check
                    tags.add(new HtmlTag(getTagName(html.substring(openIndex, closeIndex)),
                        (html.charAt(openIndex + 1) != '/' ?
                            getTagAttributes(html.substring(openIndex, closeIndex)) : new ArrayList<Attribute>())
                        , currentLine));
                    lastTagAdded = tags.get(tags.size() - 1);
                    if (lastTagAdded.getElementName().equals("script")) {
                        index = byPassJsCode(html, index);
                    }

                } else if (html.charAt(index) == '\n' || html.charAt(index) == '\r') {
                    currentLine++;
                }
            }
            index++;
        }

        return tags;
    }


    /**
     * gets the name of the html element tag being passed in as a string
     *
     * @param currentTag html tag to get the name from
     * @return String representing the element name
     */
    private String getTagName(String currentTag) {
        String name;
        if (currentTag.contains(" ")) {
            name = currentTag.substring(1, currentTag.indexOf(" "));
            if (currentTag.substring(currentTag.length() - 2).equals("/>")) {
                name += "/";
            }
        } else {
            name = currentTag.substring(1, currentTag.length() - 1);
        }
        return name;
    }

    /**
     * @param html
     * @param startIndex
     * @return
     */
    private int byPassJsCode(String html, int startIndex) {
        int index = startIndex;
        int tagStart = 0;
        int tagEnd = 0;
        for (int i = startIndex; i < html.length(); i++) {
            if (html.charAt(i) == '<') {
                tagStart = i;
                for (int x = tagStart; x < html.length(); x++) {
                    if (html.charAt(x) == '>') {
                        if (html.substring(tagStart, x + 1).equals("</script>")) {
                            return tagStart - 1;
                        } else {
                            i = x;
                            break;
                        }
                    }
                }
            }
        }
        return index;
    }
//endregion
//region Attribute methods
    /**
     * extract all of the html tags from a provided string
     *
     * @param tag string representation of the html used
     * @return ArrayList of type string that contains each individual tag found.
     */
    private ArrayList<Attribute> getTagAttributes(String tag) {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        int attNameStart = 0; //the starting index for the current Attribute name
        int attNameEnd = 0; //the ending index for the current Attribute name

        int attValueStart = 0; //the starting index for the current Attribute's value
        int attValueEnd = 0; //the ending index for the current Attribute's value

        for (int i = 0; i < tag.length(); i++) {
            if (tag.charAt(i) == ' ' && attNameStart == 0) {
                //found the character that starts the next Attribute
                attNameStart = i + 1;
            } else if (tag.charAt(i) == '=' && attNameEnd == 0) {
                //end of the Attribute's name
                attNameEnd = i;
                //search for the value assigned to the Attribute
                attValueStart = attNameEnd + 1;
                boolean startsWithSingleQuote = (tag.charAt(attValueStart) == '\'');
                for (int v = attValueStart + 1; v < tag.length(); v++) {
                    //if an Attribute starts with a ' character but contains " characters they are ignored
                    //if the next character is a space ensure that there is no comma indicating that it is a multipart Attribute value
                    if ((tag.charAt(v) == '\"' && !startsWithSingleQuote) || tag.charAt(v) == '\'' || (tag.charAt(v) == ' ' && (tag.charAt(v - 1) != ','))) {
                        attValueEnd = v;
                        attributes.add(new Attribute(tag.substring(attNameStart, attNameEnd),
                            tag.substring(attValueStart, attValueEnd + 1)));
                        attNameStart = 0;
                        attNameEnd = 0;
                        i = v - 1;
                        break;
                    } else if (v == tag.length() - 1) {
                        attributes.add(new Attribute(tag.substring(attNameStart, attNameEnd),
                            tag.substring(attValueStart, v)));
                        i = v;
                        break;
                    }
                }
            }
        }
        return attributes;
    }

    /**
     * if a concatenated string is located, search until the proper end of the string is located
     *
     * @param tag
     * @param valueStart
     * @return
     */
    int getAttStringConcat(String tag, int valueStart) {
        int attributeEnd = 0;
        boolean isNewStringStart = false;
        for (int i = valueStart; i < tag.length(); i++) {
            if (tag.charAt(i) == '"') {
                if (isNewStringStart == false) {
                    isNewStringStart = true;
                } else {
                    if (tag.charAt(i + 1) == ' ') {
                        if (tag.charAt(i + 2) == '+') {
                            i = getAttStringConcat(tag, i + 2);
                        } else {
                            attributeEnd = i;
                            break;
                        }
                    }
                }
            }
        }
        return attributeEnd;
    }
//endregion
}
