package com.leandog.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.leandog.robogherk.RealStepClassLoader;
import com.leandog.robogherk.StepDefinitions;
import com.leandog.robogherk.StepFinder;
import com.leandog.robogherk.examples.MyFeatureTest;

public class StepFinderTest {

    @Test
    public void findsStepsForClass() throws Exception {
        StepDefinitions steps = new StepFinder("com.leandog.test.fake", new RealStepClassLoader()).findStepsFor(MyFeatureTest.class);
        String actualClassName = steps.getClass().getName();
        assertEquals("com.leandog.test.fake.MyFeatureSteps", actualClassName);
    }
     
}