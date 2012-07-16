package com.leandog.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class ArgumentFinderTest {

    ArgumentFinder finder = new ArgumentFinder();

    @Test
    public void itFindsASingleArgumentWithoutSpaces() {
        assertEquals("bar", finder.findArguments("hello 'bar' tender").get(0));
    }
    
    @Test
    public void itFindsASingleArgumentWithSpaces() {
        assertEquals("bar tender", finder.findArguments("hello 'bar tender' ender").get(0));
    }
    
    @Test
    public void itFindsASingleArgumentWithSpecialCharacters() {
       assertEquals("bar - tender!", finder.findArguments("hello 'bar - tender!' ender!!!").get(0)); 
    }
    
    @Test
    public void itFindsASingleArgumentWithAnApostrophe() {
        assertEquals("bar's tender", finder.findArguments("hello 'bar's tender' friend").get(0));
    }
   
}

class ArgumentFinder {

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