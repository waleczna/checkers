package com.kodilla.checkers.ai;

import com.kodilla.checkers.*;

import java.util.ArrayList;
import java.util.List;

public class AI { //tu jest miejsce do opisania zachowania ai
    public static Move getBestBlackMove(Board board) {
        //1. tworzymy liste mozliwych ruchow
        List<Move> possibleMoves = new ArrayList<>(); //bierzemy kazda czarna figure, sprawdzamy jakie ma mozliwe ruchy i dodajemy ja do tej listy
        for (int col = 0; col < 8; col++) //przechodzimy po kazdej kolumnie
            for (int row = 0; row < 8; row++) //przechodzimy po kazdym rzedzie
                if (board.getFigure(col, row).getColor() == FigureColor.BLACK) //jezeli wywolana figura z tablicy po kolumnach i rzedach odda kolor figury czarny to
                    addPossibleMovesForFigure(board, col, row, possibleMoves);
        //sprawdzamy jej wszystkie mozliwe ruchy na tablicy wg wspolrzednych figury i naszej listy do ktorej to cos ma dodac ruchy
        //po wykonaniu tej sekcji w tej liscie possibleMoves bedziemy mieli obiekty typu move zawierajace wszystkie ruchy wszystkich czarnych figur


        //2. dla kazdego z mozliwych ruchow obliczamy scoring planszy po ruchu

        //3. wybieramy najlepszy wynik i zwracamy

        return null;
    }

    private static void addPossibleMovesForFigure(Board board, int col, int row, List<Move> possibleMoves) {
        //metoda dostaje wspolrzedne konkretnej figury, wszystkie mozliwe ruchy i list do ktorej ma dodac wyniki
        Figure figure = board.getFigure(col, row);
        if (figure instanceof Pawn) {
            maybeAddSimpleMoves(board, col, row, possibleMoves);
            maybeAddMovesWithHit(board, col, row, possibleMoves);

        } else if (figure instanceof Queen) {
        }
    }

    private static void maybeAddSimpleMoves(Board board, int col, int row, List<Move> possibleMoves) { //biale ida od 0,1 w dol, a czarne od 7,6 do gory
        try { //ale mozemy miec taka sytuacje, ze wyjedziemy poza lewwy lub prawy margines wiec musimy obsluzyc wyjatek

            if (board.getFigure(col - 1, row - 1) instanceof None) //None - puste pole i dlatego mozemy dodac ten ruch
                //jezeli figura na tablicy porusza sie w kolumnie, po skosie, na lewo
                //i w rzedzie po skosie, do gory (zmniejszaja sie wartosci dlatego -1) to mozemy dodac taka figure do listy
                possibleMoves.add(new Move(col, row, col - 1, row - 1));
        } catch (Exception e) {
        }
        try {

            if (board.getFigure(col + 1, row - 1) instanceof None)
                //jezeli figura na tablicy porusza sie w kolumnie, po skosie, na prawo +1
                //i w rzedzie po skosie, do gory (zmniejszaja sie wartosci dlatego -1) to mozemy dodac taka figure do listy
                possibleMoves.add(new Move(col, row, col + 1, row - 1)); //dodajemy ruch w prawo do gory
        } catch (Exception e) {
        }
    }
}
