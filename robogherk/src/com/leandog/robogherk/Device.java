package com.leandog.robogherk;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import android.app.Activity;
import android.app.KeyguardManager;
import android.os.SystemClock;
import android.view.View;
import android.widget.EditText;

import com.jayway.android.robotium.solo.Solo;

public class Device {

    private static final int MAX_TRIES = 5;
    private static final int TIME_TO_WAIT = 125000;

    private Solo solo;

    public Device(Solo solo) {
        this.solo = solo;
    }

    public void clickAndWaitFor(String textToClick, Class<? extends Activity> activityToWaitFor) {
        click(textToClick);
        waitFor(activityToWaitFor);
    }

    public void click(final String textToClick) {
        tryToDo(new It() {
            public boolean shouldBeDone() {
                return solo.waitForText(textToClick, 1, TIME_TO_WAIT);
            }
        }, "failed while waiting for '" + textToClick + "'");

        solo.clickOnText(textToClick);
    }
    
    public void scrollToTop() {
        while(solo.scrollUp());
    }
    
    public void scrollToBottom() {
        while(solo.scrollDown());
    }
    
    public void goBack() {
        solo.goBack();
    }

    public boolean isOn(Class<? extends Activity> activity) {
        return solo.getCurrentActivity().getClass().getName().equals(activity.getName());
    }

    public void waitFor(final Class<? extends Activity> activityClass) {
        tryToDo(new It() {
            public boolean shouldBeDone() {
                return solo.waitForActivity(activityClass.getSimpleName(), TIME_TO_WAIT);
            }
        }, activityClass.getSimpleName() + " did not appear. \nThe activity: " + solo.getCurrentActivity()
                .getClass().getSimpleName() + " was displayed while waiting.");
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

    public void waitFor(final String text) {
        tryToDo(new It() {
            public boolean shouldBeDone() {
                return solo.waitForText(text, 1, TIME_TO_WAIT);
            }
        }, "failed to find text '" + text + "'");
    }

    public void waitForDialogToClose() {
        tryToDo(new It() {
            public boolean shouldBeDone() {
                return solo.waitForDialogToClose(TIME_TO_WAIT);
            }
        }, "timed out waiting for dialog to close");
    }

    public void typeIntoField(int id, String text) {
        final View view = solo.getView(id);
        assertNotNull("view with id " + id + " not found", view);
        assertTrue("view with id " + id + " is not an EditText (it's a " + view.getClass() + ")", EditText.class
                .isAssignableFrom(view.getClass()));
        solo.enterText((EditText) view, text);
    }

    public void assertTextIsVisible(final String... oneOrMoreTexts) {
        for (String text : oneOrMoreTexts){
            solo.searchText(text);
        }
    }

    public void viewWithIdIsVisible(int id) {
        assertNotNull("unable to find view! ", solo.getView(id));
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
        View view = solo.getView(id);
        assertNotNull("view not found!", view);
        solo.clickOnView(view);
    }

    public void rotateToLandscape() {
        solo.setActivityOrientation(Solo.LANDSCAPE);
    }

    public void updateEditTextField(int editTextId, String text) {
        solo.clearEditText((EditText) solo.getView(editTextId));
        typeIntoField(editTextId, text);
    }

    @SuppressWarnings("deprecation")
    public void unlockScreen() {
        KeyguardManager manager = (KeyguardManager) solo.getCurrentActivity().getSystemService(Activity.KEYGUARD_SERVICE);
        manager.newKeyguardLock(solo.getCurrentActivity().getClass().getName()).disableKeyguard();
    }
}
