package com.leandog.robogherk;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

public class ViewLocatorTest {
	
	private Solo androidDriver = mock(Solo.class);
	private ViewLocator viewLocator = new ViewLocator(androidDriver);
	private ArrayList<View> views = new ArrayList<View>();
	
	@Before
	public void setUp() {
		when(androidDriver.getCurrentViews()).thenReturn(views);
	}

	@Test
	public void findsATextViewWhenGivenARegexWhichMatchesAnything() {
		givenATextViewWithText("any arbitrary text");
		assertFindsAViewLike(".*");
	}
	
	@Test
	public void findReturnsNullWhenNoViewsMatch() {
		givenATextViewWithText("any arbitrary text");
		assertDoesNotFindAViewLike("flarp!");
	}
	
	@Test
	public void doesNotThrowIfAllWeHaveIsAViewThatIsNotATextView() {
		views.add(mock(ImageView.class));
		viewLocator.find(".*"); 
	}

	private void givenATextViewWithText(String text) {
		TextView textView  = mock(TextView.class);
		when(textView.getText()).thenReturn(text);
		views.add(textView);
	}

	private void assertFindsAViewLike(String regex) {
		View view = viewLocator.find(regex);
		final String failureMessage = "Nope. Ccould not find view like "+ regex;
		assertNotNull(failureMessage, view);
	}
	
	private void assertDoesNotFindAViewLike(String regex) {
		View view = viewLocator.find(regex);
		final String failureMessage = "Uh oh! Found a view like "+ regex;
		assertNull(failureMessage, view);
	}
}


