package fr.quantumadventure.manager;

import fr.quantumadventure.entity.Player;
import fr.quantumadventure.tile.Tile;
import fr.quantumadventure.tile.TileMap;
import fr.quantumadventure.tile.WorldManager;
import javafx.scene.layout.Pane;

public class TileManager {
    private final WorldManager worldManager;
    private final Pane gamePane;
    private TileMap currentMap;
    private int score = 0;

    public TileManager(Pane gamePane) {
        this.gamePane = gamePane;
        this.worldManager = new WorldManager();
        this.worldManager.initMaps();
    }

    public TileMap loadMap(String mapId) {
        if (this.currentMap != null) {
            this.gamePane.getChildren().remove(this.currentMap.getView());
        }

        this.currentMap = this.worldManager.loadMap(mapId);
        this.gamePane.getChildren().add(this.currentMap.getView());
        return this.currentMap;
    }

    public boolean checkSpecialTiles(Player player) {
        double centerX = player.getX() + player.getWidth() / 2;
        double centerY = player.getY() + player.getHeight() / 2;

        int tileX = (int) (centerX / Tile.TILE_SIZE);
        int tileY = (int) (centerY / Tile.TILE_SIZE);

        Tile currentTile = this.currentMap.getTile(tileX, tileY);
        if (currentTile != null) {
            switch (currentTile.getType()) {
                case COLLECTIBLE:
                    collectItem(tileX, tileY);
                    return true;
                case PORTAL:
                    //! switch map but for now the logic is in Game.java method update because of the collision manager
                    return true;
                default:
                    return false;
            }
        }
        return false;
    }

    public void collectItem(int x, int y) {
        String currentMapId = worldManager.getCurrentMapId();

        if ("waves".equals(currentMapId)) {
            this.currentMap.setTile(x, y, Tile.createGrass());
        } else if ("particles".equals(currentMapId)) {
            this.currentMap.setTile(x, y, Tile.createSand());
        } else {
            this.currentMap.setTile(x, y, Tile.createGrass());
        }

        this.score += 10;
        System.out.println("Object collected, score: " + score);
    }

    public TileMap changeMap(Player player) {
        String currentMapId = this.worldManager.getCurrentMapId();
        String nextMapId = "waves";

        if ("waves".equals(currentMapId)) {
            nextMapId = "particles";
        } else if ("particles".equals(currentMapId)) {
            nextMapId = "waves";
        }

        TileMap newMap = loadMap(nextMapId);

        player.setPosition(100, 100);
        player.getView().toFront();
        System.out.println("Switching to map: " + nextMapId);
        return newMap;
    }

    public TileMap getCurrentMap() {
        return this.currentMap;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
