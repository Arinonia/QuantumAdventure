package fr.quantumadventure.tile;

import java.util.HashMap;
import java.util.Map;

public class WorldManager {
    private final Map<String, TileMap> maps = new HashMap<>();
    private String currentMapId;

    public void initMaps() {
        TileMap wavesMap = createWavesMap();
        this.maps.put("waves", wavesMap);

        TileMap particlesMap = createParticlesMap();
        this.maps.put("particles", particlesMap);
    }

    private TileMap createWavesMap() {
        TileMap map = new TileMap(25, 20);

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                map.setTile(x, y, Tile.createGrass());
            }
        }

        for (int x = 0; x < map.getWidth(); x++) {
            map.setTile(x, 0, Tile.createWall());
            map.setTile(x, map.getHeight()-1, Tile.createWall());
        }

        for (int y = 0; y < map.getHeight(); y++) {
            map.setTile(0, y, Tile.createWall());
            map.setTile(map.getWidth()-1, y, Tile.createWall());
        }

        for (int x = 5; x < 10; x++) {
            for (int y = 5; y < 8; y++) {
                map.setTile(x, y, Tile.createWater());
            }
        }

        map.setTile(3, 3, Tile.createCollectible());
        map.setTile(15, 12, Tile.createCollectible());

        map.setTile(22, 15, Tile.createPortal());

        return map;
    }

    private TileMap createParticlesMap() {
        TileMap map = new TileMap(25, 20);

        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                map.setTile(x, y, Tile.createSand());
            }
        }

        for (int x = 0; x < map.getWidth(); x++) {
            map.setTile(x, 0, Tile.createWall());
            map.setTile(x, map.getHeight()-1, Tile.createWall());
        }

        for (int y = 0; y < map.getHeight(); y++) {
            map.setTile(0, y, Tile.createWall());
            map.setTile(map.getWidth()-1, y, Tile.createWall());
        }

        for (int x = 10; x < 15; x++) {
            for (int y = 10; y < 15; y++) {
                if ((x + y) % 2 == 0) {
                    map.setTile(x, y, Tile.createWall());
                }
            }
        }

        map.setTile(7, 7, Tile.createCollectible());
        map.setTile(18, 5, Tile.createCollectible());

        map.setTile(22, 5, Tile.createPortal());

        return map;
    }

    public TileMap loadMap(String mapId) {
        if (this.maps.containsKey(mapId)) {
            this.currentMapId = mapId;
            return this.maps.get(mapId);
        }
        return null;
    }

    public TileMap getCurrentMap() {
        if (this.currentMapId != null) {
            return this.maps.get(this.currentMapId);
        }
        return null;
    }

    public String getCurrentMapId() {
        return this.currentMapId;
    }
}
