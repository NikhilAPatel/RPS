package com.kotassium.rps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

public class Options extends AppCompatActivity {

    private static EditText timeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_options);

        timeInput = findViewById(R.id.timeInput);
    }

    public static int getGameTime() { //returns the time the user entered. if nothing has been entered, returns a default time of 30 seconds
        try {
            return Integer.valueOf(timeInput.getText().toString());
        } catch (Exception e) {
            return 30;
        }
    }
}
