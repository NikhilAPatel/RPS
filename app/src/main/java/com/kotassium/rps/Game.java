package com.kotassium.rps;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

//TODO add sound
//TODO finish intro screen
//TODO fix all of the yellows
//TODO fix encapsulation
//TODO make score 0 when game starts
//TODO implement ads
//TODO make default choice image not unmute icon
//TODO fix bug where scissor gives no points when facing nothing
//TODO fix bug where both players get points when its scissors vs paper
//TODO reset scores when new game starts
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
    private Button startButton;

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

        //Miscellaneous
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> startGame());


        p1rockButton.setOnClickListener((View v) -> {
            grayButton(p1rockButton);

            unGrayButton(p1scissorButton);
            unGrayButton(p1paperButton);

            gameState.selectCard(player1, Card.ROCK);
        });

        p1paperButton.setOnClickListener((View v) -> {
            grayButton(p1paperButton);

            unGrayButton(p1scissorButton);
            unGrayButton(p1rockButton);

            gameState.selectCard(player1, Card.PAPER);
        });

        p1scissorButton.setOnClickListener((View v) -> {
            grayButton(p1scissorButton);

            unGrayButton(p1paperButton);
            unGrayButton(p1rockButton);

            gameState.selectCard(player1, Card.SCISSORS);
        });

        p2rockButton.setOnClickListener((View v) -> {
            grayButton(p2rockButton);

            unGrayButton(p2scissorButton);
            unGrayButton(p2paperButton);

            gameState.selectCard(player2, Card.ROCK);
        });

        p2paperButton.setOnClickListener((View v) -> {
            grayButton(p2paperButton);

            unGrayButton(p2scissorButton);
            unGrayButton(p2rockButton);

            gameState.selectCard(player2, Card.PAPER);
        });

        p2scissorButton.setOnClickListener((View v) -> {
            grayButton(p2scissorButton);

            unGrayButton(p2paperButton);
            unGrayButton(p2rockButton);

            gameState.selectCard(player2, Card.SCISSORS);
        });


    }

    public void startGame() {
        unGrayButtons();
        initGame();
        gameState.startGame();

        Thread gameThread = new Thread(() -> {
            while (!gameState.isGameOver()) {
                try {
                    gameState.applyScores();


                    runOnUiThread(() -> {
                        if (Objects.equals(gameState.getWinner(), "P1")) {
                            matchTimer2.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                            matchTimer1.getProgressDrawable().setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN);
                        } else if (Objects.equals(gameState.getWinner(), "P2")) {
                            matchTimer2.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                            matchTimer1.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.SRC_IN);
                        } else {
                            matchTimer2.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                            matchTimer1.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                        }
                        p1score.setText(String.format(Locale.ENGLISH, "%d Points", gameState.getScore(player1)));
                        p2score.setText(String.format(Locale.ENGLISH, "%d Points", gameState.getScore(player2)));

                        p1choiceImage.setImageResource(gameState.getSelectedDrawable(player1));
                        p2choiceImage.setImageResource(gameState.getSelectedDrawable(player2));

                        matchTimer1.setProgress(gameState.getMatchTime());
                        matchTimer2.setProgress(gameState.getMatchTime());
                    });

                    Thread.sleep(100);
                } catch (InterruptedException e) {
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


    public void grayButtons() {

        grayButton(p1rockButton);
        grayButton(p1paperButton);
        grayButton(p1scissorButton);

        grayButton(p2rockButton);
        grayButton(p2paperButton);
        grayButton(p2scissorButton);

        matchTimer1.setVisibility(View.INVISIBLE);
        matchTimer2.setVisibility(View.INVISIBLE);

        startButton.setVisibility(View.VISIBLE);
    }

    public void unGrayButtons() {
        unGrayButton(p1rockButton);
        unGrayButton(p1paperButton);
        unGrayButton(p1scissorButton);

        unGrayButton(p2rockButton);
        unGrayButton(p2paperButton);
        unGrayButton(p2scissorButton);

        matchTimer1.setVisibility(View.VISIBLE);
        matchTimer2.setVisibility(View.VISIBLE);

        startButton.setVisibility(View.INVISIBLE);
    }

    public void grayButton(ImageButton button) {
        button.setClickable(false);
        button.setColorFilter(Color.GRAY);
    }

    public void unGrayButton(ImageButton button) {
        button.setClickable(true);
        button.setColorFilter(Color.BLACK);
    }

}