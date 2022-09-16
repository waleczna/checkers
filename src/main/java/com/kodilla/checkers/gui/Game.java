package com.kodilla.checkers.gui;

import com.kodilla.checkers.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javax.swing.*;

public class Game {
    private GridPane gridPane;
    private Board board;

    private int oldCol = -1;
    private int oldRow = -1;


    private final Image blackPawn = new Image("file:C:\\Users\\rycer\\Desktop\\checkers\\src\\main\\resources\\blackPawn.png");
    private final Image whitePawn = new Image("file:C:\\Users\\rycer\\Desktop\\checkers\\src\\main\\resources\\whitePawn.png");
    private final Image blackQueen = new Image("file:C:\\Users\\rycer\\Desktop\\checkers\\src\\main\\resources\\blackQueen.png");
    private final Image whiteQueen = new Image("file:C:\\Users\\rycer\\Desktop\\checkers\\src\\main\\resources\\whiteQueen.png");

    public Game(GridPane gridPane, Board board) {

        this.gridPane = gridPane;
        this.board = board;
    }

    public void displayOnGrid() {
        gridPane.getChildren().clear();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Figure figure = board.getFigure(col, row);
                Image image = null;
                if (figure instanceof Pawn) {
                    if (figure.getColor() == FigureColor.WHITE)
                        image = whitePawn;
                    else
                        image = blackPawn;
                } else if (figure instanceof Queen) {

                    if (figure.getColor() == FigureColor.WHITE)
                        image = whiteQueen;
                    else
                        image = blackQueen;
                }
                ImageView imageView = new ImageView(image);
                if (col == oldCol && row == oldRow) {
                    Rectangle rectangle = new Rectangle(114, 114, Color.RED);
                    gridPane.add(rectangle, col, row);
                }
                gridPane.add(imageView, col, row);
            }
        }
    }

    public void doClick(int col, int row) {
        if (oldCol == -1) {
            oldCol = col;
            oldRow = row;
            displayOnGrid();

        } else {
            board.move(oldCol, oldRow, col, row);
            oldCol = -1;
            oldRow = -1;
            displayOnGrid();
        }
    }
}
