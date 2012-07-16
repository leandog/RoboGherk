package com.leandog.examples.robogherk;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class HelloRoboGherk extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_robo_gherk);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_hello_robo_gherk, menu);
        return true;
    }

    
}
