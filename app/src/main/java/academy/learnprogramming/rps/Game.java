package academy.learnprogramming.rps;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class Game extends AppCompatActivity {

    //TODO add sound
    //TODO finish intro screen
    //TODO fix all of the yellows
    //TODO fix encapsulation
    //TODO create a constants.java in order keep track of game length etc.
    //TODO make score 0 when game starts
    //TODO implement ads
    //TODO add progress bar
    //TODO check all text fields for bad data entry
    //TODO make it so you can tie



    private ImageButton p1paperButton;
    private ImageButton p1rockButton;
    private ImageButton p1scissorButton;
    private ImageView p2choiceImage;
    private ImageButton p2paperButton;
    private ImageButton p2rockButton;
    private ImageButton p2scissorButton;
    private ImageView p1choiceImage;
    private TextView p1timer;
    private TextView p2timer;
    private Button startButton;
    private Button quitButton;
    private Button optionsButton;

    private boolean gameStart = false;
    private String p1state = "shoot";
    private String p2state = "shoot";

    public enum card {rock, paper, scissors, shoot}

    private card p1card = card.shoot;
    private card p2card = card.shoot;
    private TextView p1score;
    private TextView p2score;
    private int p1scoreValue = 0;
    private int p2scoreValue = 0;
    private int matchTime = 500;
    private static final int scoreCheckTime = 200;
    public int runCounts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);


        // Miscellaneous
        p1timer = findViewById(R.id.p1timer);
        p2timer = findViewById(R.id.p2timer);
        startButton = findViewById(R.id.startButton);
        quitButton = findViewById(R.id.quitButton);
        optionsButton = findViewById(R.id.optionsButton);

        //Player 1 Controls
        p1paperButton = findViewById(R.id.p1paperButton);
        p1rockButton = findViewById(R.id.p1rockButton);
        p1scissorButton = findViewById(R.id.p1scissorButton);
        p1choiceImage = findViewById(R.id.p1choiceImage);
        p1score = findViewById(R.id.p1score);

        //Player 2 Controls
        p2paperButton = findViewById(R.id.p2paperButton);
        p2rockButton = findViewById(R.id.p2rockButton);
        p2scissorButton = findViewById(R.id.p2scissorButton);
        p2choiceImage = findViewById(R.id.p2choiceImage);
        p2score = findViewById(R.id.p2score);


        View.OnClickListener quitButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Game.this, Menu.class);
                startActivity(myIntent);
            }
        };

        View.OnClickListener startButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGameWithThread();
            }
        };

        View.OnClickListener rock2OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2choiceImage.setImageResource(R.drawable.rock);

                p2rockButton.setImageResource(R.drawable.rockgrayed);
                p2rockButton.setClickable(false);

                p2scissorButton.setImageResource(R.drawable.scissors);
                p2scissorButton.setClickable(true);

                p2paperButton.setImageResource(R.drawable.paper);
                p2paperButton.setClickable(true);

                p2state = "rock";

            }
        };
        View.OnClickListener paper2OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2choiceImage.setImageResource(R.drawable.paper);

                p2paperButton.setImageResource(R.drawable.papergrayed);
                p2paperButton.setClickable(false);

                p2rockButton.setImageResource(R.drawable.rock);
                p2rockButton.setClickable(true);

                p2scissorButton.setImageResource(R.drawable.scissors);
                p2scissorButton.setClickable(true);


                p2state = "paper";

            }
        };
        View.OnClickListener scissor2OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2choiceImage.setImageResource(R.drawable.scissors);

                p2scissorButton.setImageResource(R.drawable.scissorsgrayed);
                p2scissorButton.setClickable(false);

                p2paperButton.setImageResource(R.drawable.paper);
                p2paperButton.setClickable(true);

                p2rockButton.setImageResource(R.drawable.rock);
                p2rockButton.setClickable(true);

                p2state = "scissor";

            }
        };


        View.OnClickListener rock1OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1choiceImage.setImageResource(R.drawable.rock);

                p1rockButton.setImageResource(R.drawable.rockgrayed);
                p1rockButton.setClickable(false);

                p1scissorButton.setImageResource(R.drawable.scissors);
                p1scissorButton.setClickable(true);

                p1paperButton.setImageResource(R.drawable.paper);
                p1paperButton.setClickable(true);


                p1state = "rock";

            }
        };
        View.OnClickListener paper1OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1choiceImage.setImageResource(R.drawable.paper);

                p1paperButton.setImageResource(R.drawable.papergrayed);
                p1paperButton.setClickable(false);

                p1rockButton.setImageResource(R.drawable.rock);
                p1rockButton.setClickable(true);

                p1scissorButton.setImageResource(R.drawable.scissors);
                p1scissorButton.setClickable(true);


                p1state = "paper";
            }
        };
        View.OnClickListener scissor1OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1choiceImage.setImageResource(R.drawable.scissors);

                p1scissorButton.setImageResource(R.drawable.scissorsgrayed);
                p1scissorButton.setClickable(false);

                p1paperButton.setImageResource(R.drawable.paper);
                p1paperButton.setClickable(true);

                p1rockButton.setImageResource(R.drawable.rock);
                p1rockButton.setClickable(true);


                p1state = "scissor";

            }
        };

        View.OnClickListener optionsButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Game.this, Options.class);
                startActivity(myIntent);
            }
        };

        p2paperButton.setOnClickListener(paper2OnClickListener);
        p2rockButton.setOnClickListener(rock2OnClickListener);
        p2scissorButton.setOnClickListener(scissor2OnClickListener);

        p1paperButton.setOnClickListener(paper1OnClickListener);
        p1rockButton.setOnClickListener(rock1OnClickListener);
        p1scissorButton.setOnClickListener(scissor1OnClickListener);

        startButton.setOnClickListener(startButtonOnClickListener);
        quitButton.setOnClickListener(quitButtonOnClickListener);
        optionsButton.setOnClickListener(optionsButtonOnClickListener);

    }

    public void startGameWithThread() {
        if (!gameStart) {
            gameStart = true;
            p1scoreValue = 0;
            p2scoreValue = 0;
            p1score.setText(Integer.toString(p1scoreValue) + " Points");
            p2score.setText(Integer.toString(p2scoreValue) + " Points");
            p1timer.setText("Go!");
            p2timer.setText("Go!");
            unGrayButtons();
            startButton.setVisibility(INVISIBLE); //makes the start button disappear when the game starts but later this could be changed to make the button function as a pause button
            quitButton.setVisibility(INVISIBLE);
            optionsButton.setVisibility(INVISIBLE);
            Timer runTimer = new Timer();
            final int runtime = 200 + (Options.getGameTime() * 1000);

            final long startTime = System.currentTimeMillis();
            TimerTask game = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long passedTime = System.currentTimeMillis() - startTime;
                            if (passedTime > runtime) {
                                endGame();
                                cancel();
                            } else {
                                String currentWinner = getWinner();

                                if (currentWinner.equals("P1")) {
                                    p1scoreValue++;
                                    if (p1scoreValue == 1) {
                                        p1score.setText(Integer.toString(p1scoreValue) + " Point");
                                    } else {
                                        p1score.setText(Integer.toString(p1scoreValue) + " Points");
                                    }
                                    p1timer.setTextColor(0xFFFF0000);
                                    p2timer.setTextColor(0xFFFF0000);
                                } else if (currentWinner.equals("P2")) {
                                    p2scoreValue++;
                                    if (p2scoreValue == 1) {
                                        p2score.setText(Integer.toString(p2scoreValue) + " Point");
                                    } else {
                                        p2score.setText(Integer.toString(p2scoreValue) + " Points");
                                    }
                                    p1timer.setTextColor(0xFF0000FF);
                                    p2timer.setTextColor(0xFF0000FF);
                                } else {
                                    p1timer.setTextColor(0xFF000000);
                                    p2timer.setTextColor(0xFF000000);
                                }

                                p1timer.setText(String.valueOf((runtime - passedTime) / 1000));
                                p2timer.setText(String.valueOf((runtime - passedTime) / 1000));
                            }
                        }
                    });
                }
            };
            runTimer.scheduleAtFixedRate(game, 500, scoreCheckTime);
        }
    }

    public void endGame() {
        grayButtons();
        p1timer.setText("Game Done");
        p2timer.setText("Game Done");
        startButton.setVisibility(VISIBLE);
        quitButton.setVisibility(VISIBLE);
        optionsButton.setVisibility(VISIBLE);
        p1choiceImage.setImageResource(R.drawable.shoot);
        p2choiceImage.setImageResource(R.drawable.shoot);

        gameStart = false;
        if (p1scoreValue > p2scoreValue) {
            p1score.setText("Winner: " + p1scoreValue);
            p2score.setText("Loser: " + p2scoreValue);
        } else if (p1scoreValue < p2scoreValue) {
            p2score.setText("Winner: " + p2scoreValue);
            p1score.setText("Loser: " + p1scoreValue);
        } else {
            p2score.setText("Loser: " + p2scoreValue);
            p1score.setText("Loser: " + p1scoreValue);
        }

    }

    public String getWinner() {
        String condition;
        if (p1state.equals(p2state)) {
            condition = "TIED";
        } else if ((p1state.equals("rock") && p2state.equals("paper")) || (p1state.equals("paper") && p2state.equals("scissor")) || (p1state.equals("scissor") && p2state.equals("rock")) || p1state.equals("shoot")) {
            condition = "P2";
        } else {
            condition = "P1";
        }
        return condition;
    }

    public void grayButtons(){
        p1rockButton.setClickable(false);
        p1paperButton.setClickable(false);
        p1scissorButton.setClickable(false);

        p2rockButton.setClickable(false);
        p2paperButton.setClickable(false);
        p2scissorButton.setClickable(false);

        p1rockButton.setImageResource(R.drawable.rockgrayed);
        p1scissorButton.setImageResource(R.drawable.scissorsgrayed);
        p1paperButton.setImageResource(R.drawable.papergrayed);

        p2rockButton.setImageResource(R.drawable.rockgrayed);
        p2scissorButton.setImageResource(R.drawable.scissorsgrayed);
        p2paperButton.setImageResource(R.drawable.papergrayed);
    }

    public void unGrayButtons(){
        p1rockButton.setClickable(true);
        p1paperButton.setClickable(true);
        p1scissorButton.setClickable(true);

        p2rockButton.setClickable(true);
        p2paperButton.setClickable(true);
        p2scissorButton.setClickable(true);

        p1rockButton.setImageResource(R.drawable.rock);
        p1scissorButton.setImageResource(R.drawable.scissors);
        p1paperButton.setImageResource(R.drawable.paper);

        p2rockButton.setImageResource(R.drawable.rock);
        p2scissorButton.setImageResource(R.drawable.scissors);
        p2paperButton.setImageResource(R.drawable.paper);
    }

}