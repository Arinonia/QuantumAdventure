package fr.quantumadventure.world.tile;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import fr.quantumadventure.utils.EntityType;
import fr.quantumadventure.utils.logger.Logger;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class TileFactory implements EntityFactory {
    private static final Logger log = Logger.getLogger(TileFactory.class);

    @Spawns("tile")
    public Entity newTile(final SpawnData data) {
        final TileType type = data.get("type");
        int size = data.get("size");

        if (type.isCollidable()) {
            final PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.STATIC);

            physics.setFixtureDef(new FixtureDef().density(0.0f).restitution(0.0f).friction(0.8f));

            final Entity entity = entityBuilder(data)
                    .type(EntityType.PLATFORM)
                    .with(physics)
                    .bbox(new HitBox(BoundingShape.box(size, size)))
                    .with(new CollidableComponent(true))
                    .view(new Rectangle(size, size, type.getColor()))
                    .build();

            log.info("Created collidable tile: ", type, " at ", data.getX(), ",", data.getY());
            return entity;
        }
        else {
            final Entity entity = entityBuilder(data)
                    .type(EntityType.DECORATION)
                    .view(new Rectangle(size, size, type.getColor()))
                    .build();

            log.debug("Created non-collidable tile: ", type, " at ", data.getX(), ",", data.getY());
            return entity;
        }
    }
}