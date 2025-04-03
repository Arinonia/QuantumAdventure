package fr.quantumadventure.scene;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class CustomMainMenu extends FXGLMenu {

    public CustomMainMenu() {
        super(MenuType.MAIN_MENU);

        final Rectangle background = new Rectangle(getAppWidth(), getAppHeight(), Color.BLACK);

        final Text title = new Text("QUANTUM ADVENTURE");
        title.setFont(Font.font(48));
        title.setFill(Color.WHITE);
        title.setEffect(new DropShadow(10, Color.PURPLE));

        final VBox menuBox = new VBox(15);
        menuBox.setAlignment(Pos.CENTER);

        final Button btnPlay = createButton("JOUER");
        btnPlay.setOnAction(e -> fireNewGame());

        final Button btnOptions = createButton("OPTIONS");
        btnOptions.setOnAction(e -> {
            FXGL.getDialogService().showMessageBox("Les options seront disponibles bientôt !");
        });

        final Button btnCredits = createButton("CRÉDITS");
        btnCredits.setOnAction(e -> {
            FXGL.getDialogService().showMessageBox("Développé par Arinonia et Funny76");
        });

        final Button btnExit = createButton("QUITTER");
        btnExit.setOnAction(e -> fireExit());

        menuBox.getChildren().addAll(btnPlay, btnOptions, btnCredits, btnExit);

        final VBox content = new VBox(50);
        content.setAlignment(Pos.CENTER);
        content.setTranslateY(getAppHeight() / 4);
        content.getChildren().addAll(title, menuBox);

        this.getContentRoot().getChildren().addAll(background, content);
    }

    private Button createButton(final String text) {
        final Button button = new Button(text);
        button.setFont(Font.font(20));
        button.setPrefWidth(200);
        button.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white;");

        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: #7e57c2; -fx-text-fill: white;");
            button.setEffect(new DropShadow(10, Color.PURPLE));
        });

        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color: #3c3f41; -fx-text-fill: white;");
            button.setEffect(null);
        });

        return button;
    }
}