package fr.quantumadventure.scene;

import com.almasb.fxgl.app.scene.StartupScene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MyStartupScene extends StartupScene {

    // Note: in startup scene no services are ready, so don't call FXGL.*
    public MyStartupScene(int appWidth, int appHeight) {
        super(appWidth, appHeight);

        Rectangle bg = new Rectangle(appWidth, appHeight);

        Text textCompanyName = new Text("Company Name");
        textCompanyName.setFill(Color.WHITE);
        textCompanyName.setFont(Font.font(64));

        getContentRoot().getChildren().addAll(new StackPane(bg, textCompanyName));
    }
}
