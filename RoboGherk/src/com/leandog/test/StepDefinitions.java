package com.leandog.test;

import android.app.Instrumentation;

import com.jayway.android.robotium.solo.Solo;

public interface StepDefinitions {
    public void setTestDependecies(Instrumentation instrumentation, Solo soloDriver);

}
