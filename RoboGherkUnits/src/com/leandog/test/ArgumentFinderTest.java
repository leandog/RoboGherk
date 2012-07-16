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
   
    @Test
    public void itFindsMultipleArguments() {
        List<String> actualArguments = finder.findArguments("I will be at the 'bar' feeling 'tender' friend");
        
        assertEquals("bar",actualArguments.get(0));
        assertEquals("tender", actualArguments.get(1));
    }

}

class ArgumentFinder {

    public List<String> findArguments(String string) {
        List<String> arguments = new ArrayList<String>();
        Pattern pattern = Pattern.compile("'.+'");
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            StringBuilder match = new StringBuilder(matcher.group());
            match.deleteCharAt(0).deleteCharAt(match.length()-1);
            System.out.println(match.toString());
            arguments.add(match.toString());
        }

        return arguments;
    }

}