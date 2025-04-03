package fr.quantumadventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.StartupScene;
import fr.quantumadventure.scene.CustomMainMenu;
import fr.quantumadventure.scene.CustomStartupScene;
import fr.quantumadventure.utils.Constants;
import org.jetbrains.annotations.NotNull;

public class Game extends GameApplication {

    @Override
    protected void initSettings(final GameSettings settings) {
        settings.setWidth(Constants.GAME_WIDTH);
        settings.setHeight(Constants.GAME_HEIGHT);
        settings.setTitle(Constants.GAME_NAME);
        settings.setVersion(Constants.GAME_VERSION);
        settings.setMainMenuEnabled(true);
        settings.setAppIcon("images/icon.png");

        settings.setSceneFactory(new SceneFactory() {
            @NotNull
            @Override
            public StartupScene newStartup(final int width, final int height) {
                return new CustomStartupScene(width, height);
            }

            @NotNull
            @Override
            public FXGLMenu newMainMenu() {
                return new CustomMainMenu();
            }
        });
    }

}
