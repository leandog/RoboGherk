
package com.leandog.robogherk;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.jayway.android.robotium.solo.Solo;

public class ScenarioEnvironmentTest {

    private Solo solo = mock(Solo.class);
    private ScenarioEnvironment scenarioEnvironment = new ScenarioEnvironment(null, solo);

    @Test
    public void finishesOpenActivitiesOnTearDown() {
        scenarioEnvironment.tearDown();
        verify(solo).finishOpenedActivities();
    }

}
