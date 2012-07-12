package com.leandog.test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class StepExecutor {

    private final StepFinder stepFinder;

    public StepExecutor(StepFinder stepFinder) {
        this.stepFinder = stepFinder;
    }

    public void call(Feature feature, String action) throws Throwable {
        StepDefinitions stepDefinitions = stepFinder.findStepsFor(feature);

        Method method;
        try {
            method = stepDefinitions.getClass().getMethod(getMethodNameFrom(action));
            method.invoke(stepDefinitions);
        } catch (NoSuchMethodException e) {
            throw new NoStepsFoundException(feature, e);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private String getMethodNameFrom(String action) {
        return action.replace(" ", "_");
    }
    
}