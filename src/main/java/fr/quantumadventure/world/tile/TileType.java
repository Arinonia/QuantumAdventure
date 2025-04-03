package fr.quantumadventure.world.tile;

import javafx.scene.paint.Color;

public enum TileType {
    EMPTY("empty", false, Color.TRANSPARENT),
    GRASS("grass", false, Color.FORESTGREEN),
    DIRT("dirt", false, Color.BROWN),
    SAND("sand", false, Color.SANDYBROWN),
    STONE("stone", true, Color.GRAY),
    WATER("water", true, Color.BLUE),
    TREE("tree", true, Color.DARKGREEN),
    HOUSE("house", true, Color.FIREBRICK),
    ROCK("rock", true, Color.DARKGRAY);

    private final String id;
    private final boolean collidable;
    private final Color color;

    TileType(final String id, final boolean collidable, final Color color) {
        this.id = id;
        this.collidable = collidable;
        this.color = color;
    }

    public String getId() {
        return this.id;
    }

    public boolean isCollidable() {
        return this.collidable;
    }

    public Color getColor() {
        return this.color;
    }

    public static TileType fromId(final String id) {
        for (final TileType type : values()) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return EMPTY;
    }
}