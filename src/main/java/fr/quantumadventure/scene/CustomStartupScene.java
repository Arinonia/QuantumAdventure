package fr.quantumadventure.scene;

import com.almasb.fxgl.app.scene.StartupScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CustomStartupScene extends StartupScene {
    public CustomStartupScene(int appWidth, int appHeight) {
        super(appWidth, appHeight);

        final Rectangle bg = new Rectangle(appWidth, appHeight, Color.BLACK);

        final Text title = new Text("QUANTUM ADVENTURE");
        title.setFont(Font.font(48));
        title.setFill(Color.WHITE);
        title.setTranslateX((appWidth - title.getLayoutBounds().getWidth()) / 2);
        title.setTranslateY(appHeight / 2 - 50);

        final Text loading = new Text("Chargement...");
        loading.setFont(Font.font(24));
        loading.setFill(Color.GRAY);
        loading.setTranslateX((appWidth - loading.getLayoutBounds().getWidth()) / 2);
        loading.setTranslateY(appHeight / 2 + 20);

        this.getContentRoot().getChildren().addAll(bg, title, loading);
    }
}