
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
    public void finishesOpenActivitiesOnTearDown() {
        scenarioEnvironment.tearDown();
        verify(solo).finishOpenedActivities();
    }
    
    @Test
    public void executingAStepIsDelegatedToTheExecutor() {
       scenarioEnvironment.executeStepDefinition("make members private"); 
       verify(stepExecutor).call("make members private");
    }

}
