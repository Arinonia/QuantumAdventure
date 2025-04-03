package fr.quantumadventure.world.tile;

public class Tile {
    private TileType type;
    private final int x;
    private final int y;

    public Tile(final TileType type, final int x, final int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }

    public TileType getType() {
        return this.type;
    }

    public void setType(final TileType type) {
        this.type = type;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isCollidable() {
        return this.type.isCollidable();
    }

    @Override
    public String toString() {
        return "Tile[type=" + this.type + ", x=" + this.x + ", y=" + this.y + "]";
    }
}