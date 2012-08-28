package com.leandog.robogherk;

import static org.junit.Assert.*;
import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import android.view.View;

import com.jayway.android.robotium.solo.Solo;

public class DeviceTest {
	
	private Solo androidDriver = mock(Solo.class);
	private View view = mock(View.class);
	private ViewSeeker viewSeeker = mock(ViewSeeker.class);
	private Device device = new Device(androidDriver, viewSeeker);
	
	@Before
	public void setUp() {
		when(viewSeeker.waitForView(anyString())).thenReturn(view);
	}
	
	@Test
	public void clickFindsTheView() {
		device.click("hello.*world");
		verify(viewSeeker).waitForView("hello.*world");
	}
	
	@Test
	public void clickClicksTheFoundView() {
		device.click("someRegex");
		verify(androidDriver).clickOnView(view);
	}
	
	@Test
	public void clickGivesUsAPrettyErrorWhenItCannotFindTheView() {
		when(viewSeeker.waitForView(anyString())).thenReturn(null);
		
		try {
			device.click("an arbitrary regex");
			fail("wha?! click() should have thrown a JUnit assertion");
		} catch (AssertionFailedError e) {
			assertEquals("Could not find a clickable view matching 'an arbitrary regex'", e.getMessage());
		}
	}
}