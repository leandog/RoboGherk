package com.leandog.robogherk;

import java.util.regex.Pattern;

import com.jayway.android.robotium.solo.Solo;

import android.view.View;
import android.widget.TextView;

public class ViewLocator {

	private final Solo androidDriver;

	public ViewLocator(Solo androidDriver) {
		this.androidDriver = androidDriver;
	}

	public View find(Pattern regex) {
		for (View view : androidDriver.getCurrentViews()) {
			if (viewMatchesPattern(regex, view))
				return view;
		}
		return null;
	}

	private boolean viewMatchesPattern(Pattern regex, View view) {
		if (view instanceof TextView) {
			String viewText = ((TextView) view).getText().toString();
			return regex.matcher(viewText).matches();
		}
		return false;
	}
}