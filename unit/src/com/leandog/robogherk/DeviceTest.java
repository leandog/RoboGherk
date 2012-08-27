package com.leandog.robogherk;

import org.junit.Test;
import static org.mockito.Mockito.*;

import com.jayway.android.robotium.solo.Solo;

public class DeviceTest {
	
	private Solo solo = mock(Solo.class);
	private ViewFinder viewLocator = mock(ViewFinder.class);
	private Device device = new Device(solo, viewLocator);
	
	@Test
	public void clickFindsTheTextView() {
		when(solo.waitForText(anyString(), anyInt(), anyLong())).thenReturn(true);
		device.click("hello.*world");
		verify(viewLocator).find("hello.*world");
	}
}