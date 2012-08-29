package com.leandog.robogherk;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Test;

import com.leandog.test.fake.MyFeatureSteps;

public class StepFinderTest {

    private StepClassLoader loader = mock(StepClassLoader.class);

    @Test
    public void checks_feature_package_for_steps() throws Exception {
        doReturn(MyFeatureSteps.class).when(loader).loadClass(anyString());
       
        new StepFinder(loader).findStepsFor(Feature.class);
        
        verify(loader).loadClass(eq(Feature.class.getPackage().getName() + ".FeatureSteps"));
    }
    
    @Test(expected = NoStepsFoundException.class)
    public void throws_no_steps_found_exception_when_cannot_load_steps() throws Exception {
        when(loader.loadClass(anyString())).thenThrow(new ClassNotFoundException());
        
        new StepFinder(loader).findStepsFor(Feature.class);
    }
}
