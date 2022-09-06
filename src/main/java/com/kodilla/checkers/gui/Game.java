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

    public Game(GridPane gridPane, Board board) { //konstruktor

        this.gridPane = gridPane;
        this.board = board;
    }

    public void displayOnGrid() { //metoda do wyswietlania figurek na planszy w momencie uruchomienia gry
        gridPane.getChildren().clear(); //czyści pole po ruchu (czyszczenie i rysowanie)
        for (int row = 0; row < 8; row++) { //row rowne zero tak dlugo dopoki row jest mniejsze od 8
            for (int col = 0; col < 8; col++) { //petla, ktora chodzi po kolumnach
                Figure figure = board.getFigure(col, row);
                //zmienna figure, odczytuje sobie figure z klasy reprezentujacej logike z podanych wspolrzednych - get figure set figure wczesniej zrobione
                Image image = null;
                //jak juz jest figure, to na jej podstawie zostanie utworzy nowy obrazek i umieszczony w grid dlatego sciezke dostepu z HelloJavaFX nalezy skopiowac tu jw
                //jak sa zdefiniowane pola Image w klasie Game to po odczytaniu wlasciwego obrazka bedzie on wstawiany do grid
                if (figure instanceof Pawn) { //jezeli figura (w kazdej klasie publicznej) jest instancji klasy Pawn czyli jest pionkiem
                    if (figure.getColor() == FigureColor.WHITE) //sprawdz kolor tej figury i jezeli jest bialy
                        image = whitePawn; //to wyswietl bialy pionek
                    else
                        image = blackPawn;
                } else if (figure instanceof Queen) {

                    if (figure.getColor() == FigureColor.WHITE)
                        image = whiteQueen;
                    else
                        image = blackQueen;
                }
                //konstrukcja od 40 do 50 wybiera 1 z 4 obrazkow na podstawie typu i koloru i przypisuje go do zmiennej image
                ImageView imageView = new ImageView(image); //tworzony jest nowy obiekt i wstawiona jest zmienna image, ktora zostala wybrana w petli jw
                if (col == oldCol && row == oldRow) {
                    Rectangle rectangle = new Rectangle(114, 114, Color.RED); //nowy obiekt - czerwony kwadrat
                    gridPane.add(rectangle, col, row);
                }
                gridPane.add(imageView, col, row);
                //umieszczenie imageView na gridPane (child to imageView, col, row); i przechodzimy do obslugi klikania myszka HelloJavaFX
            }
        }
    }

    public void doClick(int col, int row) {
        if (oldCol == -1) { //zaznacza pole
            oldCol = col;
            oldRow = row;
            displayOnGrid();

        } else {
            board.move(oldCol, oldRow, col, row); //wykonuje ruch
            oldCol = -1;
            oldRow = -1;
            displayOnGrid(); //wyswietla plansze po wykonaniu ruchu
        }

    }
}
