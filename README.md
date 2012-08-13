RoboGherk
=========

RoboGherk provides a cucumber-like syntax for testing Android apps.  It runs
on top of Android instrumentation test cases inside the Android emulator.
RoboGherk Features subclass the `Feature` class, provide the starting
activity, and specify steps with Given(), When(), Then(), and And().  For
example:

```java
package com.leandog.robogherk.exercises;

import com.leandog.robogherk.Feature;
import com.leandog.robogherk.example_app.FirstActivity;

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
```

Steps are dynamically loaded from MyFeatureSteps; for example, the first
Given() line invokes the `I_am_on_the_HelloRoboGherk_activity` method.

Building
--------

* Run 'ant build' from a clone of the git repository.


