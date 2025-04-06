package fr.quantumadventure;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player {
    private static final double SPEED = 200;

    private double x;
    private double y;
    private double width = 32;
    private double height = 32;

    private Rectangle view;

    public Player(double x, double y) {
        this.x = x;
        this.y = y;

        this.view = new Rectangle(this.width, this.height, Color.BLUE);

        updateViewPosition();
    }

    public void moveUp(double deltaTime) {
        this.y -= SPEED * deltaTime;
    }

    public void moveDown(double deltaTime) {
        this.y += SPEED * deltaTime;
    }

    public void moveLeft(double deltaTime) {
        this.x -= SPEED * deltaTime;
    }

    public void moveRight(double deltaTime) {
        this.x += SPEED * deltaTime;
    }

    public void update() {
        updateViewPosition();
    }

    private void updateViewPosition() {
        this.view.setTranslateX(x);
        this.view.setTranslateY(y);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        updateViewPosition();
    }

    public Rectangle getView() {
        return this.view;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public Bounds getBounds() {
        return this.view.getBoundsInParent();
    }
}