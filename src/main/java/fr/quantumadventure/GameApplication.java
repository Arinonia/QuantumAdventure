package fr.quantumadventure;

import javafx.application.Application;
import javafx.stage.Stage;

public class GameApplication extends Application {
    @Override
    public void start(Stage stage) {
        Game game = new Game(stage);
        game.start();
    }
}
