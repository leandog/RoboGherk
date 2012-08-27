package com.leandog.robogherk;

import org.junit.Test;
import static org.mockito.Mockito.*;

import android.view.View;

import com.jayway.android.robotium.solo.Solo;

public class DeviceTest {
	
	private Solo solo = mock(Solo.class);
	private ViewFinder viewLocator = mock(ViewFinder.class);
	private Device device = new Device(solo, viewLocator);
	
	@Test
	public void clickFindsTheView() {
		device.click("hello.*world");
		verify(viewLocator).find("hello.*world");
	}
	
	@Test
	public void clickClicksTheFoundView() {
		View view = mock(View.class);
		when(viewLocator.find(anyString())).thenReturn(view);
		device.click("someRegex");
		verify(solo).clickOnView(view);
	}
}