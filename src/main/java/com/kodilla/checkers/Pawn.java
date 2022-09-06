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

        return (color == FigureColor.WHITE) ? "w" : "b"; //if else: zwróć kolor = white, po znaku ? jeżeli prawda to (w), jeżeli fałsz to (b)

        /*
        if (color == FigureColor.WHITE)
            return "w";
        else
            return "b";
        mozna to napisac krocej jw
        */

    }
}
