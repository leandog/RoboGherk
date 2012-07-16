package com.leandog.test;


public class FeatureTester {

    private final StepExecutor stepExecutor;
    private String currentFeature;

    public FeatureTester(StepExecutor stepExecutor) {
        this.stepExecutor = stepExecutor;
    }

    public void callAction(String action) throws RoboGherkException {
        stepExecutor.call(currentFeature, action);
    }

    public void feature(String feature) {
        this.currentFeature = feature;
        
    }

}
