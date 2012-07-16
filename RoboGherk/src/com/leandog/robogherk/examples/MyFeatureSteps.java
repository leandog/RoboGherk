package com.leandog.robogherk.examples;

import junit.framework.Assert;

import com.leandog.examples.robogherk.HelloRoboGherk;
import com.leandog.robogherk.StepDefinitions;

public class MyFeatureSteps extends StepDefinitions {
    
    public void this_should_fail() {
    }
    
    public void it_fails() {
    }
    
    public void I_am_on_the_HelloRoboGherk_activity() {
        soloDriver.assertCurrentActivity("Not on launcher!", HelloRoboGherk.class);
    }
    
    public void my_arg_should_work_too(String arg) {
        Assert.assertEquals("parameter", arg);
    }
}
