package com.leandog.test;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;

import com.leandog.robogherk.NoStepsFoundException;
import com.leandog.robogherk.StepClassLoader;
import com.leandog.robogherk.StepFinder;
import com.leandog.robogherk.examples.MyFeatureTest;
import com.leandog.test.fake.MyFeatureSteps;

public class StepFinderTest {

    private StepClassLoader loader = mock(StepClassLoader.class);

    @Test
    public void findsStepsForClass() throws Exception {
        doReturn(MyFeatureSteps.class).when(loader).loadClass(anyString());
       
        new StepFinder("com.leandog.test.fake", loader).findStepsFor(MyFeatureTest.class);
        
        verify(loader).loadClass(eq("com.leandog.test.fake.MyFeatureSteps"));
    }
    
    @Test(expected = NoStepsFoundException.class)
    public void throwsNoStepsFoundExceptionWhenCannotLoadSteps() throws Exception {
        when(loader.loadClass(anyString())).thenThrow(new ClassNotFoundException());
        
        new StepFinder("com.leandog.test.fake", loader).findStepsFor(MyFeatureTest.class);
    }
}