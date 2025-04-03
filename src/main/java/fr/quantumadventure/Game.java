package fr.quantumadventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import fr.quantumadventure.components.CameraComponent;
import fr.quantumadventure.components.PlayerComponent;
import fr.quantumadventure.entity.PlayerFactory;
import fr.quantumadventure.ui.GameHUD;
import fr.quantumadventure.utils.Constants;
import fr.quantumadventure.utils.EntityType;
import fr.quantumadventure.utils.logger.Logger;
import fr.quantumadventure.world.LevelManager;
import fr.quantumadventure.world.WorldFactory;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGL.*;

public class Game extends GameApplication {
    private static final Logger log = Logger.getLogger(Game.class);
    private Entity player;
    private PlayerComponent playerComponent;
    private CameraComponent cameraComponent;
    private LevelManager levelManager;

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

        getGameWorld().addEntityFactory(new PlayerFactory());
        getGameWorld().addEntityFactory(new WorldFactory());

        this.levelManager = new LevelManager();
        this.levelManager.loadLevel("waves");

        // Dimensions du niveau actuel
        int levelWidth = levelManager.getCurrentLevelWidth();
        int levelHeight = levelManager.getCurrentLevelHeight();

        // Création du joueur
        this.player = spawn("player", 100, 100);
        this.playerComponent = this.player.getComponent(PlayerComponent.class);

        // Ajout de la composante caméra au joueur
        this.cameraComponent = new CameraComponent(levelWidth, levelHeight);
        this.player.addComponent(this.cameraComponent);

        getWorldProperties().setValue("score", 0);
    }

    private void changeLevel(String levelId) {
        int currentScore = getWorldProperties().getInt("score");
        levelManager.loadLevel(levelId);

        // Mise à jour des dimensions du monde pour la caméra
        int levelWidth = levelManager.getCurrentLevelWidth();
        int levelHeight = levelManager.getCurrentLevelHeight();
        cameraComponent.setWorldDimensions(levelWidth, levelHeight);

        player.setPosition(100, 100);
        getWorldProperties().setValue("score", currentScore);
        log.info("Changed to level: " + levelId);
    }

    @Override
    protected void initInput() {
        final Input input = getInput();

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                playerComponent.moveRight();
            }

            @Override
            protected void onActionEnd() {
                playerComponent.stop();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                playerComponent.moveLeft();
            }

            @Override
            protected void onActionEnd() {
                playerComponent.stop();
            }
        }, KeyCode.Q);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                playerComponent.moveUp();
            }

            @Override
            protected void onActionEnd() {
                playerComponent.stop();
            }
        }, KeyCode.Z);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onAction() {
                playerComponent.moveDown();
            }

            @Override
            protected void onActionEnd() {
                playerComponent.stop();
            }
        }, KeyCode.S);

        input.addAction(new UserAction("Interact") {
            @Override
            protected void onActionBegin() {
                log.info("Interacting with object");
            }
        }, KeyCode.E);

        input.addAction(new UserAction("Special Ability") {
            @Override
            protected void onActionBegin() {
                log.info("Using special ability");
            }
        }, KeyCode.SPACE);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0, 0);
        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PLATFORM) {
            @Override
            protected void onCollisionBegin(final Entity player, final Entity obstacle) {
                //PlayerComponent playerComponent = player.getComponent(PlayerComponent.class);
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.ITEM) {
            @Override
            protected void onCollisionBegin(final Entity player, final Entity collectible) {
                collectible.removeFromWorld();
                inc("score", +10);
            }
        });

        getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityType.PLAYER, EntityType.PORTAL) {
            private boolean portalCooldown = false;
            private long lastPortalUseTime = 0;

            @Override
            protected void onCollisionBegin(final Entity player, final Entity portal) {
                final long currentTime = System.currentTimeMillis();
                final long PORTAL_COOLDOWN_MS = 2000;
                if (this.portalCooldown && (currentTime - this.lastPortalUseTime < PORTAL_COOLDOWN_MS)) {
                    return;
                }

                this.portalCooldown = true;
                this.lastPortalUseTime = currentTime;

                final String currentLevel = levelManager.getCurrentLevelId();
                String nextLevel = "waves";
                if ("waves".equals(currentLevel)) {
                    nextLevel = "particles";
                } else if ("particles".equals(currentLevel)) {
                    nextLevel = "waves";
                }

                log.info("Changing level from " + currentLevel + " to " + nextLevel);
                changeLevel(nextLevel);
            }

            @Override
            protected void onCollisionEnd(final Entity player, final Entity portal) {

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