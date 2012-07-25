package com.leandog.robogherk;


public class StepFinder {

    private final StepClassLoader stepClassLoader;

    public StepFinder(StepClassLoader stepClassLoader) {
        this.stepClassLoader = stepClassLoader;
    }

    public StepDefinitions findStepsFor(Class<? extends Feature> testCaseClass) {
        String stepDefinitionClassName = testCaseClass.getSimpleName().concat("Steps");
        return loadStepsClass(testCaseClass.getSimpleName(), testCaseClass.getPackage().getName() + "." + stepDefinitionClassName);
    }
    
    private StepDefinitions loadStepsClass(String feature, String className) {
        try {
            Class<?> stepClass = stepClassLoader.loadClass(className);
            return (StepDefinitions) stepClass.newInstance();
        } catch (Exception e) {
            throw new NoStepsFoundException(feature, e);
        }
    }
    
}
