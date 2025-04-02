package fr.quantumadventure.scene;

import com.almasb.fxgl.app.scene.IntroScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.dsl.FXGL;

public class GameSceneFactory extends SceneFactory {
    @Override
    public IntroScene newIntro() {
        return new GameScene(FXGL.getAppWidth(), FXGL.getAppHeight());
    }
}


