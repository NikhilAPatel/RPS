package academy.learnprogramming.rps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    Button launchButton;
    Button optionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_activity);


        launchButton = (Button) findViewById(R.id.launchButton);
        optionsButton = (Button) findViewById(R.id.optionsButton);

        View.OnClickListener launchButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu.this, Game.class);
                startActivity(myIntent);
            }
        };

        View.OnClickListener optionsButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Menu.this, Options.class);
                startActivity(myIntent);
            }
        };

        launchButton.setOnClickListener(launchButtonOnClickListener);
        optionsButton.setOnClickListener(optionsButtonOnClickListener);
    }

}
