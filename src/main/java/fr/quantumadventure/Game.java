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
        settings.setAppIcon("images/icon.png");
    }

    @Override
    protected void initGame() {
        System.out.println("Game initialized");
    }

    @Override
    protected void initPhysics() {
        System.out.println("Physics initialized");
    }

    @Override
    protected void initUI() {
        System.out.println("UI initialized");
    }

    @Override
    protected void onUpdate(double tpf) {
        System.out.println("Game updated");
    }
}
