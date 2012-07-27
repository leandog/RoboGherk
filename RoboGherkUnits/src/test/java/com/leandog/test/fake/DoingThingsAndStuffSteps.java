package com.leandog.test.fake;

import junit.framework.Assert;

import com.leandog.robogherk.StepDefinitions;

public class DoingThingsAndStuffSteps extends StepDefinitions {
    public void I_do_the_first_thing() {
    }

    public void I_should_fail_an_assertion() {
        Assert.fail("this blows up");
    }

    public void this_should_blow_something_else_up() {
        throw new IllegalAccessError("NO ACCESS FOO!");
    }

    public void I_say_arg_to_Dave(String arg) {

    }
}