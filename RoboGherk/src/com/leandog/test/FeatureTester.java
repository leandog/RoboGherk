package com.leandog.test;


public class FeatureTester {

    
   
    private final StepFinder stepFinder;
    private final StepExecutor stepExecutor;

    public FeatureTester(StepFinder stepFinder, StepExecutor stepExecutor) {
        this.stepFinder = stepFinder;
        this.stepExecutor = stepExecutor;
    }

    public void Feature(String feature) throws NoStepsFoundException {
       stepFinder.findStepsFor(feature); 
    }

    public void Given(String string) {
        // TODO Auto-generated method stub
        
    }

}
