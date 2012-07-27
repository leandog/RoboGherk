package com.leandog.robogherk;

import android.app.Instrumentation;

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
        stepExecutor.tearDown();
    }

    void executeStepDefinition(String action) {
        stepExecutor.call(action);
    }

    static ScenarioEnvironment buildEnvironment(Class<? extends Feature> featureClass, Instrumentation instrumentation) throws RoboGherkException {
        StepDefinitions stepDefinitions = StepDefinitions.forClass(featureClass);
        StepExecutor stepExecutor = new StepExecutor(stepDefinitions);
        Solo solo = new Solo(instrumentation);
        stepExecutor.setUp(instrumentation, solo);
        return new ScenarioEnvironment(stepExecutor, solo);
    }
}
