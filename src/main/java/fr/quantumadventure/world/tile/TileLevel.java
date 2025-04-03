package fr.quantumadventure.world.tile;

import fr.quantumadventure.utils.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class TileLevel {
    private static final Logger log = Logger.getLogger(TileLevel.class);

    private final String id;
    private final TileGrid tileGrid;
    private final List<PositionData> collectibles = new ArrayList<>();
    private final List<PositionData> portals = new ArrayList<>();
    private final int tileSize;

    public TileLevel(final String id, final int width, final int height, final int tileSize) {
        this.id = id;
        this.tileSize = tileSize;
        this.tileGrid = new TileGrid(width, height, tileSize);
        log.info("Created tile level: ", id, " with dimensions: ", width, "x", height);
    }

    public String getId() {
        return this.id;
    }

    public TileGrid getTileGrid() {
        return this.tileGrid;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public void addCollectible(final int x, final int y) {
        this.collectibles.add(new PositionData(x, y));
    }

    public void addPortal(final int x, final int y) {
        this.portals.add(new PositionData(x, y));
    }

    public List<PositionData> getCollectibles() {
        return this.collectibles;
    }

    public List<PositionData> getPortals() {
        return this.portals;
    }

    public void setTile(final int x, final int y, final TileType type) {
        this.tileGrid.setTile(x, y, type);
    }

    public void fillRect(final int startX, final int startY, final int endX, final int endY, final TileType type) {
        this.tileGrid.fillRect(startX, startY, endX, endY, type);
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