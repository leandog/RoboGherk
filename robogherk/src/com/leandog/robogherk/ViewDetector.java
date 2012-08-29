package com.leandog.robogherk;

import com.jayway.android.robotium.solo.Solo;

import android.view.View;
import android.widget.TextView;

public class ViewDetector {

	private final Solo androidDriver;

	public ViewDetector(Solo androidDriver) {
		this.androidDriver = androidDriver;
	}

	public View find(String regex) {
		for (View view : androidDriver.getCurrentViews()) {
			if (viewHasTextMatchingPattern(regex, view))
				return view;
		}
		return null;
	}

	private boolean viewHasTextMatchingPattern(String regex, View view) {
		if (view instanceof TextView) {
			String viewText = ((TextView) view).getText().toString();
    	    return stripHtmlFrom(viewText).matches(regex);
		}
		return false;
	}

    private String stripHtmlFrom(String viewText) {
        return viewText.replaceAll("^<.*?>", "").replaceAll("<.*?>$" , "");
    }
}