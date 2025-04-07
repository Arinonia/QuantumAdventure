package fr.quantumadventure.entity;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public abstract class Entity {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected double speed;
    protected boolean active;// for now we use bool, but we can use an enum for more states ex: ACTIVE INACTIVE, DEAD, etc

    protected Rectangle view;

    public Entity(double x, double y, double width, double height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.view = new Rectangle(width, height, color);
        updateViewPosition();
    }

    public void update(double deltaTime) {
        updateViewPosition();
    }

    protected void updateViewPosition() {
        this.view.setTranslateX(this.x);
        this.view.setTranslateY(this.y);
    }

    public void setPosition(double x, double y) {
        this.x = x;
        this.y = y;
        updateViewPosition();
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

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
        this.view.setVisible(active);
    }

    public Node getView() {
        return this.view;
    }

    public boolean intersects(Entity other) {
        return this.x < other.x + other.width &&
                this.x + this.width > other.x &&
                this.y < other.y + other.height &&
                this.y + this.height > other.y;
    }
}
