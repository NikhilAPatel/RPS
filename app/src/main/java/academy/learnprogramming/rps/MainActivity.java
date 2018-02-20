package academy.learnprogramming.rps;

import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //TODO add sound
    //TODO add way to keep score
    //TODO add intro screen
    //TODO add actual way to start game
    //TODO fix all the timer bugs(basically make it work)
    //TODO add way for users to know who is winning
    //TODO find a way to get rid of title bar
    //TODO put this guy on Github


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
    public int count = 0;
    public int p1scorenum;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

                if (!gameStart) {
                    startGame();
                }
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

                if (!gameStart) {
                    startGame();
                }
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

                if (!gameStart) {
                    startGame();
                }
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

                if (!gameStart) {
                    startGame();
                }
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

                if (!gameStart) {
                    startGame();
                }
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

                if (!gameStart) {
                    startGame();
                }
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

    }

    public void startGame() {
        gameStart = true;
        new CountDownTimer(30000, 1) {

            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(millisUntilFinished / 1000));
                millis = (int) millisUntilFinished / 1000;
            }

            public void onFinish() {
                timer.setText("Game Done");
                endGame();
            }
        }.start();
    }

    //TODO finish endGame method (find out who wins and have options for quit to menu and play again)
    public void endGame() {
        int p1scorenum;

        p1rockButton.setClickable(false);
        p1paperButton.setClickable(false);
        p1scissorButton.setClickable(false);

        p2rockButton.setClickable(false);
        p2paperButton.setClickable(false);
        p2scissorButton.setClickable(false);
        if(p1score.getText().toString().equals("")){
            p1scorenum = 0;
        }else{
            p1scorenum = Integer.parseInt(p1score.getText().toString());
        }
        p1score.setText(p1scorenum + count + "" );
    }

    public void checkWinning() {
        String condition = "TIED";
        if (p1state.equals(p2state)) {
            condition = "TIED";
        } else if ((p1state.equals("rock") && p2state.equals("paper")) || (p1state.equals("paper") && p2state.equals("scissors")) || (p1state.equals("scissors") && p2state.equals("rock")) || p1state.equals("shoot")) {
            condition = "LOSS";
        } else if ((p1state.equals("paper") && p2state.equals("rock")) || (p1state.equals("rock") && p2state.equals("scissors")) || (p1state.equals("scissors") && p2state.equals("paper")) || p2state.equals("shoot")) {
            condition = "WIN";
        }
        System.out.println(condition);
        updateTimers(condition);
    }

    public void updateTimers(String condition) {


        if (condition.equals("WIN")) {
            count = 0;
            int delay = 1000; //
            int period = 2000; // repeat every sec.
            if(p1score.getText().toString().equals("")){
                p1scorenum = 0;
            }else{
                p1scorenum = Integer.parseInt(p1score.getText().toString());
            }

            Timer timer = new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {

                public void run() {
                    // Your code

                    count++;
                    System.out.println("count " + count );

                    p1score.setText(p1scorenum + count + "" );


                }
            }, delay, period);

        }
    }

}
