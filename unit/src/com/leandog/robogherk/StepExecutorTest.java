package com.leandog.robogherk;

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
    Instrumentation instrumentation;
    Solo solo;
    DoingThingsAndStuffSteps stepStub = mock(DoingThingsAndStuffSteps.class);
    StepExecutor stepExecutor = new StepExecutor(stepStub);
    
    @Before
    public void setUp() {
        instrumentation = mock(Instrumentation.class);
        solo = mock(Solo.class);
    }

    @Test
    public void it_provides_test_dependencies_to_the_steps() throws RoboGherkException {
        stepExecutor.setUp(instrumentation, solo);
        stepExecutor.call("I do the first thing");

        verify(stepStub).setTestDependecies(instrumentation, solo);
    }

    @Test
    public void it_executes_the_step_for_a_feature() throws Throwable {
        stepExecutor.call("I do the first thing");

        verify(stepStub).I_do_the_first_thing();
    }

    @Test
    public void it_executes_the_step_for_a_feature_with_arguments() throws RoboGherkException {
        stepExecutor.call("I say 'hello - Dave' to Dave");

        verify(stepStub).I_say_arg_to_Dave("hello - Dave");
    }

    @Test(expected = NoStepsFoundException.class)
    public void it_tells_me_when_it_cannot_find_a_step() throws Throwable {
        stepExecutor.call("this does not exist");
    }

    @Test(expected = RoboGherkException.class)
    public void it_tells_me_when_an_assertion_fails() throws Throwable {
        stepStub = new DoingThingsAndStuffSteps();

        stepExecutor = new StepExecutor(stepStub);
        stepExecutor.call("I should fail an assertion");
    }

    @Test(expected = RoboGherkException.class)
    public void it_tells_me_the_cause_of_my_step_failure() throws Throwable {
        stepStub = new DoingThingsAndStuffSteps();

        stepExecutor = new StepExecutor(stepStub);
        stepExecutor.call("this should blow something else up");
    }
    
    @Test
    public void it_can_pass_through_call_to_set_up_scenario() throws Exception {
        StepDefinitions stepStub = mock(StepDefinitions.class);
       
        stepExecutor = new StepExecutor(stepStub);
        stepExecutor.setUp(instrumentation, solo);
     
        verify(stepStub).setUpScenario();
    }
    
    @Test
    public void overriding_setup_scenario_is_not_required() throws RoboGherkException {
        StepDefinitions stepStub = new DoingThingsAndStuffSteps();
       
        stepExecutor = new StepExecutor(stepStub);
        stepExecutor.setUp(instrumentation, solo);
    }

}
