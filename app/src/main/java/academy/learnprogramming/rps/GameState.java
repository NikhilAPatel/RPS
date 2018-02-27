package academy.learnprogramming.rps;

import java.util.ArrayList;

class GameState {
    private static final GameState instance = new GameState();

    static GameState getInstance() {
        return instance;
    }

    private boolean muted = false;
    private ArrayList<Player> players = new ArrayList<>();

    private int matchLength = 30000; // 30 second match time. TODO pull from options
    private long endTime;

    void setMuted(boolean muted) {
        this.muted = muted;
    }

    boolean isMuted() {
        return muted;
    }

    /**
     * Adds a player to the game
     * @param name The name of the new player
     * @return The player's ID
     */
    int addPlayer(String name) {
        Player newPlayer = new Player(name);
        players.add(newPlayer);
        return players.indexOf(newPlayer);
    }

    /**
     * Sets a players selected card
     * @param pid The player to change the selection of
     * @param selection The card the player should selectCard
     */
    void selectCard(int pid, Card selection) {
        Player p = players.get(pid);
        if(p != null)
            p.select(selection);
        else
            throw new NullPointerException("Invalid PID supplied, got null!");
    }

    /**
     * Gets the specified player's current sleection
     * @param pid The ID of the player to get the selection of
     * @return The player's current selection
     */
    Card getSelected(int pid) {
        Player p = players.get(pid);
        if(p != null)
            return p.getSelection();
        else
            throw new NullPointerException("Invalid PID supplied, got null!");
    }

    /**
     * Gets the resource ID for the drawable associated with the player's current selection
     * @param pid The ID of the player to get the drawable for
     * @return Resource ID for the currently selected card
     */
    int getSelectedDrawable(int pid) {
        switch(getSelected(pid)) {
            case INITIAL:
                return R.drawable.ic_mute_off;
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

    void addScore(int pid, int amount) {
        Player p = players.get(pid);
        if(p != null)
            p.addScore(amount);
        else
            throw new NullPointerException("Invalid PID supplied, got null!");
    }

    int getScore(int pid) {
        Player p = players.get(pid);
        if(p != null)
            return p.getScore();
        else
            throw new NullPointerException("Invalid PID supplied, got null!");
    }

    public int getMatchTime() {
        return (int)(endTime - System.currentTimeMillis());
    }

    public void startGame() {
        endTime = System.currentTimeMillis() + matchLength;
    }

    public boolean isGameOver() {
        return System.currentTimeMillis() > endTime;
    }

    public void setMatchLength(int matchTime) {
        this.matchLength = matchTime;
    }

    public int getMatchLength() {
        return matchLength;
    }

    void applyScores() {
        for(Player p1:players) {
            for(Player p2:players) {
                if(!p1.equals(p2)) {
                    switch(p1.getSelection()) {
                        case ROCK:
                            if(p2.getSelection() == Card.SCISSORS) p1.addScore(1);
                            break;
                        case PAPER:
                            if(p2.getSelection() == Card.ROCK) p1.addScore(1);
                            break;
                        case SCISSORS:
                            if(p2.getSelection() == Card.PAPER) p1.addScore(1);
                            break;
                    }
                }
            }
        }
    }
}
