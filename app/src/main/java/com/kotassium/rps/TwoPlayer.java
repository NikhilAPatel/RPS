package com.kotassium.rps;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GRAY;
import static android.graphics.Color.RED;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.kotassium.rps.WinState.P1WINNING;
import static com.kotassium.rps.WinState.P2WINNING;

//TODO add sound
//TODO finish all graphics
//TODO implement ads
//TODO Powerups
//TODO add a 3 second timer before game actually starts


public class TwoPlayer extends AppCompatActivity {
    GameState gameState = GameState.getInstance();

    int player1, player2;

    private ImageButton p1paperButton;
    private ImageButton p1rockButton;
    private ImageButton p1scissorButton;
    private ImageView p1choiceImage;
    private TextView p1doubleLabel;
    private TextView p1pointsLabel;

    private ImageButton p2paperButton;
    private ImageButton p2rockButton;
    private ImageButton p2scissorButton;
    private ImageView p2choiceImage;
    private TextView p2doubleLabel;
    private TextView p2pointsLabel;

    private TextView p1score, p2score;
    private ProgressBar matchTimer1, matchTimer2;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Run app in fullscreen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Load main menu view
        setContentView(R.layout.act_twoplayer);


        //Instantiate players
        player1 = gameState.addPlayer("Player 1", 0);
        player2 = gameState.addPlayer("Player 2", 1);


        //Player 1 Controls
        p1paperButton = findViewById(R.id.p1paperButton);
        p1rockButton = findViewById(R.id.p1rockButton);
        p1scissorButton = findViewById(R.id.p1scissorButton);
        p1choiceImage = findViewById(R.id.p1choiceImage);
        p1score = findViewById(R.id.p1score);
        matchTimer1 = findViewById(R.id.matchTimer1);
        p1doubleLabel = findViewById(R.id.p1double);
        p1pointsLabel = findViewById(R.id.p1points);

        //Player 2 Controls
        p2paperButton = findViewById(R.id.p2paperButton);
        p2rockButton = findViewById(R.id.p2rockButton);
        p2scissorButton = findViewById(R.id.p2scissorButton);
        p2choiceImage = findViewById(R.id.p2choiceImage);
        p2score = findViewById(R.id.p2score);
        matchTimer2 = findViewById(R.id.matchTimer2);
        p2doubleLabel = findViewById(R.id.p2double);
        p2pointsLabel = findViewById(R.id.p2points);

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
        startButton.setVisibility(INVISIBLE);
        Thread gameThread = new Thread(() -> {
            while (gameState.isGameOver()) {
                try {
                    if (gameState.checkIfDoublePoints()) {
                        runOnUiThread(this::setDoublePointsVisible);
                    }
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
        }, "TwoPlayer Thread");

        Thread timerThread = new Thread(() -> {
            try {
                p1score.setText(R.string.rock);
                p2score.setText(R.string.rock);
                Thread.sleep(1000);
                p1score.setText(R.string.paper);
                p2score.setText(R.string.paper);
                Thread.sleep(1000);
                p1score.setText(R.string.scissors);
                p2score.setText(R.string.scissors);
                Thread.sleep(1000);
                p1score.setText(R.string.shoot);
                p2score.setText(R.string.shoot);
                Thread.sleep(500);
                initGame();
                gameState.startGame();
                gameThread.start();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        timerThread.start();

    }

    private void initGame() {
        initButtons();
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
            p2choiceImage.setImageResource(R.drawable.ic_paper);
            setDoublePointsInvisible();
            p1choiceImage.setImageResource(R.drawable.ic_initial);
            p2choiceImage.setImageResource(R.drawable.ic_initial);

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

        matchTimer1.setVisibility(INVISIBLE);
        matchTimer2.setVisibility(INVISIBLE);

        startButton.setVisibility(VISIBLE);
    }

    public void unGrayButtons() {
        unGrayButton(p1rockButton);
        unGrayButton(p1paperButton);
        unGrayButton(p1scissorButton);

        unGrayButton(p2rockButton);
        unGrayButton(p2paperButton);
        unGrayButton(p2scissorButton);

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
        p2doubleLabel.setVisibility(INVISIBLE);
        p2pointsLabel.setVisibility(INVISIBLE);

    }

    public void setDoublePointsVisible() {
        p1doubleLabel.setVisibility(VISIBLE);
        p1pointsLabel.setVisibility(VISIBLE);
        p2doubleLabel.setVisibility(VISIBLE);
        p2pointsLabel.setVisibility(VISIBLE);
        p1pointsLabel.setTextColor(RED);
        p1doubleLabel.setTextColor(RED);
        p2pointsLabel.setTextColor(BLUE);
        p2doubleLabel.setTextColor(BLUE);
    }

}