package fr.quantumadventure.entity;

import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Entity {
    private static final double DEFAULT_SPEED = 200;

    public Player(double x, double y) {
        super(x, y, 32, 32, Color.BLUE);
        this.speed = DEFAULT_SPEED;
        this.active = true;
    }

    public void moveUp(double deltaTime) {
        this.y -= this.speed * deltaTime;
    }

    public void moveDown(double deltaTime) {
        this.y += this.speed * deltaTime;
    }

    public void moveLeft(double deltaTime) {
        this.x -= this.speed * deltaTime;
    }

    public void moveRight(double deltaTime) {
        this.x += this.speed * deltaTime;
    }

    public void stop() {

    }

    public void update() {
        updateViewPosition();
    }

    @Override
    public void update(double deltaTime) {
        super.update(deltaTime);
    }

    public Bounds getBounds() {
        return this.view.getBoundsInParent();
    }

    @Override
    public Rectangle getView() {
        return this.view;
    }
}