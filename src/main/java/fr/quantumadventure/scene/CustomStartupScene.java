package fr.quantumadventure.scene;

import com.almasb.fxgl.app.scene.StartupScene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CustomStartupScene extends StartupScene {
    public CustomStartupScene(int appWidth, int appHeight) {
        super(appWidth, appHeight);

        // Crée un arrière-plan
        Rectangle bg = new Rectangle(appWidth, appHeight, Color.BLACK);

        // Ajoute ton logo ou un texte
        Text title = new Text("QUANTUM ADVENTURE");
        title.setFont(Font.font(48));
        title.setFill(Color.WHITE);
        title.setTranslateX((appWidth - title.getLayoutBounds().getWidth()) / 2);
        title.setTranslateY(appHeight / 2 - 50);

        // Ajoute un texte supplémentaire
        Text loading = new Text("Chargement...");
        loading.setFont(Font.font(24));
        loading.setFill(Color.GRAY);
        loading.setTranslateX((appWidth - loading.getLayoutBounds().getWidth()) / 2);
        loading.setTranslateY(appHeight / 2 + 20);

        // Ajoute ces éléments à la scène
        getContentRoot().getChildren().addAll(bg, title, loading);

        // Définit la durée d'affichage (en secondes)
        //set(Duration.seconds(2.5));
    }
}