package com.leandog.robogherk;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.Instrumentation;

import com.jayway.android.robotium.solo.Solo;

public class StepExecutor {

    private final StepDefinitions stepDefinitions;

    public StepExecutor(StepDefinitions stepDefinitions) {
        this.stepDefinitions = stepDefinitions;
    }

    public void call(String action) {
        try {
            invoke(action);
        } catch (NoSuchMethodException e) {
            throw new NoStepsFoundException(stepDefinitions.getClass().getName(), e);
        } catch (InvocationTargetException e) {
            throw new RoboGherkException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void invoke(String action) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
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

    public void setUp(Instrumentation instrumentation, Solo solo) throws RoboGherkException {
        stepDefinitions.setTestDependecies(instrumentation, solo);
        call("setUpScenario");
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

    public void tearDown() {
        call("cleanUpScenario");
    }
}