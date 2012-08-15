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
        environment = ScenarioEnvironment.buildEnvironment(getClass(), getInstrumentation());
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        environment.tearDown();
    }

    public void Given(String stepName) {
        call(stepName);
    }

    public void When(String stepName) {
        call(stepName);
    }

    public void Then(String stepName) {
        call(stepName);
    }

    public void And(String stepName) {
        call(stepName);
    }

    private void call(String action) {
        prepareToExecute();
        environment.executeStepDefinition(action);
    }

    private void prepareToExecute() {
        getActivity();
    }
}
