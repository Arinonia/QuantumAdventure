package fr.quantumadventure.world.tile;

import fr.quantumadventure.utils.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class TileGrid {
    private static final Logger LOGGER = Logger.getLogger(TileGrid.class);

    private final int tileSize;
    private final int width;
    private final int height;
    private final Tile[][] grid;

    public TileGrid(final int width, final int height, final int tileSize) {
        this.width = width;
        this.height = height;
        this.tileSize = tileSize;
        this.grid = new Tile[width][height];

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                this.grid[x][y] = new Tile(TileType.EMPTY, x, y);
            }
        }

        LOGGER.info("Created tile grid with dimensions: ", width, "x", height, " (tile size: ", tileSize, ")");
    }

    public void setTile(final int x, final int y, final TileType type) {
        if (isValidPosition(x, y)) {
            this.grid[x][y].setType(type);
        } else {
            LOGGER.warn("Attempted to set tile outside grid bounds: ", x, ",", y);
        }
    }

    public Tile getTile(final int x, final int y) {
        if (isValidPosition(x, y)) {
            return this.grid[x][y];
        } else {
            LOGGER.warn("Attempted to get tile outside grid bounds: ", x, ",", y);
            return new Tile(TileType.EMPTY, x, y);
        }
    }

    public boolean isValidPosition(final int x, final int y) {
        return x >= 0 && x < this.width && y >= 0 && y < this.height;
    }

    public int getTileSize() {
        return this.tileSize;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getPixelWidth() {
        return this.width * this.tileSize;
    }

    public int getPixelHeight() {
        return this.height * this.tileSize;
    }

    public List<Tile> getAllTiles() {
        final List<Tile> tiles = new ArrayList<>();
        for (int x = 0; x < this.width; x++) {
            for (int y = 0; y < this.height; y++) {
                tiles.add(this.grid[x][y]);
            }
        }
        return tiles;
    }

    public int toTileX(final double pixelX) {
        return (int) (pixelX / this.tileSize);
    }

    public int toTileY(final double pixelY) {
        return (int) (pixelY / this.tileSize);
    }

    public int toPixelX(final int tileX) {
        return tileX * this.tileSize;
    }

    public int toPixelY(final int tileY) {
        return tileY * this.tileSize;
    }

    public void fillRect(final int startX, final int startY, final int endX, final int endY, final TileType type) {
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                setTile(x, y, type);
            }
        }
    }
}