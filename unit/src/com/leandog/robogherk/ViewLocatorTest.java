package com.leandog.robogherk;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.junit.Test;
import static org.mockito.Mockito.*;

import com.jayway.android.robotium.solo.Solo;

import static org.junit.Assert.*;

import android.view.View;
import android.widget.TextView;

public class ViewLocatorTest {
	
	private Solo androidDriver = mock(Solo.class);

	@Test
	public void findsATextViewWhenGivenARegexWhichMatchesAnything() {
		ArrayList<View> mockViews = new ArrayList<View>();
		TextView textView  = mock(TextView.class);
		when(textView.getText()).thenReturn("howdy, partner!");
		mockViews.add(textView);
		when(androidDriver.getCurrentViews()).thenReturn(mockViews);
		
		ViewLocator viewLocator = new ViewLocator(androidDriver);
		String regex = ".*";
		View view = viewLocator.find(Pattern.compile(regex));
		final String failureMessage = "could not find view like "+ regex;
		assertNotNull(failureMessage, view);
	}
	
}


