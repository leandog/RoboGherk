package com.leandog.robogherk;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

public abstract class Feature extends ActivityInstrumentationTestCase2<Activity> {

    private ScenarioEnvironment environment;

    @SuppressWarnings("unchecked")
    public Feature(Class<? extends Activity> activityClass) {
        super((Class<Activity>) activityClass);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        environment = new ScenarioEnvironment(getInstrumentation());
        environment.solo = new Solo(getInstrumentation());
        environment.stepDefinitions = StepDefinitions.forClass(getClass());
        environment.stepExecutor = new StepExecutor(environment.stepDefinitions);
        environment.stepExecutor.setUp(getInstrumentation(), environment.solo);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        environment.tearDown();
    }

    public void Given(String given) {
        call(given);
    }

    public void When(String when) {
        call(when);
    }

    public void Then(String then) {
        call(then);
    }

    public void AndThen(String then) {
        call(then);
    }

    public void AndWhen(String when) {
        call(when);
    }

    private void call(String action) {
        getActivity();
        environment.stepExecutor.call(action);
    }
}
