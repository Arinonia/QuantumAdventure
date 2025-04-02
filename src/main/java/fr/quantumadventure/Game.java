package fr.quantumadventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.*;
import fr.quantumadventure.scene.MyStartupScene;
import fr.quantumadventure.utils.Constants;
import fr.quantumadventure.utils.logger.AnsiColor;
import fr.quantumadventure.utils.logger.Logger;

public class Game extends GameApplication {
    private static final Logger log = Logger.getLogger(Game.class);

    @Override
    protected void initSettings(final GameSettings settings) {

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public StartupScene newStartup(int width, int height) {
                return new MyStartupScene(width, height);
            }
        });

        settings.setWidth(Constants.GAME_WIDTH);
        settings.setHeight(Constants.GAME_HEIGHT);
        settings.setTitle(Constants.GAME_NAME);
        settings.setVersion(Constants.GAME_VERSION);
        settings.setAppIcon("images/icon.png");


    }

    @Override
    protected void initGame() {
        log.info("Initializing Game");
    }

    @Override
    protected void initPhysics() {
        log.info("Initializing Physics");
    }

    @Override
    protected void initUI() {
        log.info("Initializing UI");
    }

    @Override
    protected void onUpdate(double tpf) {
        log.debug("Updating UI:", Logger.colored("" + tpf, AnsiColor.GREEN));
    }
}
