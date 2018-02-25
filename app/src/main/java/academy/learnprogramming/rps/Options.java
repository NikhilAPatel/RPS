package academy.learnprogramming.rps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class Options extends AppCompatActivity {
    private Button backToMenu;
    private Button backToGame;
    private static EditText timeInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_activity);

        backToMenu = findViewById(R.id.backToMenu);
        backToGame = findViewById(R.id.backToGame);
        timeInput = findViewById(R.id.timeInput);

        View.OnClickListener backToMenuButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Options.this, Menu.class);
                startActivity(myIntent);
            }
        };

        View.OnClickListener backToGameButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Options.this, Game.class);
                startActivity(myIntent);
            }
        };

        backToGame.setOnClickListener(backToGameButtonOnClickListener);
        backToMenu.setOnClickListener(backToMenuButtonOnClickListener);


    }

    public static int getGameTime() { //returns the time the user entered. if nothing has been entered, returns a default time of 30 seconds
        try {
            return Integer.valueOf(timeInput.getText().toString());
        } catch (Exception e) {
            return 30;
        }
    }
}
