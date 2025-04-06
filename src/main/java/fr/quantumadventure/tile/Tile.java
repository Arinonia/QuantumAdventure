package fr.quantumadventure.tile;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {
    public static final int TILE_SIZE = 32;

    private final TileType type;
    private final boolean walkable;
    private final Rectangle view;

    public Tile(TileType type, boolean walkable, Color color) {
        this.type = type;
        this.walkable = walkable;

        this.view = new Rectangle(TILE_SIZE, TILE_SIZE, color);
        this.view.setStroke(null);
        this.view.setSmooth(false);
    }

    public static Tile createGrass() {
        return new Tile(TileType.GRASS, true, Color.GREEN);
    }

    public static Tile createWater() {
        return new Tile(TileType.WATER, false, Color.BLUE);
    }

    public static Tile createWall() {
        return new Tile(TileType.WALL, false, Color.GRAY);
    }

    public static Tile createSand() {
        return new Tile(TileType.SAND, true, Color.YELLOW);
    }

    public static Tile createPortal() {
        return new Tile(TileType.PORTAL, true, Color.PURPLE);
    }

    public static Tile createCollectible() {
        return new Tile(TileType.COLLECTIBLE, true, Color.GOLD);
    }

    public TileType getType() {
        return this.type;
    }

    public boolean isWalkable() {
        return this.walkable;
    }

    public Rectangle getView() {
        return this.view;
    }
}
