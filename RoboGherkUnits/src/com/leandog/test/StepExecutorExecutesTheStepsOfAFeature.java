package com.leandog.test;

import static org.mockito.Mockito.*;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;

public class StepExecutorExecutesTheStepsOfAFeature {
    Feature feature = new SampleFeature("doing things and stuff");
    StepFinder stepFinder = mock(StepFinder.class);
    DoingThingsAndStuffSteps stepStub = mock(DoingThingsAndStuffSteps.class);
    private StepExecutor stepExecutor;

    @Before
    public void setUp() throws Exception {
        when(stepFinder.findStepsFor(feature)).thenReturn(stepStub);
        stepExecutor = new StepExecutor(stepFinder);
    }

    @Test
    public void itExecutesTheStepForAFeature() throws Throwable {
        stepExecutor.call(feature, "I do the first thing");

        verify(stepStub).I_do_the_first_thing();
    }

    @Test(expected = NoStepsFoundException.class)
    public void itTellsMeWhenItCannotFindAStep() throws Throwable {
        stepExecutor.call(feature, "this does not exist");
    }

    @Test(expected = AssertionFailedError.class)
    public void itTellsMeWhenAnAssertionFails() throws Throwable {
        stepStub = new DoingThingsAndStuffSteps();
        when(stepFinder.findStepsFor(feature)).thenReturn(stepStub);

        stepExecutor = new StepExecutor(stepFinder);
        stepExecutor.call(feature, "I should fail an assertion");
    }
    
    
    @Test(expected=IllegalAccessError.class)
    public void itTellsMeTheCauseOfMyStepFailure() throws Throwable{
        stepStub = new DoingThingsAndStuffSteps();
        when(stepFinder.findStepsFor(feature)).thenReturn(stepStub);

        stepExecutor = new StepExecutor(stepFinder);
        stepExecutor.call(feature, "this should blow something else up");
    }
}

class DoingThingsAndStuffSteps implements StepDefinitions {
    public void I_do_the_first_thing() {
    }

    public void I_should_fail_an_assertion() {
        Assert.fail("this blows up");
    }
    
    public void this_should_blow_something_else_up() {
        throw new IllegalAccessError("NO ACCESS FOO!");
    }
    
}
