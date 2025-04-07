package fr.quantumadventure.tile;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tile {
    public static final int TILE_SIZE = 32;

    private final TileType type;
    private final boolean walkable;
    private final ImageView view;

    public Tile(TileType type, boolean walkable, ImageView sprite) {
        this.type = type;
        this.walkable = walkable;

        this.view = sprite;
        //this.view.setStroke(null);
        this.view.setSmooth(false);
    }

    public static Tile createGrass() {
        return new Tile(TileType.GRASS, true, Tileset.getTileAt(0, 0));
    }

    public static Tile createWater() {
        return new Tile(TileType.WATER, false, Tileset.getTileAt(0, 4));
    }

    public static Tile createWall() {
        return new Tile(TileType.WALL, false, Tileset.getTileAt(0, 13));
    }

    public static Tile createSand() {
        return new Tile(TileType.SAND, true, Tileset.getTileAt(0, 12));
    }

    public static Tile createPortal() {
        return new Tile(TileType.PORTAL, true, Tileset.getTileAt(5, 0));
    }

    public static Tile createCollectible() {
        return new Tile(TileType.COLLECTIBLE, true, Tileset.getTileAt(13, 0));
    }

    public TileType getType() {
        return this.type;
    }

    public boolean isWalkable() {
        return this.walkable;
    }

    public ImageView getView() {
        return this.view;
    }
}
