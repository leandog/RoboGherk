package com.leandog.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

import android.app.Instrumentation;

import com.jayway.android.robotium.solo.Solo;
import com.leandog.robogherk.NoStepsFoundException;
import com.leandog.robogherk.RoboGherkException;
import com.leandog.robogherk.StepDefinitions;
import com.leandog.robogherk.StepExecutor;
import com.leandog.test.fake.DoingThingsAndStuffSteps;

public class StepExecutorTest {
    DoingThingsAndStuffSteps stepStub = mock(DoingThingsAndStuffSteps.class);
    StepExecutor stepExecutor;

    @Before
    public void setUp() throws Exception {
        stepExecutor = new StepExecutor(stepStub);
    }

    @Test
    public void itProvidesTestDependenciesToTheSteps() throws RoboGherkException {
        Instrumentation instrumentation = mock(Instrumentation.class);
        Solo solo = mock(Solo.class);
        stepExecutor.setup(instrumentation, solo);
        stepExecutor.call("I do the first thing");

        verify(stepStub).setTestDependecies(instrumentation, solo);
    }

    @Test
    public void itExecutesTheStepForAFeature() throws Throwable {
        stepExecutor.call("I do the first thing");

        verify(stepStub).I_do_the_first_thing();
    }

    @Test
    public void itExecutesTheStepForAFeatureWithArguments() throws RoboGherkException {
        stepExecutor.call("I say 'hello - Dave' to Dave");

        verify(stepStub).I_say_arg_to_Dave("hello - Dave");
    }

    @Test(expected = NoStepsFoundException.class)
    public void itTellsMeWhenItCannotFindAStep() throws Throwable {
        stepExecutor.call("this does not exist");
    }

    @Test(expected = RoboGherkException.class)
    public void itTellsMeWhenAnAssertionFails() throws Throwable {
        stepStub = new DoingThingsAndStuffSteps();

        stepExecutor = new StepExecutor(stepStub);
        stepExecutor.call("I should fail an assertion");
    }

    @Test(expected = RoboGherkException.class)
    public void itTellsMeTheCauseOfMyStepFailure() throws Throwable {
        stepStub = new DoingThingsAndStuffSteps();

        stepExecutor = new StepExecutor(stepStub);
        stepExecutor.call("this should blow something else up");
    }
    
    @Test
    public void itCanPassThroughCallToSetUpScenario() throws Exception {
        StepDefinitions stepStub = mock(StepDefinitions.class);
       
        stepExecutor = new StepExecutor(stepStub);
        stepExecutor.callSetUpScenario();
     
        verify(stepStub).setUpScenario();
    }

}
