package fr.quantumadventure;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import fr.quantumadventure.utils.Constants;

public class Game extends GameApplication {
    @Override
    protected void initSettings(final GameSettings settings) {
        settings.setWidth(Constants.GAME_WIDTH);
        settings.setHeight(Constants.GAME_HEIGHT);
        settings.setTitle(Constants.GAME_NAME);
        settings.setVersion(Constants.GAME_VERSION);
    }
}
