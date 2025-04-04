package fr.quantumadventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.level.tiled.TMXLevelLoader;
import com.almasb.fxgl.entity.level.tiled.TiledMap;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import fr.quantumadventure.components.CameraComponent;
import fr.quantumadventure.components.PlayerComponent;
import fr.quantumadventure.entity.GameEntityFactory;
import fr.quantumadventure.entity.PlayerFactory;
import fr.quantumadventure.ui.GameHUD;
import fr.quantumadventure.utils.Constants;
import fr.quantumadventure.utils.EntityType;
import fr.quantumadventure.utils.logger.Logger;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication {
    private static final Logger log = Logger.getLogger(Game.class);
    private Entity player;
    private PlayerComponent playerComponent;
    private CameraComponent cameraComponent;
    private boolean isChangingLevel = false;

    @Override
    protected void initSettings(final GameSettings settings) {
        settings.setWidth(Constants.GAME_WIDTH);
        settings.setHeight(Constants.GAME_HEIGHT);
        settings.setTitle(Constants.GAME_NAME);
        settings.setVersion(Constants.GAME_VERSION);
        settings.setMainMenuEnabled(true);
        settings.setGameMenuEnabled(true);
        settings.setFullScreenAllowed(true);
        settings.setDeveloperMenuEnabled(true);
        settings.setAppIcon("images/icon.png");

        settings.setSceneFactory(new CustomSceneFactory());
    }

    @Override
    protected void initGame() {
        log.info("Initializing Game");

        getGameWorld().addEntityFactory(new GameEntityFactory());
        getGameWorld().addEntityFactory(new PlayerFactory());
        // Charge la map depuis les assets
//        var map = FXGL.getAssetLoader().loadLevel("maps/test.tmx", new TMXLevelLoader());
        var map = getAssetLoader().loadLevel("maps/test.tmx", new TMXLevelLoader());

        // Applique la carte au monde du jeu
//        FXGL.getGameWorld().setLevel(map);
        getGameWorld().setLevel(map);

//        getGameWorld().addEntityFactory(new WorldFactory());
//        getGameWorld().addEntityFactory(new TileFactory());

//        this.tileLevelManager = new TileLevelManager();
//        this.tileLevelManager.loadLevel("waves");

//        int levelWidth = this.tileLevelManager.getCurrentLevelWidth();
//        int levelHeight = this.tileLevelManager.getCurrentLevelHeight();

        createPlayer(100, 100, map.getWidth(), map.getHeight());

        getWorldProperties().setValue("score", 0);
    }

    private void createPlayer(final double x, final double y, final int levelWidth, final int levelHeight) {
        if (this.player != null) {
            this.player.removeFromWorld();
            log.info("Removing old player entity");
        }

        this.player = spawn("player", x, y);
        this.playerComponent = this.player.getComponent(PlayerComponent.class);

        this.cameraComponent = new CameraComponent(levelWidth, levelHeight);
        this.player.addComponent(this.cameraComponent);

        log.info("Player created at position: " + x + ", " + y);
    }

    private void changeLevel(final String levelId) {
        if (this.isChangingLevel) {
            log.warn("Already changing level, ignoring request");
            return;
        }

        this.isChangingLevel = true;
        log.info("Starting level change to: " + levelId);

        double playerX = this.player.getX();
        double playerY = this.player.getY();
        int currentScore = getWorldProperties().getInt("score");

        getGameScene().getViewport().fade(() -> {
            this.player.removeFromWorld();

//            this.tileLevelManager.loadLevel(levelId);

//            final int levelWidth = this.tileLevelManager.getCurrentLevelWidth();
//            final int levelHeight = this.tileLevelManager.getCurrentLevelHeight();

//            createPlayer(playerX, playerY, levelWidth, levelHeight);

            getWorldProperties().setValue("score", currentScore);

            log.info("Level changed to: " + levelId + ", player at: " + this.player.getX() + ", " + this.player.getY());

            getGameScene().getViewport().flash(() -> {
                this.isChangingLevel = false;
            });
        });
    }

    @Override
    protected void initInput() {
        final Input input = getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                if (playerComponent != null && !isChangingLevel) {
                    playerComponent.moveRight();
                }
            }

            @Override
            protected void onActionEnd() {
                if (playerComponent != null) {
                    playerComponent.stop();
                }
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                if (playerComponent != null && !isChangingLevel) {
                    playerComponent.moveLeft();
                }
            }

            @Override
            protected void onActionEnd() {
                if (playerComponent != null) {
                    playerComponent.stop();
                }
            }
        }, KeyCode.Q);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                if (playerComponent != null && !isChangingLevel) {
                    playerComponent.moveUp();
                }
            }

            @Override
            protected void onActionEnd() {
                if (playerComponent != null) {
                    playerComponent.stop();
                }
            }
        }, KeyCode.Z);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                if (playerComponent != null && !isChangingLevel) {
                    playerComponent.moveDown();
                }
            }

            @Override
            protected void onActionEnd() {
                if (playerComponent != null) {
                    playerComponent.stop();
                }
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Interact") {
            @Override
            protected void onActionBegin() {
                if (!isChangingLevel) {
                    log.info("Interacting with object");
                }
            }
        }, KeyCode.E);

        input.addAction(new UserAction("Special Ability") {
            @Override
            protected void onActionBegin() {
                if (!isChangingLevel) {
                    log.info("Using special ability");
                }
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 0);
        /*getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLATFORM) {
            @Override
            protected void onCollisionBegin(final Entity player, final Entity obstacle) {
                log.info("Player collided with platform/obstacle");
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.ITEM) {
            @Override
            protected void onCollisionBegin(final Entity player, final Entity collectible) {
                collectible.removeFromWorld();
                inc("score", +10);
                log.info("Collected item, score increased by 10");
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PORTAL) {
            private boolean portalCooldown = false;
            private long lastPortalUseTime = 0;

            @Override
            protected void onCollisionBegin(final Entity player, final Entity portal) {
                if (isChangingLevel) {
                    return;
                }

                final long currentTime = System.currentTimeMillis();
                final long PORTAL_COOLDOWN_MS = 2000;
                if (this.portalCooldown && (currentTime - this.lastPortalUseTime < PORTAL_COOLDOWN_MS)) {
                    return;
                }

                this.portalCooldown = true;
                this.lastPortalUseTime = currentTime;

//                final String currentLevel = tileLevelManager.getCurrentLevelId();
//                String nextLevel = "waves";
//                if ("waves".equals(currentLevel)) {
//                    nextLevel = "particles";
//                } else if ("particles".equals(currentLevel)) {
//                    nextLevel = "waves";
//                }
//
//                log.info("Player initiated level change from " + currentLevel + " to " + nextLevel);
//                changeLevel(nextLevel);
            }
        });*/

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.WALL) {
            @Override
            protected void onCollision(Entity player, Entity wall) {
                System.out.println("Le joueur touche un mur !");
            }
        });
    }

    @Override
    protected void initUI() {
        final GameHUD gameHUD = new GameHUD();

        addUINode(gameHUD);

        getip("score").addListener((obs, old, newValue) -> {
            gameHUD.updateScore(newValue.intValue());
        });

        set("score", 0);
    }
}