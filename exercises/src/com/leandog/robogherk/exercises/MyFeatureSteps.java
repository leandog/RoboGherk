package com.leandog.robogherk.exercises;

import junit.framework.Assert;

import com.leandog.robogherk.Device;
import com.leandog.robogherk.StepDefinitions;
import com.leandog.robogherk.example_app.FirstActivity;

public class MyFeatureSteps extends StepDefinitions {

    private boolean isTrueWhenSetupeScenarioIsCalled = false;
    private Device device;

    @Override
    public void setUpScenario() {
        isTrueWhenSetupeScenarioIsCalled = true;
        device = new Device(soloDriver);
    }

    public void things_that_should_have_occured_in_setup_have() {
        Assert.assertTrue(isTrueWhenSetupeScenarioIsCalled);
    }

    public void I_am_on_the_HelloRoboGherk_activity() {
        soloDriver.assertCurrentActivity("Not on launcher!", FirstActivity.class);
    }

    public void my_arg_should_work_too(String arg) {
        Assert.assertEquals("parameter", arg);
    }

    public void I_go_to_another_screen() {
        soloDriver.clickOnText("click me");
    }

    public void I_select_arg_from_the_spinner(String number) {
        device.selectValueOnSpinner(com.leandog.robogherk.example_app.R.id.spinner, number);
    }
}
