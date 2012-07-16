package com.leandog.test;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import com.leandog.robogherk.ArgumentFinder;

public class ArgumentFinderTest {

    ArgumentFinder finder = new ArgumentFinder("'");

    @Test
    public void itFindsASingleArgumentWithoutSpaces() {
        assertEquals("bar", finder.findArgument("hello 'bar' tender"));
    }
    
    @Test
    public void itFindsASingleArgumentWithSpaces() {
        assertEquals("bar tender", finder.findArgument("hello 'bar tender' ender"));
    }
    
    @Test
    public void itFindsASingleArgumentWithSpecialCharacters() {
       assertEquals("bar - tender!", finder.findArgument("hello 'bar - tender!' ender!!!")); 
    }
    
    @Test
    public void itFindsASingleArgumentWithAnApostrophe() {
        assertEquals("bar's tender", finder.findArgument("hello 'bar's tender' friend"));
    }
}