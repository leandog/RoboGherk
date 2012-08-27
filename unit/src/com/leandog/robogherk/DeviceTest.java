package com.leandog.robogherk;

import org.junit.Test;
import static org.mockito.Mockito.*;

import com.jayway.android.robotium.solo.Solo;

public class DeviceTest {
	
	private Solo solo = mock(Solo.class);
	private Device device = new Device(solo);
	
	@Test
	public void clickWithStringParamCallsClickOnText() {
		when(solo.waitForText(anyString(), anyInt(), anyLong())).thenReturn(true);
		device.click("Foo");
		verify(solo).clickOnText("Foo");
	}
}