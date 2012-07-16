package com.leandog.test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class FeatureTesterTest {
    StepExecutor stepExecutor = mock(StepExecutor.class);
    FeatureTester featureTester = new FeatureTester(stepExecutor);
  
    
    @Test
    public void itCallsGivenStepsForTheFeature() throws Exception {
        featureTester.feature("magic");
        featureTester.callAction("magic happened"); 
        
        verify(stepExecutor).call("magic","magic happened");
    }
    
}