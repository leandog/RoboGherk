package com.leandog.robogherk;

import com.jayway.android.robotium.solo.Solo;

public class ScenarioEnvironment {
    
    private Solo solo;
    private StepExecutor stepExecutor;

    public ScenarioEnvironment(StepExecutor stepExecutor, Solo solo) {
        this.solo = solo;
        this.stepExecutor = stepExecutor;
    }

    void tearDown() {
        solo.finishOpenedActivities();
    }

    void executeStepDefinition(String action) {
        stepExecutor.call(action);
    }
}
