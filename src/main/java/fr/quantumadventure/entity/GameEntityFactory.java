package fr.quantumadventure.entity;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import fr.quantumadventure.utils.EntityType;

//@Spawns("wall") // Annotation pour identifier l'entité dans le TMX
public class GameEntityFactory implements EntityFactory {

    @Spawns("wall")  // Correspond à l'objet "wall" dans Tiled
    public Entity newWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.WALL)
                .bbox(new HitBox(BoundingShape.box(32, 32))) // Taille d'une tile
                .with(new CollidableComponent(true)) // Permet les collisions
                .build();
    }
}
