package com.leandog.robogherk;

import android.app.Instrumentation;

import com.jayway.android.robotium.solo.Solo;

public class ScenarioEnvironment {
    
    public Solo solo;
    public StepExecutor stepExecutor;
    public StepDefinitions stepDefinitions;

    public ScenarioEnvironment(Instrumentation instrumentation) {
    }

    void tearDown() {
        solo.finishOpenedActivities();
    }
}
