package academy.learnprogramming.rps;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
    private boolean gameStart = false;
    private String p1state = "shoot";
    private String p2state = "shoot";
    private TextView p1score;
    private TextView p2score;
    private int millis = 0;
    public int count1 = 0;
    public int count2 = 0;
    private static final int matchTime=30000;
    private static final int scoreCheckTime=1000;
    public int p1scorenum;
    public int p2scorenum;
    private Button startButton;
    CountDownTimerPausable t1 = new CountDownTimerPausable(matchTime, 1) {

        public void onTick(long millisUntilFinished) {
            if (p1score.getText().toString().equals("")) {
                p1scorenum = 0;
            } else {
                p1scorenum = Integer.parseInt(p1score.getText().toString());
            }

            count1 = ((matchTime - (int) millisRemaining) / scoreCheckTime);
            p1score.setText(count1 + "");

        }

        public void onFinish() {
            //timer.setText("Game Done 1");
            endGame();
        }
    };
    CountDownTimerPausable t2 = new CountDownTimerPausable(matchTime, 1) {

        public void onTick(long millisUntilFinished) {
            if (p2score.getText().toString().equals("")) {
                p2scorenum = 0;
            } else {
                p2scorenum = Integer.parseInt(p2score.getText().toString());
            }

            count2 = ((matchTime - (int) millisRemaining) / scoreCheckTime);
            p2score.setText(count2 + "");

        }

        public void onFinish() {
            //timer.setText("Game Done 2");
            endGame();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //removes notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_activity);

        p1paperButton = (ImageButton) findViewById(R.id.p1paperButton);
        p1rockButton = (ImageButton) findViewById(R.id.p1rockButton);
        p1scissorButton = (ImageButton) findViewById(R.id.p1scissorButton);
        p1choiceImage = (ImageView) findViewById(R.id.p1choiceImage);
        p1score = (TextView) findViewById(R.id.p1score);

        p2paperButton = (ImageButton) findViewById(R.id.p2paperButton);
        p2rockButton = (ImageButton) findViewById(R.id.p2rockButton);
        p2scissorButton = (ImageButton) findViewById(R.id.p2scissorButton);
        p2choiceImage = (ImageView) findViewById(R.id.p2choiceImage);
        p2score = (TextView) findViewById(R.id.p2score);

        timer = (TextView) findViewById(R.id.timer);
        timer = (TextView) findViewById(R.id.timer);
        startButton = (Button) findViewById(R.id.startButton);

        View.OnClickListener startButtonOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenUpdate().start();
            }
        };

        View.OnClickListener rock2OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2rockButton.setImageResource(R.drawable.rockgrayed);
                p2choiceImage.setImageResource(R.drawable.rock);
                p2rockButton.setClickable(false);

                p2scissorButton.setImageResource(R.drawable.scissors);
                p2scissorButton.setClickable(true);

                p2paperButton.setImageResource(R.drawable.paper);
                p2paperButton.setClickable(true);
/*
                if (!gameStart) {
                    startGame();
                }
                */
                p2state = "rock";

                checkWinning();
            }
        };
        View.OnClickListener paper2OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2paperButton.setImageResource(R.drawable.papergrayed);
                p2choiceImage.setImageResource(R.drawable.paper);
                p2paperButton.setClickable(false);

                p2rockButton.setImageResource(R.drawable.rock);
                p2rockButton.setClickable(true);

                p2scissorButton.setImageResource(R.drawable.scissors);
                p2scissorButton.setClickable(true);
/*
                if (!gameStart) {
                    startGame();
                }
                */
                p2state = "paper";

                checkWinning();
            }
        };
        View.OnClickListener scissor2OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p2scissorButton.setImageResource(R.drawable.scissorsgrayed);
                p2choiceImage.setImageResource(R.drawable.scissors);
                p2scissorButton.setClickable(false);

                p2paperButton.setImageResource(R.drawable.paper);
                p2paperButton.setClickable(true);

                p2rockButton.setImageResource(R.drawable.rock);
                p2rockButton.setClickable(true);
/*
                if (!gameStart) {
                    startGame();
                }
                */
                p2state = "scissor";

                checkWinning();
            }
        };


        View.OnClickListener rock1OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1rockButton.setImageResource(R.drawable.rockgrayed);
                p1choiceImage.setImageResource(R.drawable.rock);
                p1rockButton.setClickable(false);

                p1scissorButton.setImageResource(R.drawable.scissors);
                p1scissorButton.setClickable(true);

                p1paperButton.setImageResource(R.drawable.paper);
                p1paperButton.setClickable(true);
/*
                if (!gameStart) {
                    startGame();
                }
                */
                p1state = "rock";

                checkWinning();
            }
        };
        View.OnClickListener paper1OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1paperButton.setImageResource(R.drawable.papergrayed);
                p1choiceImage.setImageResource(R.drawable.paper);
                p1paperButton.setClickable(false);

                p1rockButton.setImageResource(R.drawable.rock);
                p1rockButton.setClickable(true);

                p1scissorButton.setImageResource(R.drawable.scissors);
                p1scissorButton.setClickable(true);
/*
                if (!gameStart) {
                    startGame();
                }
                */
                p1state = "paper";


                checkWinning();
            }
        };
        View.OnClickListener scissor1OnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1scissorButton.setImageResource(R.drawable.scissorsgrayed);
                p1choiceImage.setImageResource(R.drawable.scissors);
                p1scissorButton.setClickable(false);

                p1paperButton.setImageResource(R.drawable.paper);
                p1paperButton.setClickable(true);

                p1rockButton.setImageResource(R.drawable.rock);
                p1rockButton.setClickable(true);
/*
                if (!gameStart) {
                    startGame();
                }
                */
                p1state = "scissor";

                checkWinning();
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
        gameStart = true;
        new CountDownTimer(matchTime, 1) {

            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
                millis = (int) millisUntilFinished / 1000;
            }

            public void onFinish() {

                endGame();
            }
        }.start();
    }

    public Thread screenUpdate()
    {
        Thread updateScreen=new Thread() {
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(!gameStart){
                                    startGame();
                                }
                            }
                        });

                    }
                } catch (InterruptedException e) {

                }
            }
        };
        return updateScreen;

    }


    //TODO finish endGame method (find out who wins and have options for quit to menu and play again)
    public void endGame() {
        timer.setText("Game Done");
        int p1scorenum;

        p1rockButton.setClickable(false);
        p1paperButton.setClickable(false);
        p1scissorButton.setClickable(false);

        p2rockButton.setClickable(false);
        p2paperButton.setClickable(false);
        p2scissorButton.setClickable(false);
        try {
            t1.pause();
        } catch (Exception e) {}
        try{
            t2.pause();
        }catch(Exception e){}

    }

    public void checkWinning() {
        String condition = "TIED";
        if (p1state.equals(p2state)) {
            condition = "TIED";
        } else if ((p1state.equals("rock") && p2state.equals("paper")) || (p1state.equals("paper") && p2state.equals("scissor")) || (p1state.equals("scissor") && p2state.equals("rock")) || p1state.equals("shoot")) {
            condition = "LOSS";
        } else if ((p1state.equals("paper") && p2state.equals("rock")) || (p1state.equals("rock") && p2state.equals("scissor")) || (p1state.equals("scissor") && p2state.equals("paper")) || p2state.equals("shoot")) {
            condition = "WIN";
        }
        updateTimers(condition);
    }

    public void updateTimers(String condition) {


        if (condition.equals("WIN")) {
            count1 = 0;
            t1.start();
        } else {
            try {
                t1.pause();
            } catch (Exception e) {
            }
        }
        if (condition.equals("LOSS")) {
            count2 = 0;
            t2.start();
        } else {
            try {
                t2.pause();
            } catch (Exception e) {
            }
        }
    }

}
