package fr.quantumadventure.tile;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import static fr.quantumadventure.tile.Tile.TILE_SIZE;

public class Tileset {
    private static final Image tileset = new Image(Tileset.class.getResourceAsStream("/images/tileset.png"));

    public static ImageView getTileAt(int col, int row) {
        WritableImage tile = new WritableImage(
                tileset.getPixelReader(),
                col * TILE_SIZE,
                row * TILE_SIZE,
                TILE_SIZE,
                TILE_SIZE
        );
        return new ImageView(tile);
    }
}
