package com.leandog.robogherk;

import android.test.ActivityInstrumentationTestCase2;

import com.jayway.android.robotium.solo.Solo;

@SuppressWarnings("rawtypes")
public abstract class RoboGherkTester extends ActivityInstrumentationTestCase2 {

    private Solo solo;
    private StepExecutor stepExecutor;
    private final String packageName;

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
        getActivity();
        stepExecutor.call(getClass(), action);
    }
}
