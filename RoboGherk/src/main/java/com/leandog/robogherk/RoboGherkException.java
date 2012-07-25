package com.leandog.robogherk;


public class RoboGherkException extends AssertionError {
    private static final long serialVersionUID = 743458777778964419L;

    public RoboGherkException() {
    }

    public RoboGherkException(Throwable throwable) {
        super(throwable);
    }
}
