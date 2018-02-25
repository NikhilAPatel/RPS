package academy.learnprogramming.rps;

class GameState {
    private static final GameState instance = new GameState();

    static GameState getInstance() {
        return instance;
    }

    boolean muted = false;
    Card p1Selected = Card.INITIAL;
    Card p2Selected = Card.INITIAL;

    public void setMuted(boolean muted) {
        this.muted = muted;
    }

    public boolean isMuted() {
        return muted;
    }
}
