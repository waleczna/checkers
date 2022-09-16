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

    @Override
    public void start(Stage stage) throws /*URISyntaxException*/ FileNotFoundException {
        BackgroundSize backgroundSize = new BackgroundSize(896, 896, false, false, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(bkg, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);
        GridPane gridPane = new GridPane();
        gridPane.setBackground(background);
        for (int n = 0; n < 8; n++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(112));
            gridPane.getRowConstraints().add(new RowConstraints(112));
        }
        Board board = new Board();
        board.initBoard();
        Game game = new Game(gridPane, board);
        game.displayOnGrid();
        gridPane.setOnMouseClicked(e -> {
            int col = (int)e.getX() / 112;
            int row = (int)e.getY() / 112;
            game.doClick(col, row);
        });
        Scene scene = new Scene(gridPane, 896, 896);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}