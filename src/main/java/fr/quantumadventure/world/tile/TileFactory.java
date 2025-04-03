package fr.quantumadventure.world.tile;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import fr.quantumadventure.utils.EntityType;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class TileFactory implements EntityFactory {

    @Spawns("tile")
    public Entity newTile(final SpawnData data) {
        final TileType type = data.get("type");
        int size = data.get("size");

        final Entity entity = entityBuilder(data)
                .type(type.isCollidable() ? EntityType.PLATFORM : EntityType.DECORATION)
                .view(new Rectangle(size, size, type.getColor()))
                .build();

        if (type.isCollidable()) {
            entity.addComponent(new CollidableComponent(true));
            entity.getBoundingBoxComponent().addHitBox(new HitBox(BoundingShape.box(size, size)));
        }

        return entity;
    }
}