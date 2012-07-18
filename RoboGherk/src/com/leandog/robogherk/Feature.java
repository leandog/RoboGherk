package com.leandog.robogherk;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;


public abstract class Feature extends ActivityInstrumentationTestCase2<Activity> {

    private ScenarioEnvironment environment;

    @SuppressWarnings("unchecked")
    public Feature(Class<? extends Activity> activityClass) {
        super((Class<Activity>) activityClass);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        StepDefinitions stepDefinitions = StepDefinitions.forClass(getClass());
        StepExecutor stepExecutor = new StepExecutor(stepDefinitions);
        environment = new ScenarioEnvironment(this, stepExecutor);
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
        environment.executeStepDefinition(action);
    }
}
