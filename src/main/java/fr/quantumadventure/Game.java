package fr.quantumadventure;

import fr.quantumadventure.tile.Tile;
import fr.quantumadventure.tile.TileMap;
import fr.quantumadventure.tile.WorldManager;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class Game {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private static final String TITLE = "Quantum Adventure";

    private final Stage stage;
    private final Scene scene;
    private final Pane root;
    private Pane viewport;
    private Pane gamePane;

    private final Player player;
    private WorldManager worldManager;
    private TileMap currentMap;
    private Camera camera;
    private int score = 0;

    private final Map<KeyCode, Boolean> keys = new HashMap<>();

    private AnimationTimer gameLoop;

    public Game(Stage stage) {
        this.stage = stage;
        this.root = new Pane();
        this.viewport = new Pane();
        this.gamePane = new Pane();
        this.scene = new Scene(root, WIDTH, HEIGHT, Color.BLACK);

        this.stage.setTitle(TITLE);
        this.stage.setResizable(false);
        this.stage.setScene(scene);

        this.viewport.setPrefSize(WIDTH, HEIGHT);
        this.viewport.getChildren().add(this.gamePane);
        this.root.getChildren().add(this.viewport);

        this.root.getChildren().add(this.gamePane);

        this.worldManager = new WorldManager();
        this.worldManager.initMaps();

        this.currentMap = this.worldManager.loadMap("waves");
        this.gamePane.getChildren().add(this.currentMap.getView());

        this.player = new Player(100, 100);
        this.gamePane.getChildren().add(this.player.getView());

        this.camera = new Camera(this.gamePane, this.viewport, WIDTH, HEIGHT);

        setupInput();

        createGameLoop();
    }

    private void setupInput() {
        this.scene.setOnKeyPressed(e -> this.keys.put(e.getCode(), true));
        this.scene.setOnKeyReleased(e -> this.keys.put(e.getCode(), false));
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


        if (isKeyPressed(KeyCode.Z)) {
            this.player.moveUp(deltaTime);
        }
        if (isKeyPressed(KeyCode.S)) {
            this.player.moveDown(deltaTime);
        }
        if (isKeyPressed(KeyCode.Q)) {
            this.player.moveLeft(deltaTime);
        }
        if (isKeyPressed(KeyCode.D)) {
            this.player.moveRight(deltaTime);
        }

        handleTileCollisions(oldX, oldY);
        checkSpecialTiles();
        this.player.update();

        this.camera.update(this.player.getX(), this.player.getY(),
                this.currentMap.getPixelWidth(), this.currentMap.getPixelHeight());
    }

    private void handleTileCollisions(double oldX, double oldY) {
        double playerWidth = this.player.getWidth();
        double playerHeight = this.player.getHeight();

        double[][] points = {
                {this.player.getX(), this.player.getY()},
                {this.player.getX() + playerWidth - 1, this.player.getY()},
                {this.player.getX(), this.player.getY() + playerHeight - 1},
                {this.player.getX() + playerWidth - 1, this.player.getY() + playerHeight - 1}
        };

        boolean collision = false;
        for (double[] point : points) {
            int tileX = (int) (point[0] / Tile.TILE_SIZE);
            int tileY = (int) (point[1] / Tile.TILE_SIZE);

            if (!isTileWalkable(tileX, tileY)) {
                collision = true;
                break;
            }
        }
        if (collision) {
            this.player.setPosition(oldX, oldY);
        }
    }

    private boolean isTileWalkable(int tileX, int tileY) {
        if (tileX < 0 || tileX >= this.currentMap.getWidth() || tileY < 0 || tileY >= this.currentMap.getHeight()) {
            return false;
        }

        return this.currentMap.isWalkable(tileX, tileY);
    }

    private void checkSpecialTiles() {
        double centerX = player.getX() + player.getWidth() / 2;
        double centerY = player.getY() + player.getHeight() / 2;

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
            currentMap.setTile(x, y, Tile.createGrass());
        } else if ("particles".equals(currentMapId)) {
            currentMap.setTile(x, y, Tile.createSand());
        } else {
            currentMap.setTile(x, y, Tile.createGrass());
        }
        //currentMap.setTile(x, y, Tile.createGrass());

        this.score += 10;
        System.out.println("Objet collecté ! Score : " + this.score);
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
        this.gamePane.getChildren().add(this.currentMap.getView());
        this.player.setPosition(100, 100);
        this.player.getView().toFront();
    }

    private boolean isKeyPressed(KeyCode code) {
        return this.keys.getOrDefault(code, false);
    }

    public void start() {
        this.stage.show();
        this.gameLoop.start();
    }
}