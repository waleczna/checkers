package com.kodilla.checkers;

public class Queen implements Figure {
    private FigureColor color;

    public Queen(FigureColor color) {
        this.color = color;
    }

    @Override
    public FigureColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return getColorSymbol() + "Q";
    }

    private String getColorSymbol() {
        return (color == FigureColor.WHITE) ? "w" : "b";
    }
}
