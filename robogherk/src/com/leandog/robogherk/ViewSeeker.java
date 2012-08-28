package com.leandog.robogherk;

import android.view.View;

public class ViewSeeker {

    private final ViewDetector viewDetector;
    private final Sleeper sleeper;
    
    public ViewSeeker(ViewDetector viewDetector, Sleeper sleeper) {
        this.viewDetector = viewDetector;
        this.sleeper = sleeper;
    }
    
    View seek(final String regex) {
        View view = null;
        for (int i = 0; i < 1000; i++) {
            view = viewDetector.find(regex);
            if (view != null)
                break;
            sleeper.sleep(750);
        }
        return view;
    }

}
