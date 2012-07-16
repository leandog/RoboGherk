package com.leandog.examples.robogherk.test;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.examples.robogherk.HelloRoboGherk;
import com.leandog.test.FeatureTester;
import com.leandog.test.RoboGherkException;
import com.leandog.test.StepExecutor;
import com.leandog.test.StepFinder;

import android.test.ActivityInstrumentationTestCase2;


public class RoboGherkTest extends ActivityInstrumentationTestCase2<HelloRoboGherk> {
    
    Solo solo;
    FeatureTester featureTester;
    StepFinder stepFinder = new StepFinder("com.leandog.examples.robogherk.test.step_defs");
    StepExecutor stepExecutor = new StepExecutor(stepFinder);
    private String feature;

    public RoboGherkTest() {
        super(HelloRoboGherk.class);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        featureTester = new FeatureTester(stepExecutor);
        Feature("subscribe to mailing list");
    }
    
    public void Feature(String feature) {
        this.feature = feature;
        stepExecutor.setup(getInstrumentation(), solo);
    }
   
    public void Scenario(String scenario) {
        getActivity();
    }

    public void given(String given) throws RoboGherkException {
        stepExecutor.call(feature, given);
    }
    
    public void test_SubScribingToEmailList() throws Exception {
        Scenario("doing things");
        given("I want to subscribe to leandogs mail list");
//        When("I provide my email");
//        Then("I see a thank you message");
//        And("I see my email address");
    }

}
