
package com.leandog.robogherk;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.jayway.android.robotium.solo.Solo;

public class ScenarioEnvironmentTest {

    Solo solo = mock(Solo.class);
    StepExecutor stepExecutor = mock(StepExecutor.class);
    ScenarioEnvironment scenarioEnvironment = new ScenarioEnvironment(stepExecutor, solo);

    @Test
    public void finishes_open_activities_on_tear_down() {
        scenarioEnvironment.tearDown();
        verify(solo).finishOpenedActivities();
    }
    
    @Test
    public void executing_a_step_is_delegated_to_the_executor() {
       scenarioEnvironment.executeStepDefinition("make members private"); 
       verify(stepExecutor).call("make members private");
    }

}
