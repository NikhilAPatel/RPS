package com.kotassium.rps;

import java.util.Objects;
import java.util.Random;

public class CPU {

    GameState gameState;
    Random rn = new Random();

    public CPU() {
        this.gameState = GameState.getInstance();
    }

    void doTurn() {
        if (!(Objects.equals(gameState.getWinner(), WinState.P2WINNING))) {
            gameState.selectCard(1, chooseCard());
        }
    }

    Card getCard() {
        return gameState.getP2Card();
    }

    Card chooseCard() {
        int num = rn.nextInt(3);
        Card card = numToCard(num);
        while (Objects.equals(card, getCard()) && !numGood(num)) {
            num = rn.nextInt();
            card = numToCard(num);
        }
        return card;
    }

    Card numToCard(int num) {
        switch (num) {
            case 0:
                return Card.ROCK;
            case 1:
                return Card.PAPER;
            case 2:
                return Card.SCISSORS;
            default:
                return Card.INITIAL;
        }
    }

    boolean numGood(int num) {
        return num == 0 || num == 1 || num == 2;
    }
}
