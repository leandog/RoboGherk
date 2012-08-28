package com.leandog.robogherk;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import android.app.Activity;
import android.app.KeyguardManager;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

public class Device {

    private static final int MAX_TRIES = 5;
    private static final int TIME_TO_WAIT = 125000;

    private final Solo androidDriver;
    private final ViewFinder viewFinder;
    private final Waiter waiter;
    
    public Device(Solo androidDriver) {
    	this(androidDriver, new ViewFinder(androidDriver));
    }

    public Device(Solo androidDriver, ViewFinder viewLocator) {
        this.androidDriver = androidDriver;
        this.viewFinder = viewLocator;
        this.waiter = new Waiter();
    }

    public void clickAndWaitFor(String textToClick, Class<? extends Activity> activityToWaitFor) {
        click(textToClick);
        waitFor(activityToWaitFor);
    }

    public void click(final String regex) {
        View view = waitForView(regex);
        assertNotNull("Could not find a clickable view matching '" + regex + "'", view);
		androidDriver.clickOnView(view);
    }

    public void scrollToTop() {
        while(androidDriver.scrollUp());
    }
    
    public void scrollToBottom() {
        while(androidDriver.scrollDown());
    }
    
    public void goBack() {
        androidDriver.goBack();
    }

    public boolean isOn(Class<? extends Activity> activity) {
        return androidDriver.getCurrentActivity().getClass().getName().equals(activity.getName());
    }

    public void waitFor(final Class<? extends Activity> activityClass) {
        tryToDo(new It() {
            public boolean shouldBeDone() {
                return androidDriver.waitForActivity(activityClass.getSimpleName(), TIME_TO_WAIT);
            }
        }, activityClass.getSimpleName() + " did not appear. \nThe activity: " + androidDriver.getCurrentActivity()
                .getClass().getSimpleName() + " was displayed while waiting.");
    }
    
    public void waitFor(final String text) {
        final String failureMessage = "failed to find text '" + text + "'";
		assertTrue(failureMessage, androidDriver.waitForText(text, 1, TIME_TO_WAIT));
    }

    public void waitUntilNotOn(Class<? extends Activity> activity) {
        long endTime = SystemClock.currentThreadTimeMillis() + 5000;
        while (endTime > SystemClock.currentThreadTimeMillis()) {
            if (!isOn(activity))
                return;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        fail("timed out waiting to leave " + activity.getSimpleName());
    }
    
    public void waitForDialogToClose() {
        tryToDo(new It() {
            public boolean shouldBeDone() {
                return androidDriver.waitForDialogToClose(TIME_TO_WAIT);
            }
        }, "timed out waiting for dialog to close");
    }

    public void typeIntoField(int id, String text) {
        androidDriver.clearEditText((EditText) androidDriver.getView(id));
        final View view = androidDriver.getView(id);
        assertNotNull("view with id " + id + " not found", view);
        assertTrue("view with id " + id + " is not an EditText (it's a " + view.getClass() + ")", EditText.class
                .isAssignableFrom(view.getClass()));
        androidDriver.enterText((EditText) view, text);
    }

    public void assertTextIsVisible(final String... oneOrMoreTexts) {
        for (String text : oneOrMoreTexts){
            androidDriver.searchText(text);
        }
    }

    public void viewWithIdIsVisible(int id) {
        assertNotNull("unable to find view! ", androidDriver.getView(id));
    }

    public void tryToDo(It it, String failureMessage) {
        boolean isDone = false;
        for (int count = 0; !isDone && count < MAX_TRIES; count++) {
            isDone = it.shouldBeDone();
        }
        assertTrue(failureMessage, isDone);
    }

    interface It {
        boolean shouldBeDone();
    }

    public void clickOnViewWithId(int id) {
        View view = androidDriver.getView(id);
        assertNotNull("view not found!", view);
        androidDriver.clickOnView(view);
    }

    public void rotateToLandscape() {
        androidDriver.setActivityOrientation(Solo.LANDSCAPE);
    }

    public void updateEditTextField(int editTextId, String text) {
        typeIntoField(editTextId, text);
    }
    
    public String getTextFromTextView(int textViewId) {
       View view = androidDriver.getView(textViewId);
       assertTrue("The view requested is not a TextView!",view instanceof TextView);
       return ((TextView)view).getText().toString(); 
    }

    @SuppressWarnings("deprecation")
    public void unlockScreen() {
        KeyguardManager manager = (KeyguardManager) androidDriver.getCurrentActivity().getSystemService(Activity.KEYGUARD_SERVICE);
        manager.newKeyguardLock(androidDriver.getCurrentActivity().getClass().getName()).disableKeyguard();
    }
    
    private View waitForView(final String regex) {
        View view = null;
        for (int i = 0; i < 1000; i++) {
            view = viewFinder.find(regex);
            if (view != null)
                break;
        }
        return view;
    }
}