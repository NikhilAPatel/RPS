package com.kotassium.rps;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;

import static com.kotassium.rps.Difficulty.EASY;
import static com.kotassium.rps.Difficulty.HARD;
import static com.kotassium.rps.Difficulty.MEDIUM;

//TODO store settings even when app closes

public class Options extends AppCompatActivity {

    private static Difficulty difficulty = MEDIUM;
    GameState gameState = GameState.getInstance();
    @SuppressLint("StaticFieldLeak")
    private EditText timeInput;
    private RadioButton radioEasy;
    private RadioButton radioMedium;
    private RadioButton radioHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_options);

        timeInput = findViewById(R.id.timeInput);

        radioEasy = findViewById(R.id.radio_easy);
        radioMedium = findViewById(R.id.radio_medium);
        radioHard = findViewById(R.id.radio_hard);

        radioEasy.setOnClickListener(v -> difficulty = EASY);
        radioMedium.setOnClickListener(v -> difficulty = MEDIUM);
        radioHard.setOnClickListener(v -> difficulty = HARD);

        setupOptions();
    }

    @Override
    public void onBackPressed() {
        gameState.setMatchLength(Integer.valueOf(timeInput.getText().toString()) * 1000);
        gameState.setDifficulty(difficulty);
        super.onBackPressed();
    }

    private void setupOptions() {
        timeInput.setText(String.valueOf(gameState.getMatchLength() / 1000));
        if (gameState.getDifficulty() == 1000) {
            radioHard.setChecked(true);
        } else if (gameState.getDifficulty() == 2000) {
            radioMedium.setChecked(true);
        } else if (gameState.getDifficulty() == 3000) {
            radioEasy.setChecked(true);
        }
    }
}
