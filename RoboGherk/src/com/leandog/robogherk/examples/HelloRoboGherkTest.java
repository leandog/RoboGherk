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
    
    public void test_A_working_scenario() throws Exception {
        Given("I am on the HelloRoboGherk activity");
        Then("my 'parameter' should work too");
    }

}
