package fr.quantumadventure;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.StartupScene;
import fr.quantumadventure.scene.CustomMainMenu;
import fr.quantumadventure.scene.CustomStartupScene;
import org.jetbrains.annotations.NotNull;

public class CustomSceneFactory extends SceneFactory {

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
}
