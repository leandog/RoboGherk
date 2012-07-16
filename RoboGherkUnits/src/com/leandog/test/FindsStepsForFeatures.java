package com.leandog.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import android.app.Instrumentation;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.robogherk.NoStepsFoundException;
import com.leandog.robogherk.StepDefinitions;
import com.leandog.robogherk.StepFinder;

public class FindsStepsForFeatures {

    
    @Test
    public void findsStepsForFeature() throws Exception {
        StepDefinitions steps = new StepFinder("com.leandog.test").findStepsFor("local fake");
        String actualClassName = steps.getClass().getName();
        assertEquals("com.leandog.test.LocalFakeSteps", actualClassName);
    }

    @Test
    public void findsStepsForAFeatureInADifferentPackage() throws Exception {
        StepDefinitions steps = new StepFinder("com.leandog.test.fake").findStepsFor("fake");
        assertEquals("com.leandog.test.fake.FakeSteps", steps.getClass().getName());
    }

    @Test(expected = NoStepsFoundException.class)
    public void itLetsMeKnowWhenItCantFindSteps() throws Exception {
        new StepFinder("com.leandog.test").findStepsFor("this is fake");
    }
     
}

class LocalFakeSteps extends StepDefinitions {
    @Override
    public void setTestDependecies(Instrumentation instrumentation, Solo soloDriver) {
    }
}