package com.kotassium.rps;

import android.media.MediaPlayer;

import java.util.ArrayList;
import java.util.Objects;

import static com.kotassium.rps.Difficulty.EASY;
import static com.kotassium.rps.Difficulty.HARD;
import static com.kotassium.rps.Difficulty.MEDIUM;
import static com.kotassium.rps.Menu.musicThread;
import static com.kotassium.rps.WinState.P1WINNING;
import static com.kotassium.rps.WinState.P2WINNING;
import static com.kotassium.rps.WinState.TIED;

class GameState {
    private static final GameState instance = new GameState();
    private boolean muted = false;
    private ArrayList<Player> players = new ArrayList<>();
    private int matchLength = 30000;
    private long endTime;
    private int nextCpuMove = 30000;
    private int difficulty = 2000;


    static GameState getInstance() {
        return instance;
    }

    boolean isMuted() {
        return muted;
    }

    void setMuted(boolean muted) {
        this.muted = muted;
    }

    void startMusic(){
        if(!musicThread.isInterrupted()){
            musicThread.start();
        }
    }

    void stopMusic(){
        if(musicThread.isInterrupted()){
            musicThread.interrupt();
        }
    }


    /**
     * Adds a player to the game
     *
     * @param name The name of the new player
     * @param index The index that the new player should be located at in the Player List
     * @return The player's ID
     */
    int addPlayer(String name, int index) {
        Player newPlayer = new Player(name);
        players.add(index, newPlayer);
        return players.indexOf(newPlayer);
    }

    /**
     * Sets a players selected card
     *
     * @param pid       The player to change the selection of
     * @param selection The card the player should selectCard
     */
    void selectCard(int pid, Card selection) {
        Player p = players.get(pid);
        if (p != null)
            p.select(selection);
        else
            throw new NullPointerException("Invalid PID supplied, got null!");
    }

    /**
     * Gets the specified player's current sleection
     *
     * @param pid The ID of the player to get the selection of
     * @return The player's current selection
     */
    private Card getSelected(int pid) {
        Player p = players.get(pid);
        if (p != null)
            return p.getSelection();
        else
            throw new NullPointerException("Invalid PID supplied, got null!");
    }

    /**
     * Gets the resource ID for the drawable associated with the player's current selection
     *
     * @param pid The ID of the player to get the drawable for
     * @return Resource ID for the currently selected card
     */
    int getSelectedDrawable(int pid) {
        switch (getSelected(pid)) {
            case INITIAL:
                return R.drawable.ic_initial;
            case ROCK:
                return R.drawable.ic_rock;
            case PAPER:
                return R.drawable.ic_paper;
            case SCISSORS:
                return R.drawable.ic_scissors;
            default:
                throw new NullPointerException("Invalid PID supplied, got null!");
        }
    }


    int getScore(int pid) {
        Player p = players.get(pid);
        if (p != null)
            return p.getScore();
        else
            throw new NullPointerException("Invalid PID supplied, got null!");
    }


    public int getMatchTime() {
        return (int) (endTime - System.currentTimeMillis());
    }

    public void startGame() {

        endTime = System.currentTimeMillis() + matchLength;
    }

    public boolean isGameOver() {
        return System.currentTimeMillis() <= endTime;
    }

    public int getMatchLength() {
        return matchLength;
    }

    public void setMatchLength(int matchTime) {
        this.matchLength = matchTime;
        this.nextCpuMove = matchTime;
    }

    public void setNextCpuMove() {
        this.nextCpuMove -= this.difficulty;
    }

    public boolean canCpuMove() {
        return this.nextCpuMove >= getMatchTime();
    }

    public void resetGame() {
        for (Player p : players) {
            p.score = 0;
            p.select(Card.INITIAL);
        }
    }

    void applyScores() {
        Player p1 = players.get(0);
        Player p2 = players.get(1);
        int amount = 1;
        if (checkIfDoublePoints()) {
            amount = 2;
        }
        if (!(Objects.equals(getWinner(), TIED))) {
            if (Objects.equals(getWinner(), P2WINNING)) {
                p2.addScore(amount);
            } else {
                p1.addScore(amount);
            }
        }
    }

    boolean checkIfDoublePoints() {
        return this.getMatchTime() < this.getMatchLength() / 4;
    }

    Card getP2Card() {
        return players.get(0).getSelection();
    }

    int getDifficulty() {
        return this.difficulty;
    }

    void setDifficulty(Difficulty diff) {
        if (Objects.equals(diff, EASY)) {
            difficulty = 3000;
        } else if (Objects.equals(diff, MEDIUM)) {
            difficulty = 2000;
        } else if (Objects.equals(diff, HARD)) {
            difficulty = 1000;
        }
    }

    public WinState getWinner() {
        Player p1 = players.get(0);
        Player p2 = players.get(1);

        WinState condition;
        if (p1.getSelection() == p2.getSelection()) {
            condition = TIED;
        } else if ((p1.getSelection() == Card.ROCK && p2.getSelection() == Card.PAPER) || (p1.getSelection() == Card.PAPER && p2.getSelection() == Card.SCISSORS) || (p1.getSelection() == Card.SCISSORS && p2.getSelection() == Card.ROCK) || p1.getSelection() == Card.INITIAL) {
            condition = P2WINNING;
        } else {
            condition = P1WINNING;
        }
        return condition;
    }


}
