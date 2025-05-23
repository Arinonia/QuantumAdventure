package fr.quantumadventure;

import fr.quantumadventure.entity.Player;
import fr.quantumadventure.input.KeyInputManager;
import fr.quantumadventure.manager.CollisionManager;
import fr.quantumadventure.manager.TileManager;
import fr.quantumadventure.tile.Tile;
import fr.quantumadventure.tile.TileMap;
import fr.quantumadventure.tile.TileType;
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
    private final Pane viewport;
    private final Pane gamePane;

    private final KeyInputManager keyInputManager;
    private final CollisionManager collisionManager;
    private final TileManager tileManager;
    private final Player player;
    private final WorldManager worldManager;
    private TileMap currentMap;
    private final Camera camera;

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
        this.tileManager = new TileManager(this.gamePane);

        this.worldManager = new WorldManager();
        this.worldManager.initMaps();

        this.currentMap = this.tileManager.loadMap("waves");
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

        if (this.keyInputManager.isKeyPressed(KeyCode.D)) {
            this.player.moveRight(deltaTime);
        }
        if (this.keyInputManager.isKeyPressed(KeyCode.Q)) {
            this.player.moveLeft(deltaTime);
        }

        if (this.collisionManager.checkCollision(this.player)) {
            this.player.setPosition(oldX, this.player.getY());
        }

        if (this.keyInputManager.isKeyPressed(KeyCode.Z)) {
            this.player.moveUp(deltaTime);
        }
        if (this.keyInputManager.isKeyPressed(KeyCode.S)) {
            this.player.moveDown(deltaTime);
        }

        if (this.collisionManager.checkCollision(this.player)) {
            this.player.setPosition(this.player.getX(), oldY);
        }

        if (!this.keyInputManager.isKeyPressed(KeyCode.D) &&
                !this.keyInputManager.isKeyPressed(KeyCode.Q) &&
                !this.keyInputManager.isKeyPressed(KeyCode.Z) &&
                !this.keyInputManager.isKeyPressed(KeyCode.S)) {
            this.player.stop();
        }

        if (this.tileManager.checkSpecialTiles(player)) {
            Tile currentTile = getCurrentTileAtPlayerCenter();

            if (currentTile != null && currentTile.getType() == TileType.PORTAL) {
                TileMap newMap = this.tileManager.changeMap(player);
                this.collisionManager.setCurrentMap(newMap);
                this.currentMap = newMap;
            }
        }

        this.player.update();

        this.camera.update(this.player.getX(), this.player.getY(),
                this.currentMap.getPixelWidth(), this.currentMap.getPixelHeight());
    }

    private Tile getCurrentTileAtPlayerCenter() {
        double centerX = player.getX() + player.getWidth() / 2;
        double centerY = player.getY() + player.getHeight() / 2;

        int tileX = (int) (centerX / Tile.TILE_SIZE);
        int tileY = (int) (centerY / Tile.TILE_SIZE);

        return tileManager.getCurrentMap().getTile(tileX, tileY);
    }

    public void start() {
        this.stage.show();
        this.gameLoop.start();
    }
}