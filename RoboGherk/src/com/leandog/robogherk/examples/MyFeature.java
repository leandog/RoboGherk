package com.leandog.robogherk.examples;

import com.leandog.examples.robogherk.HelloRoboGherk;
import com.leandog.robogherk.Feature;

public class MyFeature extends Feature {

    public MyFeature() {
        super(HelloRoboGherk.class, "com.leandog.robogherk.examples");
    }

    public void test_A_working_scenario() throws Exception {
        Given("I am on the HelloRoboGherk activity");
        Then("my 'parameter' should work too");
    }

}