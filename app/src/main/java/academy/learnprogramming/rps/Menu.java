package academy.learnprogramming.rps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

public class Menu extends AppCompatActivity {
    Button btnLaunchSp, btnLaunchMpWifi, btnLaunchMpBluetooth;
    ImageButton btnMute, btnOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Run app in fullscreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Load main menu view
        setContentView(R.layout.act_main_menu);


        //Instanitate buttons
        btnLaunchSp = findViewById(R.id.menuLocalSingleplayer);
        btnLaunchMpWifi = findViewById(R.id.menuWifiMultiplayer);
        btnLaunchMpBluetooth = findViewById(R.id.menuBTMultiplayer);

        btnMute = findViewById(R.id.menuMute);
        btnOptions = findViewById(R.id.menuOptions);


        //Set button listeners
        btnLaunchSp.setOnClickListener((View v) ->
                startActivity(new Intent(getApplicationContext(), Game.class))
        );

        btnMute.setOnClickListener((View v) -> {
                if(GameState.getInstance().isMuted()) {
                    GameState.getInstance().setMuted(false);
                    btnMute.setImageResource(R.drawable.ic_mute_off);
                } else {
                    GameState.getInstance().setMuted(true);
                    btnMute.setImageResource(R.drawable.ic_mute_on);
                }
        });
        btnOptions.setOnClickListener((View v) ->
                startActivity(new Intent(getApplicationContext(), Options.class))
        );
    }

}
