package com.leandog.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.leandog.robogherk.NoStepsFoundException;
import com.leandog.robogherk.StepDefinitions;
import com.leandog.robogherk.StepFinder;

public class StepFinderTest {
    
    @Test
    public void findsStepsForFeature() throws Exception {
        StepDefinitions steps = new StepFinder("com.leandog.test.fake").findStepsFor("local fake");
        String actualClassName = steps.getClass().getName();
        assertEquals("com.leandog.test.fake.LocalFakeSteps", actualClassName);
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