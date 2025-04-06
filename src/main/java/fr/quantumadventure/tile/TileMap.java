package fr.quantumadventure.tile;

import javafx.scene.layout.Pane;

public class TileMap {
    private final int width;
    private final int height;
    private final Tile[][] tiles;
    private final Pane mapPane;

    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        this.tiles = new Tile[width][height];
        this.mapPane = new Pane();
    }

    public void setTile(int x, int y, Tile tile) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            if (this.tiles[x][y] != null) {
                this.mapPane.getChildren().remove(this.tiles[x][y].getView());
            }

            tiles[x][y] = tile;

            tile.getView().setTranslateX(x * Tile.TILE_SIZE);
            tile.getView().setTranslateY(y * Tile.TILE_SIZE);

            this.mapPane.getChildren().add(tile.getView());
        }
    }

    public Tile getTile(int x, int y) {
        if (x >= 0 && x < this.width && y >= 0 && y < this.height) {
            return this.tiles[x][y];
        }
        return null;
    }

    public boolean isWalkable(int x, int y) {
        Tile tile = getTile(x, y);
        return tile != null && tile.isWalkable();
    }

    public Pane getView() {
        return this.mapPane;
    }

    public int getPixelWidth() {
        return this.width * Tile.TILE_SIZE;
    }

    public int getPixelHeight() {
        return this.height * Tile.TILE_SIZE;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
