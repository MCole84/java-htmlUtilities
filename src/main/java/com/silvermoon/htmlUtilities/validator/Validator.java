package com.Silvermoon.HtmlUtilities.validator;


import com.Silvermoon.HtmlUtilities.HtmlElements.htmlTag;
import com.Silvermoon.HtmlUtilities.Logging.IError;
import com.Silvermoon.HtmlUtilities.Logging.IWarning;
import com.Silvermoon.HtmlUtilities.parser.Parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

/**
 * Created by Michael Cole on 7/20/2016.
 */
public class Validator extends Parser{
    private ArrayList<IError> errors;
    private ArrayList<IWarning> warnings;


    /**
     * Takes a file containing HTML source code and parses it to determine if it is valid
     * @param htmlFile the file destination containing the source code
     * @return true if the code has no errors, false otherwise.
     */
    public boolean validateTagLayout(File htmlFile){
        String source="";
        try {
            Scanner reader = new Scanner(htmlFile);
            while(reader.hasNext()){
                source+=reader.nextLine()+"\r";
            }
            reader.close();
        }catch(FileNotFoundException ex){
            System.out.println("File not found");
        }
        return validateTagLayout(source);
    }

    /**
     * Takes HTML source as a string and returns the results on if the code is in a valid format
     * @param html the string representation of the html source code
     * @return true if the code has no errors, false otherwise
     */
    public boolean validateTagLayout(String html){
        errors=new ArrayList<IError>();
        warnings=new ArrayList<IWarning>();
        tags = getTags(html);



        return tags.size() > 0;
    }

}
