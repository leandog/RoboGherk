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
import com.leandog.robogherk.examples.MyFeature;
import com.leandog.test.fake.MyFeatureSteps;

public class StepFinderTest {

    private StepClassLoader loader = mock(StepClassLoader.class);

    @Test
    public void checksFeaturePackageForSteps() throws Exception {
        doReturn(MyFeatureSteps.class).when(loader).loadClass(anyString());
       
        new StepFinder(loader).findStepsFor(MyFeature.class);
        
        verify(loader).loadClass(eq(MyFeature.class.getPackage().getName() + ".MyFeatureSteps"));
    }
    
    @Test(expected = NoStepsFoundException.class)
    public void throwsNoStepsFoundExceptionWhenCannotLoadSteps() throws Exception {
        when(loader.loadClass(anyString())).thenThrow(new ClassNotFoundException());
        
        new StepFinder(loader).findStepsFor(MyFeature.class);
    }
}