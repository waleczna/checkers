package com.kodilla.checkers;

import com.kodilla.checkers.ai.Move;
import java.util.ArrayList;
import java.util.List;

public class Board {
    private List<BoardRow> rows = new ArrayList<>();
    private FigureColor whoseMove = FigureColor.WHITE;

    public Board() {
        for (int n = 0; n < 8; n++) {
            rows.add(new BoardRow());
        }
    }

    public FigureColor getWhoseMove() {
        return whoseMove;
    }

    public Figure getFigure(int col, int row) {
        return rows.get(row).getCols().get(col);
    }

    public void setFigure(int col, int row, Figure figure) {
        rows.get(row).getCols().set(col, figure);
    }

    public boolean move(Move move) {
        return move(move.getCol1(), move.getRow1(), move.getCol2(), move.getRow2());
    }

    public boolean move(int col1, int row1, int col2, int row2) {
        if (getFigure(col1, row1) instanceof Pawn)
            return pawnMove(col1, row1, col2, row2);
        else if (getFigure(col1, row1) instanceof Queen)
            return queenMove(col1, row1, col2, row2);
        else
            return false;
    }

    private boolean queenMove(int col1, int row1, int col2, int row2) {
        boolean isHit = false;
        boolean result = true;
        result = result && istColorCorrect(whoseMove, getFigure(col1, row1).getColor());
        result = result && isMoveDiagonal(col1, row1, col2, row2);
        result = result && isTargetFieldEmpty(col2, row2);
        result = result && isPositionInsideBoard(col2, row2);
        if (isPathClear(col1, row1, col2, row2)) {
            result = true;
        } else {
            result = result && isQueenHit(col1, row1, col2, row2);
            if (result)
                isHit = true;
        }
        if (result) {
            Figure figure = getFigure(col1, row1);
            setFigure(col2, row2, figure);
            setFigure(col1, row1, new None());
            if (isHit) {
                remoweHittedFigure(col1, row1, col2, row2);
            }
        }
        return result;
    }

    private void remoweHittedFigure(int col1, int row1, int col2, int row2) {
        int deltaCol = (col2 > col1) ? 1 : -1;
        int deltaRow = (row2 > row1) ? 1 : -1;
        int col = col1 + (Math.abs(col1 - col2) - 1) * deltaCol;
        int row = row1 + (Math.abs(row1 - row2) - 1) * deltaRow;
        setFigure(col, row, new None());
    }

    private boolean isQueenHit(int col1, int row1, int col2, int row2) {
        int deltaCol = (col2 > col1) ? 1 : -1;
        int deltaRow = (row2 > row1) ? 1 : -1;
        boolean result = true;
        for (int n = 1; n <= Math.abs(col1 - col2) - 2; n++) {
            int col = col1 + n * deltaCol;
            int row = row1 + n * deltaRow;
            result = result && (getFigure(col, row) instanceof None);
        }
        int col = col1 + (Math.abs(col1 - col2) - 1) * deltaCol;
        int row = row1 + (Math.abs(row1 - row2) - 1) * deltaRow;
        result = result && isOppositeFigure(whoseMove, col, row);
        return result;
    }

    private boolean isOppositeFigure(FigureColor color, int col, int row) {
        return getFigure(col, row).getColor() == oppositeColor(color);
    }

    private boolean isPathClear(int col1, int row1, int col2, int row2) {
        int deltaCol = (col2 > col1) ? 1 : -1;
        int deltaRow = (row2 > row1) ? 1 : -1;
        boolean result = true;
        for (int n = 1; n <= Math.abs(col1 - col2) - 1; n++) {
            int col = col1 + n * deltaCol;
            int row = row1 + n * deltaRow;
            result = result && (getFigure(col, row) instanceof None);
        }
        return result;
    }

    private boolean pawnMove(int col1, int row1, int col2, int row2) {
        boolean isHit = false;
        boolean result = true;
        result = result && istColorCorrect(whoseMove, getFigure(col1, row1).getColor());
        result = result && isMoveDiagonal(col1, row1, col2, row2);
        result = result && isTargetFieldEmpty(col2, row2);
        result = result && isPositionInsideBoard(col2, row2);
        if (result && isMoveByTwo(col1, col2)) {
            result = result && isOpponentBetween(oppositeColor(whoseMove), col1, row1, col2, row2);
            if (result)
                isHit = true;
        } else if (result) {
            result = result && isDirectionCorrect(whoseMove, row1, row2);
            result = result && isMoveByOne(col1, col2);
        }
        if (result) {
            Figure figure = getFigure(col1, row1);
            setFigure(col2, row2, figure);
            setFigure(col1, row1, new None());
            if (isHit) {
                remoweFigureBetween(col1, row1, col2, row2);
            }
            if (row2 == 0 || row2 == 7) {
                switchPawnToQueen(col2, row2);
            }
            whoseMove = oppositeColor(whoseMove);
        }
        return result;
    }

    private boolean isPositionInsideBoard(int col, int row) {
        return col >= 0 && col < 8 && row >= 0 && row < 8;
    }

    private void switchPawnToQueen(int col, int row) {
        FigureColor color = getFigure(col, row).getColor();
        setFigure(col, row, new Queen(color));
    }

    private void remoweFigureBetween(int col1, int row1, int col2, int row2) {
        int deltaCol = (col2 > col1) ? 1 : -1;
        int deltaRow = (row2 > row1) ? 1 : -1;
        setFigure(col1 + deltaCol, row1 + deltaRow, new None());
    }

    private boolean isOpponentBetween(FigureColor expectedFigureColor, int col1, int row1, int col2, int row2) {
        int deltaCol = (col2 > col1) ? 1 : -1;
        int deltaRow = (row2 > row1) ? 1 : -1;
        return getFigure(col1 + deltaCol, row1 + deltaRow).getColor() == expectedFigureColor;
    }

    private boolean isMoveByTwo(int col1, int col2) {
        return Math.abs(col1 - col2) == 2;
    }

    private boolean isTargetFieldEmpty(int col2, int row2) {
        return getFigure(col2, row2) instanceof None;
    }

    private boolean isMoveDiagonal(int col1, int row1, int col2, int row2) {
        return Math.abs(col1 - col2) == Math.abs(row1 - row2);
    }

    private boolean isMoveByOne(int col1, int col2) {
        return Math.abs(col1 - col2) == 1;
    }

    private boolean isDirectionCorrect(FigureColor whoseMove, int row1, int row2) {
        if (whoseMove == FigureColor.WHITE)
            return row2 > row1;
        else
            return row1 > row2;
    }

    private boolean istColorCorrect(FigureColor whoseMove, FigureColor figureColor) {
        return whoseMove == figureColor; //
    }

    private FigureColor oppositeColor(FigureColor color) {
        return (color == FigureColor.WHITE) ? FigureColor.BLACK : FigureColor.WHITE; //
    }

    @Override
    public String toString() {
        String s = "|--|--|--|--|--|--|--|--|\n";
        for (int row = 0; row < 8; row++) {
            s += rows.get(row).toString();
        }
        s += "|--|--|--|--|--|--|--|--|\n";
        return s;
    }

    public void initBoard() {
        for (int col = 0; col < 8; col += 2)
            setFigure(col, 0, new Pawn(FigureColor.WHITE));
        for (int col = 1; col < 8; col += 2)
            setFigure(col, 1, new Pawn(FigureColor.WHITE));
        for (int col = 0; col < 8; col += 2)
            setFigure(col, 6, new Pawn(FigureColor.BLACK));
        for (int col = 1; col < 8; col += 2)
            setFigure(col, 7, new Pawn(FigureColor.BLACK));
    }

    public Board deepCopy() {
        Board newBoard = new Board();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Figure figure = getFigure(col, row);
                if (figure instanceof Pawn) {
                    newBoard.setFigure(col, row, new Pawn(figure.getColor()));
                } else if (figure instanceof Queen) {
                    newBoard.setFigure(col, row, new Queen(figure.getColor()));
                }
            }
        }
        newBoard.whoseMove = whoseMove;
        return newBoard;
    }
}

