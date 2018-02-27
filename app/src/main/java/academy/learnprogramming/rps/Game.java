package academy.learnprogramming.rps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

//TODO add sound
//TODO finish intro screen
//TODO fix all of the yellows
//TODO fix encapsulation
//TODO make score 0 when game starts
//TODO implement ads

public class Game extends AppCompatActivity {
    GameState gameState = GameState.getInstance();

    int player1, player2;

    private ImageButton p1paperButton;
    private ImageButton p1rockButton;
    private ImageButton p1scissorButton;
    private ImageView p1choiceImage;

    private ImageButton p2paperButton;
    private ImageButton p2rockButton;
    private ImageButton p2scissorButton;
    private ImageView p2choiceImage;

    private TextView p1score, p2score;
    private ProgressBar matchTimer1, matchTimer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Run app in fullscreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Load main menu view
        setContentView(R.layout.act_singleplayer);


        //Instantiate players
        player1 = gameState.addPlayer("Player 1");
        player2 = gameState.addPlayer("Player 2");


        //Player 1 Controls
        p1paperButton = findViewById(R.id.p1paperButton);
        p1rockButton = findViewById(R.id.p1rockButton);
        p1scissorButton = findViewById(R.id.p1scissorButton);
        p1choiceImage = findViewById(R.id.p1choiceImage);
        p1score = findViewById(R.id.p1score);
        matchTimer1 = findViewById(R.id.matchTimer1);

        //Player 2 Controls
        p2paperButton = findViewById(R.id.p2paperButton);
        p2rockButton = findViewById(R.id.p2rockButton);
        p2scissorButton = findViewById(R.id.p2scissorButton);
        p2choiceImage = findViewById(R.id.p2choiceImage);
        p2score = findViewById(R.id.p2score);
        matchTimer2 = findViewById(R.id.matchTimer2);


        p1rockButton.setOnClickListener((View v) -> {
            p1rockButton.setImageResource(R.drawable.ic_rock);
            p1rockButton.setClickable(false);

            p1scissorButton.setImageResource(R.drawable.ic_scissors);
            p1scissorButton.setClickable(true);

            p1paperButton.setImageResource(R.drawable.ic_paper);
            p1paperButton.setClickable(true);

            gameState.selectCard(player1, Card.ROCK);
        });

        p1paperButton.setOnClickListener((View v) -> {
            p1paperButton.setImageResource(R.drawable.ic_paper);
            p1paperButton.setClickable(false);

            p1rockButton.setImageResource(R.drawable.ic_rock);
            p1rockButton.setClickable(true);

            p1scissorButton.setImageResource(R.drawable.ic_scissors);
            p1scissorButton.setClickable(true);

            gameState.selectCard(player1, Card.PAPER);
        });

        p1scissorButton.setOnClickListener((View v) -> {
            p1scissorButton.setImageResource(R.drawable.ic_scissors);
            p1scissorButton.setClickable(false);

            p1paperButton.setImageResource(R.drawable.ic_paper);
            p1paperButton.setClickable(true);

            p1rockButton.setImageResource(R.drawable.ic_rock);
            p1rockButton.setClickable(true);

            gameState.selectCard(player1, Card.SCISSORS);
        });

        p2rockButton.setOnClickListener((View v) -> {
            p2rockButton.setImageResource(R.drawable.ic_rock);
            p2rockButton.setClickable(false);

            p2scissorButton.setImageResource(R.drawable.ic_scissors);
            p2scissorButton.setClickable(true);

            p2paperButton.setImageResource(R.drawable.ic_paper);
            p2paperButton.setClickable(true);

            gameState.selectCard(player2, Card.ROCK);
        });

        p2paperButton.setOnClickListener((View v) -> {
            p2paperButton.setImageResource(R.drawable.ic_paper);
            p2paperButton.setClickable(false);

            p2rockButton.setImageResource(R.drawable.ic_rock);
            p2rockButton.setClickable(true);

            p2scissorButton.setImageResource(R.drawable.ic_scissors);
            p2scissorButton.setClickable(true);

            gameState.selectCard(player2, Card.PAPER);
        });

        p2scissorButton.setOnClickListener((View v) -> {
            p2scissorButton.setImageResource(R.drawable.ic_scissors);
            p2scissorButton.setClickable(false);

            p2paperButton.setImageResource(R.drawable.ic_paper);
            p2paperButton.setClickable(true);

            p2rockButton.setImageResource(R.drawable.ic_rock);
            p2rockButton.setClickable(true);

            gameState.selectCard(player2, Card.SCISSORS);
        });

        startGame();

    }

    public void startGame() {
        unGrayButtons();

        initGame();
        gameState.startGame();

        Thread gameThread = new Thread(() -> {
            while(!gameState.isGameOver()) {
                try {
                    gameState.applyScores();


                    runOnUiThread(() -> {
                        p1score.setText(String.format(Locale.ENGLISH, "%d Points", gameState.getScore(player1)));
                        p2score.setText(String.format(Locale.ENGLISH, "%d Points", gameState.getScore(player2)));

                        p1choiceImage.setImageResource(gameState.getSelectedDrawable(player1));
                        p2choiceImage.setImageResource(gameState.getSelectedDrawable(player2));

                        matchTimer1.setProgress(gameState.getMatchTime());
                        matchTimer2.setProgress(gameState.getMatchTime());
                    });

                    Thread.sleep(100);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }

            endGame();
        }, "Game Thread");

        gameThread.start();
    }

    private void initGame() {
        runOnUiThread(() -> {
            unGrayButtons();

            matchTimer1.setMax(gameState.getMatchLength());
            matchTimer2.setMax(gameState.getMatchLength());
        });

        gameState.startGame();
    }

    public void endGame() {
        runOnUiThread(() -> {
            p1choiceImage.setImageResource(R.drawable.ic_paper);
            p2choiceImage.setImageResource(R.drawable.ic_paper);

            if (gameState.getScore(player1) > gameState.getScore(player2)) {
                p1score.setText(String.format(Locale.ENGLISH, "Winner: %d", gameState.getScore(player1)));
                p2score.setText(String.format(Locale.ENGLISH, "Loser: %d", gameState.getScore(player2)));
            } else if (gameState.getScore(player1) < gameState.getScore(player2)) {
                p2score.setText(String.format(Locale.ENGLISH, "Winner: %d", gameState.getScore(player2)));
                p1score.setText(String.format(Locale.ENGLISH, "Loser: %d", gameState.getScore(player1)));
            } else {
                p1score.setText(String.format(Locale.ENGLISH, "Tie: %d", gameState.getScore(player1)));
                p2score.setText(String.format(Locale.ENGLISH, "Tie: %d", gameState.getScore(player2)));
            }

            grayButtons();
        });
    }

//    public String getWinner() {
//        String condition;
//        if (p1state.equals(p2state)) {
//            condition = "TIED";
//        } else if ((p1state.equals("rock") && p2state.equals("paper")) || (p1state.equals("paper") && p2state.equals("scissor")) || (p1state.equals("scissor") && p2state.equals("rock")) || p1state.equals("shoot")) {
//            condition = "P2";
//        } else {
//            condition = "P1";
//        }
//        return condition;
//    }

    public void grayButtons(){
        p1rockButton.setClickable(false);
        p1paperButton.setClickable(false);
        p1scissorButton.setClickable(false);

        p2rockButton.setClickable(false);
        p2paperButton.setClickable(false);
        p2scissorButton.setClickable(false);

//        p1rockButton.setImageResource(R.drawable.rockgrayed);
//        p1scissorButton.setImageResource(R.drawable.scissorsgrayed);
//        p1paperButton.setImageResource(R.drawable.papergrayed);
//
//        p2rockButton.setImageResource(R.drawable.rockgrayed);
//        p2scissorButton.setImageResource(R.drawable.scissorsgrayed);
//        p2paperButton.setImageResource(R.drawable.papergrayed);
    }

    public void unGrayButtons(){
        p1rockButton.setClickable(true);
        p1paperButton.setClickable(true);
        p1scissorButton.setClickable(true);

        p2rockButton.setClickable(true);
        p2paperButton.setClickable(true);
        p2scissorButton.setClickable(true);

//        p1rockButton.setImageResource(R.drawable.rock);
//        p1scissorButton.setImageResource(R.drawable.scissors);
//        p1paperButton.setImageResource(R.drawable.paper);
//
//        p2rockButton.setImageResource(R.drawable.rock);
//        p2scissorButton.setImageResource(R.drawable.scissors);
//        p2paperButton.setImageResource(R.drawable.paper);
    }

}