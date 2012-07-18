package com.leandog.robogherk;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public abstract class Feature extends ActivityInstrumentationTestCase2<Activity> {

    private Solo solo;
    private StepExecutor stepExecutor;
    private StepDefinitions stepDefinitions;

    @SuppressWarnings("unchecked")
    public Feature(Class<? extends Activity> activityClass) {
        super((Class<Activity>) activityClass);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        stepDefinitions = StepDefinitions.forClass(getClass());
        stepExecutor = new StepExecutor(stepDefinitions);
        stepExecutor.setUp(getInstrumentation(), solo);
    }

    public void Given(String given) throws RoboGherkException {
        call(given);
    }

    public void When(String when) throws RoboGherkException {
        call(when);
    }

    public void Then(String then) throws RoboGherkException {
        call(then);
    }

    public void AndThen(String then) throws RoboGherkException {
        call(then);
    }

    public void AndWhen(String when) throws RoboGherkException {
        call(when);
    }

    private void call(String action) throws RoboGherkException {
        getActivity();
        stepExecutor.call(action);
    }
}
