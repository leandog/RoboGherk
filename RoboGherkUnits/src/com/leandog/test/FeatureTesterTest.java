package com.leandog.test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

public class FeatureTesterTest {
    StepExecutor stepExecutor = mock(StepExecutor.class);
    StepFinder stepFinder = mock(StepFinder.class);
    FeatureTester featureTester = new FeatureTester(stepFinder,stepExecutor);
  
    
    @Test
    public void itFindsStepDefinitionsWhenTheFeatureIsDefined() throws Exception {
       
        featureTester.Feature("magic happened");
        
        verify(stepFinder).findStepsFor("magic happened");
    }
}