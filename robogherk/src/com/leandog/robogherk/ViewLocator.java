package com.leandog.robogherk;

import java.util.regex.Pattern;

import com.jayway.android.robotium.solo.Solo;

import android.view.View;

public class ViewLocator {

	private final Solo androidDriver;

	public ViewLocator(Solo androidDriver) {
		this.androidDriver = androidDriver;
	}

	public View find(Pattern compile) {
		return androidDriver.getCurrentViews().get(0);
	}

}
