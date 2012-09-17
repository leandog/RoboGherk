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
		when(viewSeeker.seek(anyString())).thenReturn(view);
	}
	
	@Test
	public void click_finds_the_view() {
		device.click("hello.*world");
		verify(viewSeeker).seek("hello.*world");
	}
	
	@Test
	public void click_clicks_the_found_view() {
		device.click("someRegex");
		verify(androidDriver).clickOnView(view);
	}
	
	@Test
	public void click_gives_us_a_pretty_error_when_it_cannot_find_the_view() {
		when(viewSeeker.seek(anyString())).thenReturn(null);
		
		try {
			device.click("an arbitrary regex");
			fail("wha?! click() should have thrown a JUnit assertion");
		} catch (AssertionFailedError e) {
			assertEquals("Could not find a clickable view matching 'an arbitrary regex'", e.getMessage());
		}
	}
	
	@Test
	public void it_can_rotate_device_to_landscape() {
	    device.rotateToLandscape();
	    verify(androidDriver).setActivityOrientation(Solo.LANDSCAPE);
	}
	
	@Test
	public void it_can_rotate_device_to_portrait() {
	    device.rotateToPortrait();
	    verify(androidDriver).setActivityOrientation(Solo.PORTRAIT);
	}
}
