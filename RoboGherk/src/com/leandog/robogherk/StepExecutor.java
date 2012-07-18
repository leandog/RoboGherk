package com.leandog.robogherk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Instrumentation;

import com.jayway.android.robotium.solo.Solo;

public class StepExecutor {

    private final StepFinder stepFinder;
    private Instrumentation instrumentation;
    private Solo solo;

    public StepExecutor(StepFinder stepFinder) {
        this.stepFinder = stepFinder;
    }

    public void call(Class<? extends Feature> feature, String action) throws RoboGherkException {
        StepDefinitions stepDefinitions = stepFinder.findStepsFor(feature);
        stepDefinitions.setTestDependecies(instrumentation, solo);
        try {
            invoke(action, stepDefinitions);
        } catch (NoSuchMethodException e) {
            throw new NoStepsFoundException(feature.getName(), e);
        } catch (InvocationTargetException e) {
            throw new RoboGherkException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void invoke(String action, StepDefinitions stepDefinitions) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        String methodName = getMethodNameFrom(action);

        if (hasArguments(action)) {
            Method method = stepDefinitions.getClass().getMethod(methodName, String.class);
            method.invoke(stepDefinitions, getArgumentFrom(action));
        } else {
            Method method = stepDefinitions.getClass().getMethod(methodName);
            method.invoke(stepDefinitions);
        }
    }

    private boolean hasArguments(String action) {
        return !"".equals(getArgumentFrom(action));
    }

    public void setup(Instrumentation instrumentation, Solo solo) {
        this.instrumentation = instrumentation;
        this.solo = solo;
    }

    private String getArgumentFrom(String action) {
        return new ArgumentFinder("'").findArgument(action);
    }

    private String getMethodNameFrom(String action) {
        if (hasArguments(action)) {
            action = action.replace("'" + getArgumentFrom(action) + "'", "arg");
        }

        return action.replace(" ", "_");
    }

    public void callSetUpScenario(Class<? extends Feature> testClass) throws RoboGherkException {
        call(testClass, "setUpScenario");
    }
}