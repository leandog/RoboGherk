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

public class ViewDetectorTest {
	
	private Solo androidDriver = mock(Solo.class);
	private ViewDetector viewFinder = new ViewDetector(androidDriver);
	private ArrayList<View> views = new ArrayList<View>();
	
	@Before
	public void setUp() {
		when(androidDriver.getCurrentViews()).thenReturn(views);
	}

	@Test
	public void finds_a_text_view_when_given_a_regex_which_matches_anything() {
		givenATextViewWithText("any arbitrary text");
		assertFindsAViewLike(".*");
	}
	
	@Test
	public void find_returns_null_when_no_views_match() {
		givenATextViewWithText("any arbitrary text");
		assertDoesNotFindAViewLike("flarp!");
	}
	
	@Test
	public void does_not_throw_if_all_we_have_is_a_view_that_is_not_a_text_view() {
		views.add(mock(ImageView.class));
		viewFinder.find(".*"); 
	}
	
	@Test
	public void ignores_HTML_tags() {
	    givenATextViewWithText("<p>The big dog is <strong>really</strong> big</p>");
		assertFindsAViewLike("The big dog is really big");
	}
	
	@Test
	public void ignores_spaces_on_the_ends() {
	    givenATextViewWithText(" Admire my leading and trailing spaces! "); 
	    assertFindsAViewLike("Admire my leading and trailing spaces!");
	}

	private void assertFindsAViewLike(String regex) {
		View view = viewFinder.find(regex);
		final String failureMessage = "Nope. Ccould not find view like "+ regex;
		assertNotNull(failureMessage, view);
	}
	
	private void assertDoesNotFindAViewLike(String regex) {
		View view = viewFinder.find(regex);
		final String failureMessage = "Uh oh! Found a view like "+ regex;
		assertNull(failureMessage, view);
	}
	
	private void givenATextViewWithText(String text) {
		TextView textView  = mock(TextView.class);
		when(textView.getText()).thenReturn(text);
		views.add(textView);
	}
}
