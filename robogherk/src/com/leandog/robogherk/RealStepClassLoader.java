package com.leandog.robogherk;

public class RealStepClassLoader implements StepClassLoader {

    @Override
    public Class<?> loadClass(String className) throws ClassNotFoundException {
        return Class.forName(className);
    }

}
