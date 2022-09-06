package com.kodilla.checkers;

class TestApp {
    public static void main(String[] args) {
        Board board = new Board();
        // board.setFigure(2, 2, new Pawn(FigureColor.WHITE)); // USUNIETE PO NAPISANIU DRUGIEGO RUCHU
        // board.setFigure(5, 1, new Queen(FigureColor.BLACK)); // jw
        // System.out.println(board); // jw
        // board.move(2, 2, 3, 3); // jw
        board.initBoard();
        System.out.println(board);
        System.out.println(board.getWhoseMove());
        System.out.println(board.move(1,1,2,2)); // ruch poprawny
        System.out.println(board);
        System.out.println(board.getWhoseMove());
        System.out.println(board.move(2,2,4,7)); // ruch niemożliwy - pozbawiony sensu - dla sprawdzenia
        System.out.println(board);
        System.out.println(board.getWhoseMove());
    }
}
