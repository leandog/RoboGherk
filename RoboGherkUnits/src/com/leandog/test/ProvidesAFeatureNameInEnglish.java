package com.leandog.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ProvidesAFeatureNameInEnglish {

    @Test
    public void itHasAName() {
        Feature feature = new SampleFeature("name of the feature");
        assertEquals("name of the feature",feature.getFeatureName());
    }
    
    public void theNameMatchesTheClassName() {
        
    }
}
