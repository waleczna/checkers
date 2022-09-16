package com.kodilla.checkers.ai;

import com.kodilla.checkers.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AI {

    public static Move getBestBlackMove(Board board) {

        //1. tworzymy liste mozliwych ruchow
        List<Move> possibleMoves = new ArrayList<>();
        for (int col = 0; col < 8; col++)
            for (int row = 0; row < 8; row++)
                if (board.getFigure(col, row).getColor() == FigureColor.BLACK)
                    addPossibleMovesForFigure(board, col, row, possibleMoves);

        //2. dla kazdego z mozliwych ruchow obliczamy scoring planszy po ruchu
        Map<Move, Scoring> scoring = new HashMap<>();
        for (Move move : possibleMoves) {
            scoring.put(move, getScore(move, board));
        }

        //3. wybieramy najlepszy wynik i zwracamy
        Map.Entry<Move, Scoring> exampleScoring = scoring.entrySet().stream()
                .findFirst()
                .orElse(null);
        Move bestMove = exampleScoring.getKey();
        int delta = exampleScoring.getValue().getComputerScore() - exampleScoring.getValue().getHumanScore();
        for (Map.Entry<Move, Scoring> entry : scoring.entrySet()) {
            int newDelta = entry.getValue().getComputerScore() - entry.getValue().getHumanScore();
            if (newDelta > delta) {
                bestMove = entry.getKey();
                delta = newDelta;
            }
        }
        return bestMove;
    }

    private static Scoring getScore(Move move, Board originalBoard) {
        Board simulatedBoard = originalBoard.deepCopy();
        simulatedBoard.move(move);
        int humanScore = 0;
        int computerScore = 0;
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Figure figure = simulatedBoard.getFigure(col, row);
                if (figure instanceof Pawn) {
                    if (figure.getColor() == FigureColor.WHITE) {
                        humanScore += row + 1;
                    } else {
                        computerScore += 8 - row;
                    }
                } else if (figure instanceof Queen) {
                    if (figure.getColor() == FigureColor.WHITE)
                        humanScore += 30;
                    else
                        computerScore += 30;
                }
            }
        }
        return new Scoring(computerScore, humanScore);
    }

    private static void addPossibleMovesForFigure(Board board, int col, int row, List<Move> possibleMoves) {
        Figure figure = board.getFigure(col, row);
        if (figure instanceof Pawn) {
            maybeAddSimpleMoves(board, col, row, possibleMoves);
            maybeAddMovesWithHit(board, col, row, possibleMoves);
        } else if (figure instanceof Queen) {
        }
    }

    private static void maybeAddMovesWithHit(Board board, int col, int row, List<Move> possibleMoves) {
        FigureColor oppositeColor = board.getFigure(col, row).getColor() == FigureColor.WHITE ? FigureColor.BLACK : FigureColor.WHITE;
        try {
            if (board.getFigure(col - 2, row - 2) instanceof None && !(board.getFigure(col - 1, row - 1) instanceof None) && board.getFigure(col - 1, row - 1).getColor() == oppositeColor)
                possibleMoves.add(new Move(col, row, col - 2, row - 2));
        } catch (Exception e) {
        }
        try {
            if (board.getFigure(col + 2, row - 2) instanceof None && !(board.getFigure(col + 1, row - 1) instanceof None) && board.getFigure(col + 1, row - 1).getColor() == oppositeColor)
                possibleMoves.add(new Move(col, row, col + 2, row - 2));
        } catch (Exception e) {
        }
    }

    private static void maybeAddSimpleMoves(Board board, int col, int row, List<Move> possibleMoves) {
        try {
            if (board.getFigure(col - 1, row - 1) instanceof None)
                possibleMoves.add(new Move(col, row, col - 1, row - 1));
        } catch (Exception e) {
        }
        try {
            if (board.getFigure(col + 1, row - 1) instanceof None)
                possibleMoves.add(new Move(col, row, col + 1, row - 1));
        } catch (Exception e) {
        }
    }
}
