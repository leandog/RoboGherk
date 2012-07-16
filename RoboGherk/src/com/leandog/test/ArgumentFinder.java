package com.leandog.test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ArgumentFinder {

    private Pattern pattern = Pattern.compile("'.+'");

    public List<String> findArguments(String string) {
        List<String> arguments = new ArrayList<String>();
        
        Matcher matcher = pattern.matcher(string);
       
        while (matcher.find()) {
            arguments.add(removeOutterQuotes(new StringBuilder(matcher.group())));
        }
        
        return arguments;
    }

    private String removeOutterQuotes(StringBuilder match) {
        return match.deleteCharAt(0).deleteCharAt(match.length()-1).toString();
    }
}