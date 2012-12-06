package com.leandog.robogherk.example_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class FirstActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_robo_gherk);
        
        setupSpinner();
       
        Button button = (Button) findViewById(R.id.button);
        
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, SecondActivity.class));
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_hello_robo_gherk, menu);
        return true;
    }
    
    private void setupSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        String[] numbers = getResources().getStringArray(R.array.numbers);
        ArrayAdapter<String> spinnerAdapter = getSpinnerAdapter(numbers);
        spinner.setAdapter(spinnerAdapter);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    private ArrayAdapter<String> getSpinnerAdapter(String[] numbers) {
        return new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, numbers);
    }

}
