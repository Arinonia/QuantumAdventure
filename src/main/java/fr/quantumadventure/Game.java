package fr.quantumadventure;

import fr.quantumadventure.input.KeyInputManager;
import fr.quantumadventure.manager.CollisionManager;
import fr.quantumadventure.tile.Tile;
import fr.quantumadventure.tile.TileMap;
import fr.quantumadventure.tile.WorldManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.InputStream;

public class Game {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String TITLE = "Quantum Adventure";

    private final Stage stage;
    private final Scene scene;
    private final Pane root;
    private Pane viewport;
    private Pane gamePane;

    private final KeyInputManager keyInputManager;
    private final CollisionManager collisionManager;

    private final Player player;
    private WorldManager worldManager;
    private TileMap currentMap;
    private Camera camera;
    private int score = 0;

    private AnimationTimer gameLoop;

    public Game(Stage stage) {
        this.stage = stage;
        this.root = new Pane();
        this.viewport = new Pane();
        this.gamePane = new Pane();
        this.scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);
        this.keyInputManager = new KeyInputManager(this.scene);

        this.stage.setTitle(TITLE);
        this.stage.setResizable(false);
        this.stage.setScene(scene);

        final InputStream iconStream = getClass().getResourceAsStream("/images/icon.png");
        if (iconStream != null) {
            this.stage.getIcons().add(new Image(iconStream));
        } else {
            System.err.println("Icon not found");
        }

        this.viewport.setPrefSize(WIDTH, HEIGHT);
        this.viewport.getChildren().add(this.gamePane);
        this.root.getChildren().add(this.viewport);

        this.collisionManager = new CollisionManager();

        this.worldManager = new WorldManager();
        this.worldManager.initMaps();

        this.currentMap = this.worldManager.loadMap("waves");
        this.gamePane.getChildren().add(this.currentMap.getView());
        this.collisionManager.setCurrentMap(this.currentMap);

        this.player = new Player(32, 32);
        this.gamePane.getChildren().add(this.player.getView());

        this.camera = new Camera(this.gamePane, this.viewport, WIDTH, HEIGHT);

        createGameLoop();
    }


    private void createGameLoop() {
        this.gameLoop = new AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                if (this.lastUpdate == 0) {
                    this.lastUpdate = now;
                    return;
                }

                double deltaTime = (now - this.lastUpdate) / 1_000_000_000.0;
                this.lastUpdate = now;

                update(deltaTime);
            }
        };
    }

    private void update(double deltaTime) {
        double oldX = this.player.getX();
        double oldY = this.player.getY();

        double newX = oldX;
        double newY = oldY;

        boolean movedX = false;
        boolean movedY = false;

        if (this.keyInputManager.isKeyPressed(KeyCode.D)) {
            newX += this.player.getSpeed() * deltaTime;
            movedX = true;
        }
        if (this.keyInputManager.isKeyPressed(KeyCode.Q)) {
            newX -= this.player.getSpeed() * deltaTime;
            movedX = true;
        }

        if (movedX) {
            this.player.setPosition(newX, oldY);
            if (this.collisionManager.checkCollision(this.player)) {
                this.player.setPosition(oldX, oldY);
                newX = oldX;
            }
        }

        if (this.keyInputManager.isKeyPressed(KeyCode.Z)) {
            newY -= this.player.getSpeed() * deltaTime;
            movedY = true;
        }
        if (this.keyInputManager.isKeyPressed(KeyCode.S)) {
            newY += this.player.getSpeed() * deltaTime;
            movedY = true;
        }

        if (movedY) {
            this.player.setPosition(newX, newY);
            if (this.collisionManager.checkCollision(this.player)) {
                this.player.setPosition(newX, oldY);
                newY = oldY;// useless for now
            }
        }

        if (!movedX && !movedY) {
            this.player.stop();
        }

        checkSpecialTiles();

        this.player.update();

        this.camera.update(this.player.getX(), this.player.getY(),
                this.currentMap.getPixelWidth(), this.currentMap.getPixelHeight());
    }

    private void checkSpecialTiles() {
        double centerX = this.player.getX() + this.player.getWidth() / 2;
        double centerY = this.player.getY() + this.player.getHeight() / 2;

        int tileX = (int) (centerX / Tile.TILE_SIZE);
        int tileY = (int) (centerY / Tile.TILE_SIZE);

        Tile currentTile = this.currentMap.getTile(tileX, tileY);
        if (currentTile != null) {
            switch (currentTile.getType()) {
                case COLLECTIBLE:
                    collectItem(tileX, tileY);
                    break;
                case PORTAL:
                    changeMap();
                    break;
            }
        }
    }

    private void collectItem(int x, int y) {
        String currentMapId = this.worldManager.getCurrentMapId();
        if ("waves".equals(currentMapId)) {
            this.currentMap.setTile(x, y, Tile.createGrass());
        } else if ("particles".equals(currentMapId)) {
            this.currentMap.setTile(x, y, Tile.createSand());
        } else {
            this.currentMap.setTile(x, y, Tile.createGrass());
        }

        this.score += 10;
    }

    private void changeMap() {
        String currentMapId = this.worldManager.getCurrentMapId();
        String nextMapId = "waves";

        if ("waves".equals(currentMapId)) {
            nextMapId = "particles";
        } else if ("particles".equals(currentMapId)) {
            nextMapId = "waves";
        }

        this.gamePane.getChildren().remove(this.currentMap.getView());
        this.currentMap = this.worldManager.loadMap(nextMapId);
        this.collisionManager.setCurrentMap(this.currentMap);
        this.gamePane.getChildren().add(this.currentMap.getView());
        this.player.setPosition(100, 100);
        this.player.getView().toFront();
    }

    public void start() {
        this.stage.show();
        this.gameLoop.start();
    }
}