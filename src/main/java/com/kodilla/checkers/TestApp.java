package com.kodilla.checkers;

import com.kodilla.checkers.ai.AI;
import com.kodilla.checkers.ai.Move;
import com.kodilla.checkers.gui.UserDialogs;

class TestApp {
    public static void main(String[] args) throws InterruptedException {
        Board board = new Board();
        board.initBoard();
        System.out.println(board);
        while (true) {
            if (board.getWhoseMove() == FigureColor.WHITE) {
                Move move = UserDialogs.getNextMove();
                board.move(move);
            } else {
                Thread.sleep(1000);

                Move move = AI.getBestBlackMove(board);
                board.move(move);
                System.out.println(board);
            }
        }
    }
}


