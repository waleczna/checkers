package com.kodilla.checkers.gui;

import com.kodilla.checkers.ai.Move;

import java.util.Scanner;

public class UserDialogs {

    public static Move getNextMove() {
        Scanner scanner = new Scanner(System.in); //czytamy z klawiatury
        while (true) { //robimy to tak dlugo az uda zrobic to poprawnie, petla nieskonczona
            System.out.println("Enter next move 'c1,r1,c2,r2' :"); //mamy wpisac cztery liczby oddzielone przecinkami
            String s = scanner.nextLine(); //odczytujemy z klawiatury
            String[] cords = s.split(","); //tablica stringow i dzielimy sobie ten tekst wzgledem przecinka i w kolejnych kolumnach beda elementy z c1, r1, c2, r2
            try {
                int col1 = Integer.parseInt(cords[0]);
                int row1 = Integer.parseInt(cords[1]);
                int col2 = Integer.parseInt(cords[2]);
                int row2 = Integer.parseInt(cords[3]);
                if (col1 < 0 || col1 > 7 || col2 < 0 || col2 > 7 || row1 < 0 || row1 > 7 || row2 < 0 || row2 > 7)   //jezeli ruch jest poza plansza
                System.out.println("Wrong move, train again.");
                else //w przeciwnym razie zwracamy wlasciwy ruch
                    return new Move(col1, row1, col2, row2);  //ten ruch sie wykona gdy wszystko bylo OK
            } catch (Exception e) {
                System.out.println("Wrong move, train again.");
            }
        }

    }
}
