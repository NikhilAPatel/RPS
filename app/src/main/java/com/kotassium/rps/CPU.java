package com.kotassium.rps;

import java.util.Objects;
import java.util.Random;

public class CPU {

    private GameState gameState;
    private Random rn = new Random();

    CPU() {
        this.gameState = GameState.getInstance();
    }

    void doTurn() {
        if (!(Objects.equals(gameState.getWinner(), WinState.P2WINNING))) {
            gameState.setNextCpuMove();
            gameState.selectCard(1, chooseCard());
        }
    }

    private Card getCard() {
        return gameState.getP2Card();
    }

    private Card chooseCard() {
        int num = rn.nextInt(3);
        Card card = numToCard(num);
        while (Objects.equals(card, getCard()) && !numGood(num)) {
            num = rn.nextInt();
            card = numToCard(num);
        }
        return card;
    }

    private Card numToCard(int num) {
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

    private boolean numGood(int num) {
        return num == 0 || num == 1 || num == 2;
    }
}
