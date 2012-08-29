package com.leandog.robogherk;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Test;

import android.view.View;

public class ViewSeekerTest {

    private ViewDetector viewDetector = mock(ViewDetector.class);
    private View view = mock(View.class);
    private Sleeper sleeper = mock(Sleeper.class);
    private ViewSeeker viewSeeker = new ViewSeeker(viewDetector, sleeper);
	
    @Test
    public void seek_tries_more_than_once() {
        when(viewDetector.find(anyString())).thenReturn(null).thenReturn(null).thenReturn(view);
        assertNotNull(viewSeeker.seek("huzzah"));
    }
    
    @Test
    public void seek_waits_between_tries() {
        when(viewDetector.find(anyString())).thenReturn(null).thenReturn(view);
        viewSeeker.seek("huzzah");
        verify(sleeper).sleep(anyLong());
    }
}
