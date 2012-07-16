package com.leandog.test;

import static org.junit.Assert.assertEquals;


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