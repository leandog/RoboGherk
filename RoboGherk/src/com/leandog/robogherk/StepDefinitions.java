package com.leandog.robogherk;

import android.app.Instrumentation;

import com.jayway.android.robotium.solo.Solo;

public abstract class StepDefinitions {
    protected Instrumentation instrumentation;
    protected Solo soloDriver;

    public void setTestDependecies(Instrumentation instrumentation, Solo soloDriver) {
        this.instrumentation = instrumentation;
        this.soloDriver = soloDriver;
    }
}
