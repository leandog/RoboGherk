package com.leandog.robogherk;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import android.app.Activity;
import android.os.SystemClock;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

public class Device {

    private static final int TIME_TO_WAIT = 125000;

    private final Solo androidDriver;
    private final ViewSeeker viewSeeker;

    public Device(Solo androidDriver) {
        this(androidDriver, new ViewSeeker(new ViewDetector(androidDriver), new Sleeper()));
    }

    public Device(Solo androidDriver, ViewSeeker viewSeeker) {
        this.androidDriver = androidDriver;
        this.viewSeeker = viewSeeker;
    }

    public void clickAndWaitFor(String regex, Class<? extends Activity> activityToWaitFor) {
        click(regex);
        waitFor(activityToWaitFor);
    }

    public void click(final String regex) {
        View view = viewSeeker.seek(regex);
        assertNotNull("Could not find a clickable view matching '" + regex + "'", view);
        androidDriver.clickOnView(view);
    }

    public void scrollToTop() {
        while (androidDriver.scrollUp())
            ;
    }

    public void scrollToBottom() {
        while (androidDriver.scrollDown())
            ;
    }

    public void goBack() {
        androidDriver.goBack();
    }

    public boolean isOn(Class<? extends Activity> activity) {
        return androidDriver.getCurrentActivity().getClass().getName().equals(activity.getName());
    }

    public void waitFor(final Class<? extends Activity> activityClass) {
        String failureMessage =
                activityClass.getSimpleName() + " did not appear. \nThe activity: "
                        + androidDriver.getCurrentActivity().getClass().getSimpleName()
                        + " was displayed while waiting.";
        assertTrue(failureMessage, androidDriver.waitForActivity(activityClass.getSimpleName(), TIME_TO_WAIT));
    }

    public void waitForDialogToClose() {
        String failureMessage = "timed out waiting for dialog to close";
        assertTrue(failureMessage, androidDriver.waitForDialogToClose(TIME_TO_WAIT));
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

    public void typeIntoField(int id, String text) {
        androidDriver.clearEditText((EditText) androidDriver.getView(id));
        final View view = androidDriver.getView(id);
        assertNotNull("view with id " + id + " not found", view);
        assertTrue("view with id " + id + " is not an EditText (it's a " + view.getClass() + ")", EditText.class
                .isAssignableFrom(view.getClass()));
        androidDriver.enterText((EditText) view, text);
    }

    public void assertTextIsVisible(final String... oneOrMoreTexts) {
        for (String text : oneOrMoreTexts) {
            androidDriver.searchText(text);
        }
    }

    public void viewWithIdIsVisible(int id) {
        assertNotNull("unable to find view! ", androidDriver.getView(id));
    }

    public void clickOnViewWithId(int id) {
        View view = androidDriver.getView(id);
        assertNotNull("view not found!", view);
        androidDriver.clickOnView(view);
    }

    public void rotateToLandscape() {
        androidDriver.setActivityOrientation(Solo.LANDSCAPE);
    }

    public void rotateToPortrait() {
        androidDriver.setActivityOrientation(Solo.PORTRAIT);
    }

    public String getTextFromTextView(int textViewId) {
        View view = androidDriver.getView(textViewId);
        assertTrue("The view requested is not a TextView!", view instanceof TextView);
        return ((TextView) view).getText().toString();
    }

    public void selectValueOnSpinner(final int resourceId, final String value) {

        androidDriver.getCurrentActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Spinner spinner = (Spinner) androidDriver.getView(resourceId);
                Adapter adapter = spinner.getAdapter();
                int position = -1;
                for (int i = 0; i < adapter.getCount(); i++) {
                    if (((String) adapter.getItem(i)).equals(value)) {
                        position = i;
                        break;
                    }
                }

                if (position >= 0) {
                    spinner.setSelection(position, true);
                } else {
                    assertTrue("Could not find" + value + " in provided spinner resource", false);
                }

            }
        });
    }
    
    public String selectedValueOnSpinner(int resourceId) {
        return (String) ((Spinner) androidDriver.getView(resourceId)).getSelectedItem();
    }

    public void unlockScreen() {
        androidDriver.getCurrentActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                androidDriver.getCurrentActivity().getWindow().addFlags(
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
            }
        });
    }
}