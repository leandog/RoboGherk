package com.leandog.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class KnowsAboutTheAppItIsTesting {

    @Test
    public void itCreatesClassNameFromFeatureName() {
        FeatureFinder featureFinder = new FeatureFinder();
        assertEquals("NameOfFeature", featureFinder.getClassName("name of feature"));
    }
}
