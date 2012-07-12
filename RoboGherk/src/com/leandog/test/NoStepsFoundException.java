package com.leandog.test;

public class NoStepsFoundException extends Exception {
    private static final long serialVersionUID = 1L;
    private final Feature feature;
    private String className;
    private NoSuchMethodException exception;

    public NoStepsFoundException(Feature feature, String className) {
        this.feature = feature;
        this.className = className;
    }

    public NoStepsFoundException(Feature feature, NoSuchMethodException exception) {
        this.feature = feature;
        this.exception = exception;

    }

    @Override
    public String getMessage() {
        return makeMessage();
    }

    public String makeMessage() {
        if (className != null )
            return String.format("\nSteps not found: %s\nPlease create class : %s", feature.getFeatureName(), className);

        return String.format("\nNo step found within the feature %s\n Please implement %s\n\n", feature.getFeatureName(), exception.getMessage());
    }
}
