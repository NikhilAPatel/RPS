package academy.learnprogramming.rps;

public class Player {
    String name = "error";
    Card selected = Card.INITIAL;
    Card previousCard = Card.INITIAL;
    int score = 0;
    int previousScore = 0;
    //Powerup currentPowerup;
    //Powerup currentPowerdown;

    public Player(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void select(Card card) {
        this.selected = card;
    }

    public Card getSelection() {
        return selected;
    }

    public void setPrevCard(Card prevCard) {
        this.previousCard = prevCard;
    }

    public Card getPrevCard() {
        return previousCard;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public int getScore() {
        return score;
    }

    public void setPrevScore(int prevScore) {
        this.previousScore = prevScore;
    }

    public int getPrevScore() {
        return previousScore;
    }
}