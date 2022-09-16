package com.kodilla.checkers;

public class Pawn implements Figure {

    private FigureColor color;

    public Pawn(FigureColor color) {
        this.color = color;
    }

    @Override
    public FigureColor getColor() {
        return color;
    }

    @Override
    public String toString() {
        return getColorSymbol() + "P";
    }

    private String getColorSymbol() {

        return (color == FigureColor.WHITE) ? "w" : "b";
    }
}
