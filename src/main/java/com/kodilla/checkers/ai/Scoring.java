package com.kodilla.checkers.ai;

public class Scoring {
    private int computerScore;
    private int humanScore;

    public Scoring(int computerScore, int humanScore) {
        this.computerScore = computerScore;
        this.humanScore = humanScore;
    }

    public int getComputerScore() {
        return computerScore;
    }

    public int getHumanScore() {
        return humanScore;
    }
}

