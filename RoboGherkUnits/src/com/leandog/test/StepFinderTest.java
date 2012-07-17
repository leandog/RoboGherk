package com.leandog.test;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.leandog.robogherk.NoStepsFoundException;
import com.leandog.robogherk.RealStepClassLoader;
import com.leandog.robogherk.StepClassLoader;
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
    
    @Test(expected = NoStepsFoundException.class)
    public void throwsNoStepsFoundExceptionWhenCannotLoadSteps() throws Exception {
        StepClassLoader loader = mock(StepClassLoader.class);
        when(loader.loadClass(anyString())).thenThrow(new ClassNotFoundException());
        new StepFinder("com.leandog.test.fake", loader).findStepsFor(MyFeatureTest.class);
    }
}