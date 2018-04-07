package com.kotassium.rps;

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

import static android.graphics.Color.*;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.kotassium.rps.WinState.*;

//TODO make cpu have to wait to do turns so its not so op.

public class SinglePlayer extends AppCompatActivity {

    GameState gameState = GameState.getInstance();

    int player1, cpudisplay;

    CPU cpubrain = new CPU();

    private ImageButton p1paperButton;
    private ImageButton p1rockButton;
    private ImageButton p1scissorButton;
    private ImageView p1choiceImage;
    private TextView p1doubleLabel;
    private TextView p1pointsLabel;

    private ImageView cpuchoiceImage;

    private TextView p1score, cpuscore;
    private ProgressBar matchTimer1, matchTimer2;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_singleplayer);

        //Run app in fullscreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Load main menu view
        setContentView(R.layout.act_singleplayer);


        //Instantiate players
        player1 = gameState.addPlayer("Player 1", 0);
        cpudisplay = gameState.addPlayer("Player 2", 1);


        //Player 1 Controls
        p1paperButton = findViewById(R.id.p1paperButton);
        p1rockButton = findViewById(R.id.p1rockButton);
        p1scissorButton = findViewById(R.id.p1scissorButton);
        p1choiceImage = findViewById(R.id.p1choiceImage);
        p1score = findViewById(R.id.p1score);
        matchTimer1 = findViewById(R.id.matchTimer1);
        p1doubleLabel = findViewById(R.id.p1double);
        p1pointsLabel = findViewById(R.id.p1points);

        //CPU Controls
        cpuchoiceImage = findViewById(R.id.p2choiceImage);
        cpuscore = findViewById(R.id.p2score);
        matchTimer2 = findViewById(R.id.matchTimer2);

        //Miscellaneous
        startButton = findViewById(R.id.startButton);

        startButton.setOnClickListener(v -> startGame());
    }

    public void initButtons() {


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
    }


    public void startGame() {
        initButtons();
        unGrayButtons();
        initGame();
        gameState.startGame();

        Thread gameThread = new Thread(() -> {
            while (!gameState.isGameOver()) {
                try {
                    if (gameState.checkIfDoublePoints()) {
                        runOnUiThread(this::setDoublePointsVisible);
                    }

                    cpubrain.doTurn();

                    gameState.applyScores();


                    runOnUiThread(() -> {
                        if (Objects.equals(gameState.getWinner(), P1WINNING)) {
                            matchTimer2.getProgressDrawable().setColorFilter(RED, PorterDuff.Mode.SRC_IN);
                            matchTimer1.getProgressDrawable().setColorFilter(RED, PorterDuff.Mode.SRC_IN);
                        } else if (Objects.equals(gameState.getWinner(), P2WINNING)) {
                            matchTimer2.getProgressDrawable().setColorFilter(BLUE, PorterDuff.Mode.SRC_IN);
                            matchTimer1.getProgressDrawable().setColorFilter(BLUE, PorterDuff.Mode.SRC_IN);
                        } else {
                            matchTimer2.getProgressDrawable().setColorFilter(BLACK, PorterDuff.Mode.SRC_IN);
                            matchTimer1.getProgressDrawable().setColorFilter(BLACK, PorterDuff.Mode.SRC_IN);
                        }
                        p1score.setText(String.format(Locale.ENGLISH, "%d Points", gameState.getScore(player1)));
                        cpuscore.setText(String.format(Locale.ENGLISH, "%d Points", gameState.getScore(cpudisplay)));

                        p1choiceImage.setImageResource(gameState.getSelectedDrawable(player1));
                        cpuchoiceImage.setImageResource(gameState.getSelectedDrawable(cpudisplay));

                        matchTimer1.setProgress(gameState.getMatchTime());
                        matchTimer2.setProgress(gameState.getMatchTime());
                    });

                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            endGame();
        }, "TwoPlayer Thread");

        gameThread.start();
    }

    private void initGame() {
        runOnUiThread(() -> {
            gameState.resetGame();
            unGrayButtons();
            matchTimer1.setMax(gameState.getMatchLength());
            matchTimer2.setMax(gameState.getMatchLength());
        });

        gameState.startGame();
    }

    public void endGame() {
        runOnUiThread(() -> {

            p1choiceImage.setImageResource(R.drawable.ic_paper);
            cpuchoiceImage.setImageResource(R.drawable.ic_paper);
            setDoublePointsInvisible();
            p1choiceImage.setImageResource(R.drawable.ic_initial);
            cpuchoiceImage.setImageResource(R.drawable.ic_initial);

            if (gameState.getScore(player1) > gameState.getScore(cpudisplay)) {
                p1score.setText(String.format(Locale.ENGLISH, "Winner: %d", gameState.getScore(player1)));
                cpuscore.setText(String.format(Locale.ENGLISH, "Loser: %d", gameState.getScore(cpudisplay)));
            } else if (gameState.getScore(player1) < gameState.getScore(cpudisplay)) {
                cpuscore.setText(String.format(Locale.ENGLISH, "Winner: %d", gameState.getScore(cpudisplay)));
                p1score.setText(String.format(Locale.ENGLISH, "Loser: %d", gameState.getScore(player1)));
            } else {
                p1score.setText(String.format(Locale.ENGLISH, "Tie: %d", gameState.getScore(player1)));
                cpuscore.setText(String.format(Locale.ENGLISH, "Tie: %d", gameState.getScore(cpudisplay)));
            }

            grayButtons();
        });
    }


    public void grayButtons() {

        grayButton(p1rockButton);
        grayButton(p1paperButton);
        grayButton(p1scissorButton);

        matchTimer1.setVisibility(INVISIBLE);
        matchTimer2.setVisibility(INVISIBLE);

        startButton.setVisibility(VISIBLE);
    }

    public void unGrayButtons() {
        unGrayButton(p1rockButton);
        unGrayButton(p1paperButton);
        unGrayButton(p1scissorButton);

        matchTimer1.setVisibility(VISIBLE);
        matchTimer2.setVisibility(VISIBLE);

        startButton.setVisibility(INVISIBLE);
    }

    public void grayButton(ImageButton button) {
        button.setClickable(false);
        button.setColorFilter(GRAY);
    }

    public void unGrayButton(ImageButton button) {
        button.setClickable(true);
        button.setColorFilter(BLACK);
    }

    public void setDoublePointsInvisible() {
        p1doubleLabel.setVisibility(INVISIBLE);
        p1pointsLabel.setVisibility(INVISIBLE);


    }

    public void setDoublePointsVisible() {
        p1doubleLabel.setVisibility(VISIBLE);
        p1pointsLabel.setVisibility(VISIBLE);
        p1pointsLabel.setTextColor(RED);
        p1doubleLabel.setTextColor(RED);

    }

}
