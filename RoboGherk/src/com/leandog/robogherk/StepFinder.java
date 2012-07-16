package com.leandog.robogherk;


public class StepFinder {

    private String packageName;

    public StepFinder(String packageName) {
        this.packageName = packageName;
    }

    public StepDefinitions findStepsFor(Class<? extends RoboGherkTester> testCaseClass) throws RoboGherkException {
        String stepDefinitionClassName = testCaseClass.getSimpleName().replace("Test","Steps");
        return loadStepsClass(testCaseClass.getSimpleName(), packageName + "." + stepDefinitionClassName);
    }
    
    private StepDefinitions loadStepsClass(String feature, String className) throws NoStepsFoundException {
        StepDefinitions steps = null;
        try {
            Class<?> stepClass = Class.forName(className);
            steps = (StepDefinitions) stepClass.newInstance();
        } catch (Exception e) {
            throw new NoStepsFoundException(feature, e);
        }
        return steps;
    }
    
}
