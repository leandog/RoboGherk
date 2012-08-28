package com.leandog.robogherk;

import android.view.View;

public class Waiter {

    private final ViewFinder viewFinder;
    
    public Waiter(ViewFinder viewFinder) {
        this.viewFinder = viewFinder; 
    }
    
    View waitForView(final String regex) {
        View view = null;
        for (int i = 0; i < 1000; i++) {
            view = viewFinder.find(regex);
            if (view != null)
                break;
        }
        return view;
    }

}
