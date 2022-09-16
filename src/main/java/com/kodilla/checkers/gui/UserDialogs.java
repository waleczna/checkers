package com.kodilla.checkers.gui;

import com.kodilla.checkers.ai.Move;
import java.util.Scanner;

public class UserDialogs {

    public static Move getNextMove() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter next move 'c1,r1,c2,r2' :");
            String s = scanner.nextLine();
            String[] cords = s.split(",");
            try {
                int col1 = Integer.parseInt(cords[0]);
                int row1 = Integer.parseInt(cords[1]);
                int col2 = Integer.parseInt(cords[2]);
                int row2 = Integer.parseInt(cords[3]);
                if (col1 < 0 || col1 > 7 || col2 < 0 || col2 > 7 || row1 < 0 || row1 > 7 || row2 < 0 || row2 > 7)
                System.out.println("Wrong move, train again.");
                else
                    return new Move(col1, row1, col2, row2);
            } catch (Exception e) {
                System.out.println("Wrong move, train again.");
            }
        }
    }
}
