package com.leandog.test;

import java.util.List;

import org.junit.Test;

public class TiesFeaturesToSteps {

    
    @Test
    public void itFindsTheStepDefinitionForAFeature() {
//        Given a given or a when or a then
//        When I look in the *correct* step file
//        I find a step that matches the given/when/then
        
        
        FeatureRunner featureRunner = new FeatureRunner(new SampleFeature("fake"));
        featureRunner.match(new Given("my head hurts"));
        
        
    }
    
    
}
