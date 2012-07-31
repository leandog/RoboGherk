package com.leandog.robogherk.exercises;

import com.leandog.examples.robogherk.FirstActivity;
import com.leandog.robogherk.Feature;

public class MyFeature extends Feature {

    public MyFeature() {
        super(FirstActivity.class);
    }

    public void test_A_working_scenario() {
        Given("I am on the HelloRoboGherk activity");
        Then("my 'parameter' should work too");
        And("things that should have occured in setup have");
        And("I go to another screen");
    }

}
