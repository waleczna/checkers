package com.kodilla.checkers.gui;


import com.kodilla.checkers.Board;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class HelloJavaFX extends Application {
    private final Image bkg = new Image("file:C:\\Users\\rycer\\Desktop\\checkers\\src\\main\\resources\\boardCheckers.png");
    // wczytanie obrazka poporzez zmienna typu Image

    @Override
    public void start(Stage stage) throws /*URISyntaxException,*/ FileNotFoundException {
        BackgroundSize backgroundSize = new BackgroundSize(896, 896, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(bkg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        GridPane gridPane = new GridPane(); //wirtualna siatka dzielaca ekran na 8 czesci
        gridPane.setBackground(background);
        //GridPane centerField = new GridPane();
        //centerField.setBackground(background);
        //centerField.setMinSize(700,700); ???
        //centerField.setMaxSize(700,700); ???


        for (int n = 0; n < 8; n++) { //petla, ktora musi byc ograniczona poprzez wymiary tablicy czyli rzedy i linie
            gridPane.getColumnConstraints().add(new ColumnConstraints(112)); //ograniczenie constrains co 112 pikseli
            gridPane.getRowConstraints().add(new RowConstraints(112)); //inaczej wypelnienie tablicy rzedami co 112 pikseli
        }

        Board board = new Board(); //podpiecie planszy ale najpierw nowy obiekt w klasie, ktora musi byc public
        board.initBoard(); //metoda ustawienia poczatkowego figur i powiazanie board z gridPane poprzez utworzenie nowej klasy Game

        Game game = new Game(gridPane, board); //zamiar - utworzenie klasy Game poprzedzajace utworzenie obiektu Game
        game.displayOnGrid(); //wyswietl pionki na planszy

        gridPane.setOnMouseClicked(e -> {
            //obsluga zdarzenia klikniecia myszka EventHandler<?superMouseEvent>value czyli to, co po otwarciu lewego nawiasu
            //System.out.println(e.getX() + "," + e.getY()); - to pozwoli wyświetlić współrzędne w pikselach podczas klikania myszką na planszy
            int col = (int)e.getX() / 112; //przeliczenie wspolrzednych pokazywanych podczas klikania myszka na wspolrzedne kolumny i wiersza
            int row = (int)e.getY() / 112; //i wiersza; (int) jest rzutowaniem czyli pozbyciem sie czesci ulamkowej, ktora powstalaby z dzielenia
            //teraz podczas run podawane sa: numer kolumny i numer wiersza
            //System.out.println(col + "," + row); kasujemy to, bo idziemy dalej - w powiązaniu logiki z planszą
            game.doClick(col, row);
        });

        //String javaVersion = System.get.Property("java.version");
        //String javafxVersion = System.getProperty("javafx.version");
        //Label l = new Label("Hello, JavaFX' + javafxVersion + ", running on Java " + javaVersion + ",");

        Scene scene = new Scene(gridPane, 896, 896); //utworzenie sceny
        stage.setScene(scene); //przypisanie tej sceny do stage
        stage.show(); //wyswietlenie stage
    }

    public static void main(String[] args) {
        launch();
    }

}