package com.leandog.robogherk;

import android.view.View;

public class ViewSeeker {

    private final ViewDetector viewDetector;
    
    public ViewSeeker(ViewDetector viewDetector) {
        this.viewDetector = viewDetector; 
    }
    
    View seek(final String regex) {
        View view = null;
        for (int i = 0; i < 1000; i++) {
            view = viewDetector.find(regex);
            if (view != null)
                break;
        }
        return view;
    }

}
