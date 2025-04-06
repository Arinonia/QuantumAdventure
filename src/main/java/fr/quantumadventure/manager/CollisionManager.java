package fr.quantumadventure.manager;

import fr.quantumadventure.Player;
import fr.quantumadventure.tile.Tile;
import fr.quantumadventure.tile.TileMap;

public class CollisionManager {
    private TileMap currentMap;

    public void setCurrentMap(TileMap map) {
        this.currentMap = map;
    }

    public boolean checkCollision(Player player) {
        if (this.currentMap == null) {
            return false;
        }

        double playerWidth = player.getWidth();
        double playerHeight = player.getHeight();

        double[][] points = {
                {player.getX(), player.getY()},
                {player.getX() + playerWidth - 1, player.getY()},
                {player.getX(), player.getY() + playerHeight - 1},
                {player.getX() + playerWidth - 1, player.getY() + playerHeight - 1}
        };

        for (double[] point : points) {
            int tileX = (int) (point[0] / Tile.TILE_SIZE);
            int tileY = (int) (point[1] / Tile.TILE_SIZE);

            if (!isTileWalkable(tileX, tileY)) {
                return true;
            }
        }

        return false;
    }

    public boolean isTileWalkable(int x, int y) {
        if (this.currentMap == null) {
            return false;
        }
        if (x < 0 || x >= this.currentMap.getWidth() || y < 0 || y >= this.currentMap.getHeight()) {
            return false;
        }
        return this.currentMap.isWalkable(x, y);
    }
}
