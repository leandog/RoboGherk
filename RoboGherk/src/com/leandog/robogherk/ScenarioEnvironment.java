package com.leandog.robogherk;

import com.jayway.android.robotium.solo.Solo;

public class ScenarioEnvironment {
    
    private Solo solo;
    private StepExecutor stepExecutor;

    public ScenarioEnvironment(Feature feature, StepExecutor stepExecutor) {
        solo = new Solo(feature.getInstrumentation());
        this.stepExecutor = stepExecutor;
        this.stepExecutor.setUp(feature.getInstrumentation(), solo);
    }

    void tearDown() {
        solo.finishOpenedActivities();
    }

    void executeStepDefinition(String action) {
        stepExecutor.call(action);
    }
}
