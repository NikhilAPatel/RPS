package academy.learnprogramming.rps;

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

public class MainActivity extends AppCompatActivity {

    //TODO add sound
    //TODO add intro screen
    //TODO add actual way to start game
    //TODO add way for users to know who is winning
    //TODO find a way to get rid of title bar
    //TODO fix all of the yellows
    //TODO fix encapsulation
    //TODO create a constants.java in order keep track of game length etc.
    //TODO make score default 0
    //TODO make score 0 when game starts
    //TODO make score easier to read
    //TODO implement ads
    //TODO gray out buttons on end game


    private ImageButton p1paperButton;
    private ImageButton p1rockButton;
    private ImageButton p1scissorButton;
    private ImageView p2choiceImage;
    private ImageButton p2paperButton;
    private ImageButton p2rockButton;
    private ImageButton p2scissorButton;
    private ImageView p1choiceImage;
    private TextView timer;
    private Button startButton;
    private EditText timeInput;

    private boolean gameStart = false;
    private String p1state = "shoot";
    private String p2state = "shoot";
    public enum card {rock, paper, scissors, shoot};
    private card p1card=card.shoot;
    private card p2card=card.shoot;
    private TextView p1score;
    private TextView p2score;
    private int p1scoreValue=0;
    private int p2scoreValue=0;
    private int matchTime=500;
    private static final int scoreCheckTime=200;
    public int runCounts=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);


        // Miscellaneous
        timer = findViewById(R.id.timer);
        startButton = findViewById(R.id.startButton);
        timeInput=findViewById(R.id.timeInput);

        //Player 1 Controls
        p1paperButton =findViewById(R.id.p1paperButton);
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

        View.OnClickListener startButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startGamewithThread();
            }
        };

        View.OnClickListener rock2OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2choiceImage.setImageResource(R.drawable.rock);
                /*
                p2rockButton.setImageResource(R.drawable.rockgrayed);
                p2rockButton.setClickable(false);

                p2scissorButton.setImageResource(R.drawable.scissors);
                p2scissorButton.setClickable(true);

                p2paperButton.setImageResource(R.drawable.paper);
                p2paperButton.setClickable(true);
*/
                p2state = "rock";

            }
        };
        View.OnClickListener paper2OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2choiceImage.setImageResource(R.drawable.paper);
                /*
                p2paperButton.setImageResource(R.drawable.papergrayed);
                p2paperButton.setClickable(false);

                p2rockButton.setImageResource(R.drawable.rock);
                p2rockButton.setClickable(true);

                p2scissorButton.setImageResource(R.drawable.scissors);
                p2scissorButton.setClickable(true);
                */

                p2state = "paper";

            }
        };
        View.OnClickListener scissor2OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2choiceImage.setImageResource(R.drawable.scissors);
                /*
                p2scissorButton.setImageResource(R.drawable.scissorsgrayed);
                p2scissorButton.setClickable(false);

                p2paperButton.setImageResource(R.drawable.paper);
                p2paperButton.setClickable(true);

                p2rockButton.setImageResource(R.drawable.rock);
                p2rockButton.setClickable(true);
*/
                p2state = "scissor";

            }
        };


        View.OnClickListener rock1OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1choiceImage.setImageResource(R.drawable.rock);
                /*
                p1rockButton.setImageResource(R.drawable.rockgrayed);
                p1rockButton.setClickable(false);

                p1scissorButton.setImageResource(R.drawable.scissors);
                p1scissorButton.setClickable(true);

                p1paperButton.setImageResource(R.drawable.paper);
                p1paperButton.setClickable(true);
                */

                p1state = "rock";

            }
        };
        View.OnClickListener paper1OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1choiceImage.setImageResource(R.drawable.paper);
                /*
                p1paperButton.setImageResource(R.drawable.papergrayed);
                p1paperButton.setClickable(false);

                p1rockButton.setImageResource(R.drawable.rock);
                p1rockButton.setClickable(true);

                p1scissorButton.setImageResource(R.drawable.scissors);
                p1scissorButton.setClickable(true);
                */

                p1state = "paper";
            }
        };
        View.OnClickListener scissor1OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1choiceImage.setImageResource(R.drawable.scissors);
                /*
                p1scissorButton.setImageResource(R.drawable.scissorsgrayed);
                p1scissorButton.setClickable(false);

                p1paperButton.setImageResource(R.drawable.paper);
                p1paperButton.setClickable(true);

                p1rockButton.setImageResource(R.drawable.rock);
                p1rockButton.setClickable(true);
                */

                p1state = "scissor";

            }
        };

        p2paperButton.setOnClickListener(paper2OnClickListener);
        p2rockButton.setOnClickListener(rock2OnClickListener);
        p2scissorButton.setOnClickListener(scissor2OnClickListener);

        p1paperButton.setOnClickListener(paper1OnClickListener);
        p1rockButton.setOnClickListener(rock1OnClickListener);
        p1scissorButton.setOnClickListener(scissor1OnClickListener);
        startButton.setOnClickListener(startButtonOnClickListener);

    }

    public void startGame() {
        if (!gameStart) {
            gameStart = true;
            p1scoreValue = 0;
            p2scoreValue = 0;
            p1score.setText(Integer.toString(p1scoreValue));
            p2score.setText(Integer.toString(p2scoreValue));

            timer.setText("Go!");
            int runtime = Integer.parseInt(timeInput.getText().toString()) * 1000;
            new CountDownTimer(runtime, scoreCheckTime) {

                public void onTick(long millisUntilFinished) {
                    String currentWinner = getWinner();

                    if (currentWinner.equals("P1")) {
                        p1scoreValue++;
                        p1score.setText(Integer.toString(p1scoreValue));
                        timer.setTextColor(0xFFFF0000);
                    }
                    else if (currentWinner.equals("P2")) {
                        p2scoreValue++;
                        p2score.setText(Integer.toString(p2scoreValue));
                        timer.setTextColor(0xFF0000FF);
                    }
                    else
                    {
                        timer.setTextColor(0xFF000000);
                    }

                    timer.setText(String.valueOf(millisUntilFinished / 1000));
                }

                public void onFinish() {

                    endGame();
                }
            }.start();
        }
    }
    public void startGamewithThread()
    {
        if (!gameStart) {
            gameStart = true;
            p1scoreValue = 0;
            p2scoreValue = 0;
            p1score.setText(Integer.toString(p1scoreValue));
            p2score.setText(Integer.toString(p2scoreValue));
            timer.setText("Go!");
            Timer runTimer = new Timer();
            final int runtime = 200+Integer.parseInt(timeInput.getText().toString()) * 1000;

            final long startTime = System.currentTimeMillis();
            TimerTask game=new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            long passedTime=System.currentTimeMillis() - startTime;
                            if (passedTime> runtime) {
                                endGame();
                                cancel();
                            } else {
                                String currentWinner = getWinner();

                                if (currentWinner.equals("P1")) {
                                    p1scoreValue++;
                                    p1score.setText(Integer.toString(p1scoreValue));
                                    timer.setTextColor(0xFFFF0000);
                                }
                                else if (currentWinner.equals("P2")) {
                                    p2scoreValue++;
                                    p2score.setText(Integer.toString(p2scoreValue));
                                    timer.setTextColor(0xFF0000FF);
                                }
                                else
                                {
                                    timer.setTextColor(0xFF000000);
                                }

                                timer.setText(String.valueOf((runtime-passedTime)/1000));
                            }
                        }
                    });
                }
            };
            runTimer.scheduleAtFixedRate(game, 500, scoreCheckTime);
        }
    }

    //TODO finish endGame method (find out who wins and have options for quit to menu and play again)
    public void endGame() {
        timer.setText("Game Done");
        gameStart=false;
        if (p1scoreValue>p2scoreValue)
        {
            p1score.setText("Winner: "+p1scoreValue);
            p2score.setText("Loser: "+p2scoreValue);
        }
        else if (p1scoreValue<p2scoreValue)
        {
            p2score.setText("Winner: "+p2scoreValue);
            p1score.setText("Loser: "+p1scoreValue);
        }
        else
        {
            p2score.setText("Loser: "+p2scoreValue);
            p1score.setText("Loser: "+p1scoreValue);
        }
/*
        p1rockButton.setClickable(false);
        p1paperButton.setClickable(false);
        p1scissorButton.setClickable(false);

        p2rockButton.setClickable(false);
        p2paperButton.setClickable(false);
        p2scissorButton.setClickable(false);
        */
    }

    public String getWinner() {
        String condition;
        if (p1state.equals(p2state)) {
            condition = "TIED";
        } else if ((p1state.equals("rock") && p2state.equals("paper")) || (p1state.equals("paper") && p2state.equals("scissor")) || (p1state.equals("scissor") && p2state.equals("rock")) || p1state.equals("shoot")) {
            condition = "P2";
        } else{
            condition = "P1";
        }
        return condition;
    }
}