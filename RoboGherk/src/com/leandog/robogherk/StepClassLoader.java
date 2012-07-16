package com.leandog.robogherk;

public interface StepClassLoader {

    Class<?> loadClass(String className) throws ClassNotFoundException;

}