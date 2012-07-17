package com.leandog.robogherk;


public class StepFinder {

    private final String packageName;
    private final StepClassLoader stepClassLoader;

    public StepFinder(String packageName, StepClassLoader stepClassLoader) {
        this.packageName = packageName;
        this.stepClassLoader = stepClassLoader;
    }

    public StepDefinitions findStepsFor(Class<? extends Feature> testCaseClass) throws RoboGherkException {
        String stepDefinitionClassName = testCaseClass.getSimpleName().concat("Steps");
        return loadStepsClass(testCaseClass.getSimpleName(), packageName + "." + stepDefinitionClassName);
    }
    
    private StepDefinitions loadStepsClass(String feature, String className) throws NoStepsFoundException {
        StepDefinitions steps = null;
        try {
            Class<?> stepClass = stepClassLoader.loadClass(className);
            steps = (StepDefinitions) stepClass.newInstance();
        } catch (Exception e) {
            throw new NoStepsFoundException(feature, e);
        }
        return steps;
    }
    
}
