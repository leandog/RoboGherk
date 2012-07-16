package com.leandog.test;

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
    

    public void call(String feature, String action) throws RoboGherkException {
        StepDefinitions stepDefinitions = stepFinder.findStepsFor(feature);
        stepDefinitions.setTestDependecies(instrumentation, solo);
        Method method;
        try {
            
            String methodName = getMethodNameFrom(action);
            
            
            method = stepDefinitions.getClass().getMethod(methodName);
            method.invoke(stepDefinitions);
        } catch (NoSuchMethodException e) {
            throw new NoStepsFoundException(feature, e);
        } catch (InvocationTargetException e) {
            throw new RoboGherkException(e.getCause());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getMethodNameFrom(String action) {
        
        action.replaceAll("", "arg");
        
        
        return action.replace(" ", "_");
    }


    public void setup(Instrumentation instrumentation, Solo solo) {
        this.instrumentation = instrumentation;
        this.solo = solo;
    }
}