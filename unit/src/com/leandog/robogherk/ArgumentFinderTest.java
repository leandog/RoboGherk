package com.leandog.robogherk;

import static org.junit.Assert.assertEquals;


import org.junit.Test;

import com.leandog.robogherk.ArgumentFinder;

public class ArgumentFinderTest {

    ArgumentFinder finder = new ArgumentFinder("'");

    @Test
    public void it_finds_a_single_argument_without_spaces() {
        assertEquals("bar", finder.findArgument("hello 'bar' tender"));
    }
    
    @Test
    public void it_finds_a_single_argument_with_spaces() {
        assertEquals("bar tender", finder.findArgument("hello 'bar tender' ender"));
    }
    
    @Test
    public void it_finds_a_single_argument_with_special_characters() {
       assertEquals("bar - tender!", finder.findArgument("hello 'bar - tender!' ender!!!")); 
    }
    
    @Test
    public void it_finds_a_single_argument_with_an_apostrophe() {
        assertEquals("bar's tender", finder.findArgument("hello 'bar's tender' friend"));
    }
}
