package com.leandog.robogherk;


public class StepFinder {

    private String packageName;

    public StepFinder(String packageName) {
        this.packageName = packageName;
    }

    public StepDefinitions findStepsFor(String feature) throws NoStepsFoundException {
        return loadStepsClass(feature, getClassName(feature));
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
    
    private String getClassName(String featureName) {
        String[] allTheWordsInTheFeatureName = featureName.split(" ");
        return nameOfStepsClassFrom(packageName, allTheWordsInTheFeatureName);
    }

    private String nameOfStepsClassFrom(String packageName, String[] allTheWordsInTheFeatureName) {
        return packageName + "." + camelCase(allTheWordsInTheFeatureName) + "Steps";
    }

    private String camelCase(String[] allTheWordsInTheFeatureName) {
        StringBuilder classNameBuilder = new StringBuilder(allTheWordsInTheFeatureName.length);
        for (String word : allTheWordsInTheFeatureName) {
            classNameBuilder.append(camelCaseThe(word));
        }
        return classNameBuilder.toString();
    }

    private String camelCaseThe(String word) {
        return (word.charAt(0) + "").toUpperCase() + word.substring(1, word.length());
    }

    public StepDefinitions findStepsFor(Class<? extends RoboGherkTester> testCaseClass) throws RoboGherkException {
        String stepDefinitionClassName = testCaseClass.getSimpleName().replace("Test","Steps");
        return loadStepsClass(testCaseClass.getSimpleName(), packageName + "." + stepDefinitionClassName);
    }
}
