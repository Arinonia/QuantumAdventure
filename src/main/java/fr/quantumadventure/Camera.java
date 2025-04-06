package fr.quantumadventure;

import javafx.scene.layout.Pane;

public class Camera {
    private final Pane gamePane;
    private final Pane viewport;
    private final double viewportWidth;
    private final double viewportHeight;

    public Camera(Pane gamePane, Pane viewport, double viewportWidth, double viewportHeight) {
        this.gamePane = gamePane;
        this.viewport = viewport;
        this.viewportWidth = viewportWidth;
        this.viewportHeight = viewportHeight;

        this.viewport.setClip(new javafx.scene.shape.Rectangle(viewportWidth, viewportHeight));
    }

    public void update(double playerX, double playerY, double worldWidth, double worldHeight) {
        double targetX = -playerX + this.viewportWidth / 2;
        double targetY = -playerY + this.viewportHeight / 2;


        if (worldWidth < this.viewportWidth) {
            targetX = (this.viewportWidth - worldWidth) / 2;
        } else {
            if (targetX > 0) targetX = 0;
            if (targetX < this.viewportWidth - worldWidth) targetX = this.viewportWidth - worldWidth;
        }

        if (worldHeight < this.viewportHeight) {
            targetY = (this.viewportHeight - worldHeight) / 2;
        } else {
            if (targetY > 0) targetY = 0;
            if (targetY < this.viewportHeight - worldHeight) targetY = this.viewportHeight - worldHeight;
        }

        this.gamePane.setTranslateX(targetX);
        this.gamePane.setTranslateY(targetY);
    }
}