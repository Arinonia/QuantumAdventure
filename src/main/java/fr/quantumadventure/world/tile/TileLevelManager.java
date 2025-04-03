package fr.quantumadventure.world.tile;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import fr.quantumadventure.utils.logger.Logger;
import fr.quantumadventure.world.tile.TileLevel.PositionData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TileLevelManager {
    private static final Logger LOGGER = Logger.getLogger(TileLevelManager.class);
    private static final int DEFAULT_TILE_SIZE = 32;

    private final Map<String, TileLevel> levels = new HashMap<>();
    private String currentLevelId;
    private final List<Entity> currentLevelEntities = new ArrayList<>();

    public TileLevelManager() {
        initLevels();
    }

    private void initLevels() {
        final TileLevel wavesLevel = new TileLevel("waves", 50, 30, DEFAULT_TILE_SIZE);

        wavesLevel.fillRect(0, 0, 49, 29, TileType.GRASS);

        wavesLevel.fillRect(0, 20, 49, 29, TileType.SAND);
        wavesLevel.fillRect(0, 25, 49, 29, TileType.WATER);

        wavesLevel.setTile(5, 18, TileType.ROCK);
        wavesLevel.setTile(6, 18, TileType.ROCK);
        wavesLevel.setTile(7, 19, TileType.ROCK);
        wavesLevel.setTile(25, 19, TileType.ROCK);
        wavesLevel.setTile(26, 19, TileType.ROCK);

        wavesLevel.setTile(3, 10, TileType.TREE);
        wavesLevel.setTile(10, 8, TileType.TREE);
        wavesLevel.setTile(15, 12, TileType.TREE);
        wavesLevel.setTile(30, 15, TileType.TREE);
        wavesLevel.setTile(40, 10, TileType.TREE);

        wavesLevel.setTile(20, 15, TileType.HOUSE);
        wavesLevel.setTile(21, 15, TileType.HOUSE);
        wavesLevel.setTile(22, 15, TileType.HOUSE);
        wavesLevel.setTile(20, 16, TileType.HOUSE);
        wavesLevel.setTile(21, 16, TileType.HOUSE);
        wavesLevel.setTile(22, 16, TileType.HOUSE);

        wavesLevel.addCollectible(250, 370);
        wavesLevel.addCollectible(450, 270);
        wavesLevel.addPortal(1500, 450);

        this.levels.put("waves", wavesLevel);

        final TileLevel particlesLevel = new TileLevel("particles", 60, 40, DEFAULT_TILE_SIZE);

        particlesLevel.fillRect(0, 0, 59, 39, TileType.DIRT);

        particlesLevel.fillRect(10, 10, 20, 15, TileType.STONE);
        particlesLevel.fillRect(30, 20, 40, 25, TileType.STONE);

        particlesLevel.setTile(5, 5, TileType.TREE);
        particlesLevel.setTile(8, 8, TileType.TREE);
        particlesLevel.setTile(45, 15, TileType.TREE);

        for (int x = 25; x < 35; x++) {
            particlesLevel.setTile(x, 30, TileType.STONE);
            particlesLevel.setTile(x, 35, TileType.STONE);
        }
        for (int y = 30; y <= 35; y++) {
            particlesLevel.setTile(25, y, TileType.STONE);
            particlesLevel.setTile(34, y, TileType.STONE);
        }

        particlesLevel.addCollectible(350, 370);
        particlesLevel.addCollectible(550, 270);
        particlesLevel.addPortal(1800, 450);

        levels.put("particles", particlesLevel);
    }

    public void loadLevel(final String levelId) {
        unloadCurrentLevel();

        final TileLevel levelToLoad = this.levels.get(levelId);
        if (levelToLoad == null) {
            LOGGER.error("Niveau introuvable: " + levelId);
            return;
        }

        LOGGER.info("Chargement du niveau: " + levelId);
        this.currentLevelId = levelId;

        final TileGrid tileGrid = levelToLoad.getTileGrid();
        int tileSize = levelToLoad.getTileSize();

        for (final Tile tile : tileGrid.getAllTiles()) {
            if (tile.getType() != TileType.EMPTY) {
                final Entity entity = FXGL.spawn("tile",
                        new SpawnData(tileGrid.toPixelX(tile.getX()), tileGrid.toPixelY(tile.getY()))
                                .put("type", tile.getType())
                                .put("size", tileSize));
                this.currentLevelEntities.add(entity);
            }
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
            final TileLevel level = this.levels.get(this.currentLevelId);
            return level.getTileGrid().getPixelWidth();
        }
        return 800;
    }

    public int getCurrentLevelHeight() {
        if (this.currentLevelId != null && this.levels.containsKey(this.currentLevelId)) {
            final TileLevel level = this.levels.get(this.currentLevelId);
            return level.getTileGrid().getPixelHeight();
        }
        return 600;
    }
}