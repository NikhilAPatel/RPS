package com.kotassium.rps;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;



public class Menu extends AppCompatActivity {
    GameState gameState = GameState.getInstance();

    Button btnLaunchLMp, btnLaunchSp;
    ImageButton btnMute, btnOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Run app in fullscreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Load main menu view
        setContentView(R.layout.act_main_menu);


        //Instantiate buttons
        btnLaunchLMp = findViewById(R.id.menuLocalSingleplayer);
        btnLaunchSp = findViewById(R.id.menuSingleplayer);

        btnMute = findViewById(R.id.menuMute);
        btnOptions = findViewById(R.id.menuOptions);


        //Set button listeners
        btnLaunchLMp.setOnClickListener((View v) -> startActivity(new Intent(getApplicationContext(), TwoPlayer.class)));
        btnLaunchSp.setOnClickListener((View v) -> startActivity(new Intent(getApplicationContext(), SinglePlayer.class)));

        btnMute.setOnClickListener((View v) -> {
            if (gameState.isMuted()) {
                gameState.setMuted(false);
                btnMute.setImageResource(R.drawable.ic_mute_off);
            } else {
                gameState.setMuted(true);
                btnMute.setImageResource(R.drawable.ic_mute_on);
            }
        });
        btnOptions.setOnClickListener((View v) ->
                startActivity(new Intent(getApplicationContext(), Options.class))
        );
    }

}
