package com.kotassium.rps;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;

public class Options extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static EditText timeInput;
    GameState gameState = GameState.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_options);

        timeInput = findViewById(R.id.timeInput);
        timeInput.setText(String.valueOf(gameState.getMatchLength() / 1000));
    }

    @Override
    public void onBackPressed() {
        gameState.setMatchLength(Integer.valueOf(timeInput.getText().toString()) * 1000);
        super.onBackPressed();
    }
}
