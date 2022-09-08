package com.kodilla.checkers;

import com.kodilla.checkers.ai.AI;
import com.kodilla.checkers.ai.Move;
import com.kodilla.checkers.gui.UserDialogs;

class TestApp {
    public static void main(String[] args) {
        Board board = new Board(); //tworzy nowy obiekt board
        board.initBoard();  //tworzy pusta plansze
        System.out.println(board); //wyswietlenie plansz
        while (true) { //petla nieskonczona do symulacji gry
            if (board.getWhoseMove() == FigureColor.WHITE) { //w tej petli chcemy wykonac ponizsze DWIE LINIJKI jezeli ruch jest BIALYCH czyli gracza
                Move move = UserDialogs.getNextMove(); //chcemy pytac uzytkownika o ruchy, jest to odczytanie ruchu
                board.move(move); //wywolanie metody ruch na planszy
            } else { //w przeciwnym wypadku jezeli ruch jest po stronie ai to ai musi wykorzystac sztuczna inteligencje
                Move move = AI.getBestBlackMove(board);
                //zakladamy, ze komputer zawsze gra czarnymi i jako parametr musimy przekazac plansze, nie pytamy tu o nic, komputer sam ma cos zorbic
                board.move(move);
            System.out.println(board); //wyswietlenie planszy

            //board.setFigure(2, 2, new Pawn(FigureColor.WHITE)); //USUNIETE PO NAPISANIU DRUGIEGO RUCHU
            //board.setFigure(5, 1, new Queen(FigureColor.BLACK)); //jw
            //System.out.println(board); // jw
            //board.move(2, 2, 3, 3); // jw
            // tu poczatkowo byla inicjacja Board, przeniesiona wyzej podczas ai
            //System.out.println(board);
            //System.out.println(board.getWhoseMove());
            //System.out.println(board.move(1,1,2,2)); // ruch poprawny
            //System.out.println(board);
            //System.out.println(board.getWhoseMove());
            //System.out.println(board.move(2,2,4,7)); // ruch niemożliwy - pozbawiony sensu - dla sprawdzenia
            //System.out.println(board);
            //System.out.println(board.getWhoseMove());
        }
    }
}
