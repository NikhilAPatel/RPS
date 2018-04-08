package com.kotassium.rps;

public class Player {
    String name;
    int score = 0;
    private Card selected = Card.INITIAL;
    //Powerup currentPowerup;
    //Powerup currentPowerdown;

    Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void select(Card card) {
        this.selected = card;
    }

    public Card getSelection() {
        return selected;
    }

    public void addScore(int amount) {
        this.score += amount;
    }

    public int getScore() {
        return score;
    }
}