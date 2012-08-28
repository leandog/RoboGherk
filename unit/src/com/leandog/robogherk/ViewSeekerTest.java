package com.leandog.robogherk;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;

import android.view.View;

public class ViewSeekerTest {

	private ViewDetector viewDetector = mock(ViewDetector.class);
    private View view = mock(View.class);
	private ViewSeeker viewSeeker = new ViewSeeker(viewDetector);
	
    @Test
	public void waitForViewWillPollForWhatWeWantUntilWeFindIt() {
		when(viewDetector.find(anyString())).thenReturn(null).thenReturn(null).thenReturn(view);
		assertNotNull(viewSeeker.waitForView("huzzah"));
	}
}