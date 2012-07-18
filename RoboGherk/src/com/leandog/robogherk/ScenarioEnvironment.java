package com.leandog.robogherk;

import com.jayway.android.robotium.solo.Solo;

public class ScenarioEnvironment {
    
    public Solo solo;
    public StepExecutor stepExecutor;
    public StepDefinitions stepDefinitions;

    public ScenarioEnvironment(Feature feature) {
        solo = new Solo(feature.getInstrumentation());
        stepDefinitions = StepDefinitions.forClass(feature.getClass());
        stepExecutor = new StepExecutor(stepDefinitions);
        stepExecutor.setUp(feature.getInstrumentation(), solo);
    }

    void tearDown() {
        solo.finishOpenedActivities();
    }

    void executeStepDefinition(String action) {
        stepExecutor.call(action);
    }

}
