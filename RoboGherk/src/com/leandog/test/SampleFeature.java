package com.leandog.test;

public class SampleFeature implements Feature {

    private final String featureName;

    public SampleFeature(String featureName) {
        this.featureName = featureName;

    }

    @Override
    public String getFeatureName() {
        return featureName;
    }
}
