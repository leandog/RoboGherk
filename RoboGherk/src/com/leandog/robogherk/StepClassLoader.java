package com.leandog.robogherk;

public interface StepClassLoader {

    public Class<?> loadClass(String className) throws ClassNotFoundException;

}