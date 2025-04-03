package fr.quantumadventure.entity;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyDef;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import fr.quantumadventure.components.PlayerComponent;
import fr.quantumadventure.utils.EntityType;
import fr.quantumadventure.utils.logger.Logger;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class PlayerFactory implements EntityFactory {
    private static final Logger log = Logger.getLogger(PlayerFactory.class);

    @Spawns("player")
    public Entity newPlayer(final SpawnData data) {
        final PhysicsComponent physics = new PhysicsComponent();

        physics.setBodyType(BodyType.DYNAMIC);
        final BodyDef bodyDef = new BodyDef();
        bodyDef.setFixedRotation(true);
        bodyDef.setType(BodyType.DYNAMIC);
        physics.setBodyDef(bodyDef);
        physics.setFixtureDef(new FixtureDef().density(1.0f).restitution(0.0f).friction(0.3f));

        final Entity player = entityBuilder(data)
                .type(EntityType.PLAYER)
                .bbox(new HitBox(BoundingShape.box(32, 42)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new PlayerComponent())
                .view(new Rectangle(32, 42, Color.BLUE))
                .build();

        log.info("Created player entity at ", data.getX(), ",", data.getY());
        return player;
    }
}