package com.leandog.robogherk;

public class NoStepsFoundException extends RoboGherkException {
    private static final long serialVersionUID = 1L;
    private final String feature;
    private String className;
    private Exception exception;

    public NoStepsFoundException(String feature, Exception exception) {
        this.feature = feature;
        this.exception = exception;
    }

    @Override
    public String getMessage() {
        return makeMessage();
    }

    public String makeMessage() {
        if (className != null )
            return String.format("\nSteps not found: %s\nPlease create class : %s", feature, className);

        return String.format("\nNo step found within the feature %s\n Please implement %s\n\n", feature, exception.getMessage());
    }
}
