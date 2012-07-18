package com.leandog.robogherk;

import com.jayway.android.robotium.solo.Solo;

public class ScenarioEnvironment {
    
    private Solo solo;
    private StepExecutor stepExecutor;

    public ScenarioEnvironment(Feature feature) {
        solo = new Solo(feature.getInstrumentation());
        StepDefinitions stepDefinitions = StepDefinitions.forClass(feature.getClass());
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
