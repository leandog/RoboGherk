package com.leandog.test;

public class NoStepsFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    private final String featureName;
    private final String className;

    public NoStepsFoundException(String featureName, String className) {
        this.featureName = featureName;
        this.className = className;
    }
    
    @Override
    public String getMessage() {
        return "\nSteps not found: " + featureName + "\nPlease create class : " + className;
    }
}
