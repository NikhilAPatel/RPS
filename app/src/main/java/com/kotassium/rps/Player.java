package com.kotassium.rps;

public class Player {
    String name;
    private Card selected = Card.INITIAL;
    int score = 0;
    //Powerup currentPowerup;
    //Powerup currentPowerdown;

    Player(String name) {
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

    public void addScore(int amount) {
        this.score += amount;
    }

    public int getScore() {
        return score;
    }
}