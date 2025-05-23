package fr.quantumadventure.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;

public class KeyInputManager {

    private final Map<KeyCode, Boolean> keyStates = new HashMap<>();

    public KeyInputManager(Scene scene) {
        scene.setOnKeyPressed(event -> this.keyStates.put(event.getCode(), true));
        scene.setOnKeyReleased(event -> this.keyStates.put(event.getCode(), false));
    }

    public boolean isKeyPressed(KeyCode keyCode) {
        return this.keyStates.getOrDefault(keyCode, false);
    }

    public boolean isKeyReleased(KeyCode keyCode) {
        return !this.keyStates.getOrDefault(keyCode, true);
    }

    public Map<KeyCode, Boolean> getKeyStates() {
        return keyStates;
    }
}

