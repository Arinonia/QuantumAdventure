package fr.quantumadventure.ui;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameHUD extends VBox {
    private final Text scoreText;
    private final HealthBar healthBar;

    public GameHUD() {
        this.scoreText = new Text("Score: 0");
        this.scoreText.setFont(Font.font(18));
        this.scoreText.setFill(Color.WHITE);

        this.healthBar = new HealthBar();

        final HBox topBar = new HBox(20);
        topBar.getChildren().addAll(this.scoreText, this.healthBar);

        topBar.setTranslateX(15);
        topBar.setTranslateY(15);

        this.getChildren().add(topBar);
    }

    public void updateScore(final int score) {
        this.scoreText.setText("Score: " + score);
    }

    public void updateHealth(final int currentHealth, final int maxHealth) {
        this.healthBar.update(currentHealth, maxHealth);
    }

    private static class HealthBar extends StackPane {
        private Rectangle background;
        private Rectangle bar;
        private Text text;

        public HealthBar() {
            this.background = new Rectangle(150, 20, Color.RED.deriveColor(0, 1, 1, 0.3));
            this.bar = new Rectangle(150, 20, Color.RED);

            this.text = new Text("100/100");
            this.text.setFont(Font.font(12));
            this.text.setFill(Color.WHITE);

            this.getChildren().addAll(this.background, this.bar, this.text);
        }

        public void update(final int currentHealth, final int maxHealth) {
            final double percentage = (double) currentHealth / maxHealth;
            this.bar.setWidth(150 * percentage);
            this.text.setText(currentHealth + "/" + maxHealth);
        }
    }
}
