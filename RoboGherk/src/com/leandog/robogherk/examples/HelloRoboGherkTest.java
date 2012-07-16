package com.leandog.robogherk.examples;

import com.leandog.examples.robogherk.HelloRoboGherk;
import com.leandog.robogherk.RoboGherkTester;

public class HelloRoboGherkTest extends RoboGherkTester {

    public HelloRoboGherkTest() {
        super(HelloRoboGherk.class, "com.leandog.robogherk.examples");
    }

    @Override
    public void defineFeature() {
        Feature("my feature");
    }
    
    public void testWorks() throws Exception {
        Scenario("a working test");
        Given("this should work");
        Then("my 'parameter' should work too");
    }

}
