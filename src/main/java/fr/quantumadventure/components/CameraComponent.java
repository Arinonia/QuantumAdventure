package fr.quantumadventure.components;

import com.almasb.fxgl.entity.component.Component;
import fr.quantumadventure.utils.Constants;
import fr.quantumadventure.utils.logger.Logger;

import static com.almasb.fxgl.dsl.FXGL.*;

public class CameraComponent extends Component {
    private static final Logger log = Logger.getLogger(CameraComponent.class);

    private int worldWidth;
    private int worldHeight;

    public CameraComponent(final int worldWidth, final int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        log.info("Camera initialized with world dimensions: ", worldWidth, "x", worldHeight);
    }

    @Override
    public void onUpdate(final double tpf) {
        updateCamera();
    }

    private void updateCamera() {
        double playerX = this.entity.getX();
        double playerY = this.entity.getY();

        final int viewportWidth = Constants.GAME_WIDTH;
        final int viewportHeight = Constants.GAME_HEIGHT;

        double cameraX = playerX - viewportWidth / 2;
        double cameraY = playerY - viewportHeight / 2;

        cameraX = Math.max(0, Math.min(cameraX, worldWidth - viewportWidth));
        cameraY = Math.max(0, Math.min(cameraY, worldHeight - viewportHeight));

        getGameScene().getViewport().setX((int) cameraX);
        getGameScene().getViewport().setY((int) cameraY);
    }

    public void setWorldDimensions(final int worldWidth, final int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
        log.info("Camera world dimensions updated: ", worldWidth, "x", worldHeight);
    }
}