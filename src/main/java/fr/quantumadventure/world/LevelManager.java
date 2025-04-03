package fr.quantumadventure.world;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import fr.quantumadventure.utils.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelManager {
    private static final Logger LOGGER = Logger.getLogger(LevelManager.class);

    private final Map<String, Level> levels = new HashMap<>();
    private String currentLevelId;
    private final List<Entity> currentLevelEntities = new ArrayList<>();

    private static final int DEFAULT_WORLD_WIDTH = 800;
    private static final int DEFAULT_WORLD_HEIGHT = 600;

    public LevelManager() {
        initLevels();
    }

    private void initLevels() {
        Level wavesLevel = new Level("waves", 800, 600);
        wavesLevel.addPlatform(0, 500, 800, 100, "darkblue");
        wavesLevel.addPlatform(100, 400, 200, 20, "blue");
        wavesLevel.addPlatform(400, 300, 200, 20, "blue");
        wavesLevel.addPlatform(200, 200, 200, 20, "blue");
        wavesLevel.addCollectible(150, 370);
        wavesLevel.addCollectible(450, 270);
        wavesLevel.addPortal(700, 450);
        levels.put("waves", wavesLevel);

        Level particlesLevel = new Level("particles", 1000, 800);
        particlesLevel.addPlatform(0, 500, 800, 100, "darkred");
        particlesLevel.addPlatform(200, 400, 150, 20, "red");
        particlesLevel.addPlatform(500, 350, 150, 20, "red");
        particlesLevel.addPlatform(300, 250, 150, 20, "red");
        particlesLevel.addCollectible(250, 370);
        particlesLevel.addCollectible(350, 220);
        particlesLevel.addPortal(700, 450);
        levels.put("particles", particlesLevel);
    }

    public void loadLevel(final String levelId) {
        unloadCurrentLevel();

        final Level levelToLoad = this.levels.get(levelId);
        if (levelToLoad == null) {
            LOGGER.error("Niveau introuvable: " + levelId);
            return;
        }

        LOGGER.info("Chargement du niveau: " + levelId);
        this.currentLevelId = levelId;

        for (final PlatformData platform : levelToLoad.getPlatforms()) {
            final Entity entity = FXGL.spawn("platform",
                    new SpawnData(platform.x, platform.y)
                            .put("width", platform.width)
                            .put("height", platform.height)
                            .put("color", platform.color));
            this.currentLevelEntities.add(entity);
        }

        for (final PositionData collectible : levelToLoad.getCollectibles()) {
            final Entity entity = FXGL.spawn("collectible", collectible.x, collectible.y);
            this.currentLevelEntities.add(entity);
        }

        for (final PositionData portal : levelToLoad.getPortals()) {
            final Entity entity = FXGL.spawn("portal", portal.x, portal.y);
            this.currentLevelEntities.add(entity);
        }
    }

    public void unloadCurrentLevel() {
        if (this.currentLevelId != null) {
            LOGGER.info("Unloading level: " + this.currentLevelId);

            for (final Entity entity : this.currentLevelEntities) {
                entity.removeFromWorld();
            }

            this.currentLevelEntities.clear();
            this.currentLevelId = null;
        }
    }

    public String getCurrentLevelId() {
        return this.currentLevelId;
    }

    public int getCurrentLevelWidth() {
        if (this.currentLevelId != null && this.levels.containsKey(this.currentLevelId)) {
            return this.levels.get(this.currentLevelId).width;
        }
        return DEFAULT_WORLD_WIDTH;
    }

    public int getCurrentLevelHeight() {
        if (this.currentLevelId != null && this.levels.containsKey(this.currentLevelId)) {
            return this.levels.get(this.currentLevelId).height;
        }
        return DEFAULT_WORLD_HEIGHT;
    }

    public static class Level {
        private final String id;
        private final List<PlatformData> platforms = new ArrayList<>();
        private final List<PositionData> collectibles = new ArrayList<>();
        private final List<PositionData> portals = new ArrayList<>();
        private final int width;
        private final int height;

        public Level(final String id, final int width, final int height) {
            this.id = id;
            this.width = width;
            this.height = height;
        }

        public void addPlatform(final int x, final int y, final int width, final int height, final String color) {
            this.platforms.add(new PlatformData(x, y, width, height, color));
        }

        public void addCollectible(int x, int y) {
            this.collectibles.add(new PositionData(x, y));
        }

        public void addPortal(final int x, final  int y) {
            this.portals.add(new PositionData(x, y));
        }

        public List<PlatformData> getPlatforms() {
            return this.platforms;
        }

        public List<PositionData> getCollectibles() {
            return this.collectibles;
        }

        public List<PositionData> getPortals() {
            return this.portals;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }
    }

    public static class PlatformData extends PositionData {
        public int width;
        public int height;
        public String color;

        public PlatformData(final int x, final int y, final int width, final int height, final String color) {
            super(x, y);
            this.width = width;
            this.height = height;
            this.color = color;
        }
    }

    public static class PositionData {
        public int x;
        public int y;

        public PositionData(final int x, final int y) {
            this.x = x;
            this.y = y;
        }
    }
}