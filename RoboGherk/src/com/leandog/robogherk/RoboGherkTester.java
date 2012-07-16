package com.leandog.robogherk;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

@SuppressWarnings("rawtypes")
public abstract class RoboGherkTester extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private StepExecutor stepExecutor;
    private String currentFeature;
    private final String packageName;

    public abstract void defineFeature();

    @SuppressWarnings("unchecked")
    public RoboGherkTester(Class activityClass, String packageName) {
        super(activityClass);
        this.packageName = packageName;
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        stepExecutor = new StepExecutor(new StepFinder(packageName));
        defineFeature();
    }

    public void Feature(String feature) {
        this.currentFeature = feature;
        stepExecutor.setup(getInstrumentation(), solo);
    }

    public void Given(String given) throws RoboGherkException {
        call(given);
    }

    public void When(String when) throws RoboGherkException {
        call(when);
    }

    public void Then(String then) throws RoboGherkException {
        call(then);
    }

    public void AndThen(String then) throws RoboGherkException {
        call(then);
    }

    public void AndWhen(String when) throws RoboGherkException {
        call(when);
    }

    private void call(String action) throws RoboGherkException {
        validate();
        stepExecutor.call(currentFeature, action);
    }

    private void validate() {
        getActivity();
        if (currentFeature == null) {
            fail("\nNo Feature has been defined!\nPlease define a feature by adding:\n void defineFeature() {\n\tFeature(\"My feature\");\n}\n ");
        }
    }
}
