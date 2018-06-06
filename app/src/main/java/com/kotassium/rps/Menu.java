package com.kotassium.rps;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;


public class Menu extends AppCompatActivity {
    GameState gameState = GameState.getInstance();

    Button btnLaunchLMp, btnLaunchSp;
    ImageButton btnMute, btnOptions;
    private MediaPlayer music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Run app in fullscreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Load main menu view
        setContentView(R.layout.act_main_menu);


        //Set Up A Banner Ad
        MobileAds.initialize(this, "\"ca-app-pub-9622049821532312/8192551140");
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-9622049821532312/8192551140");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        //Instantiate buttons
        btnLaunchLMp = findViewById(R.id.menuLocalSingleplayer);
        btnLaunchSp = findViewById(R.id.menuSingleplayer);

        btnMute = findViewById(R.id.menuMute);
        btnOptions = findViewById(R.id.menuOptions);


        //Set button listeners
        btnLaunchLMp.setOnClickListener((View v) -> startActivity(new Intent(getApplicationContext(), TwoPlayer.class)));
        btnLaunchSp.setOnClickListener((View v) -> startActivity(new Intent(getApplicationContext(), SinglePlayer.class)));

//        btnMute.setOnClickListener((View v) -> {
//            if (gameState.isMuted()) {
//                gameState.setMuted(false);
//                btnMute.setImageResource(R.drawable.ic_mute_off);
//                if (!music.isPlaying()) {
//                    int length = music.getCurrentPosition();
//                    music.seekTo(length);
//                    music.start();
//                }
//            } else {
//                gameState.setMuted(true);
//                btnMute.setImageResource(R.drawable.ic_mute_on);
//                if (music.isPlaying()) {
//                    music.pause();
//                }
//            }
//        });
        btnOptions.setOnClickListener((View v) ->
                startActivity(new Intent(getApplicationContext(), Options.class))
        );

//        startMusic();

    }

//    void startMusic(){
//        music = MediaPlayer.create(this, R.raw.menu_music);
//        music.setLooping(true);
//        music.start();
//    }


}
