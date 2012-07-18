package com.leandog.robogherk.examples;

import junit.framework.Assert;

import com.leandog.examples.robogherk.HelloRoboGherk;
import com.leandog.robogherk.StepDefinitions;

public class MyFeatureSteps extends StepDefinitions {
   
    public static boolean isTrueWhenSetupeScenarioIsCalled = false;
    
    @Override
    public void setUpScenario() {
       isTrueWhenSetupeScenarioIsCalled = true; 
    }
    
    public void things_that_should_have_occured_in_setup_have() {
        Assert.assertTrue(isTrueWhenSetupeScenarioIsCalled);
    }
    
    public void I_am_on_the_HelloRoboGherk_activity() {
        soloDriver.assertCurrentActivity("Not on launcher!", HelloRoboGherk.class);
    }
    
    public void my_arg_should_work_too(String arg) {
        Assert.assertEquals("parameter", arg);
    }
}
