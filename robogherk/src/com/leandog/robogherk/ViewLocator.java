package com.leandog.robogherk;

import com.jayway.android.robotium.solo.Solo;

import android.view.View;
import android.widget.TextView;

public class ViewLocator {

	private final Solo androidDriver;

	public ViewLocator(Solo androidDriver) {
		this.androidDriver = androidDriver;
	}

	public View find(String regex) {
		for (View view : androidDriver.getCurrentViews()) {
			if (viewMatchesPattern(regex, view))
				return view;
		}
		return null;
	}

	private boolean viewMatchesPattern(String regex, View view) {
		if (view instanceof TextView) {
			String viewText = ((TextView) view).getText().toString();
			return viewText.matches(regex);
		}
		return false;
	}
}