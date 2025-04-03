package fr.quantumadventure.world;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import fr.quantumadventure.utils.EntityType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class WorldFactory implements EntityFactory {

    @Spawns("platform")
    public Entity newPlatform(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PLATFORM)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .view(new Rectangle(data.<Integer>get("width"), data.<Integer>get("height"), Color.valueOf(data.get("color")))).build();
    }

    @Spawns("portal")
    public Entity newPortal(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PORTAL)
                .viewWithBBox(new Rectangle(40, 40, Color.PURPLE))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("collectible")
    public Entity newCollectible(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.ITEM)
                .viewWithBBox(new Rectangle(20, 20, Color.YELLOW))
                .with(new CollidableComponent(true))
                .build();
    }
}
