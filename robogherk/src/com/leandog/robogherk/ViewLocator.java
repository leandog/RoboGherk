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
			String viewText = ((TextView) view).getText().toString();
			if (regex.matcher(viewText).matches())
				return view;
		}
		return null;
	}

}
