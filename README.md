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
Given() line invokes the I\_am\_on\_the\_HelloRoboGherk\_activity() method.

Building
--------

* Run 'ant build' from a clone of the git repository.

Development
-----------

We did incremental development using an existing app. We didn't write unit
tests for everything, but all new features should be test-driven.

The exercises are acceptance tests on RoboGherk that run against the included
example app. 

Note on Patches/Pull Requests
-----------------------------
* Fork the project.
* Test-drive your feature addition or bug fix. If you need help with 
  unit-testing Android, start a conversation by email. We'll help. 
* Send us a pull request. Bonus points for topic branches.

Who are we?
-----------

We're the maddogs @ leandog.com. Making the world a better place for mobile
development. :)
