package fr.quantumadventure.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import fr.quantumadventure.utils.logger.Logger;

public class PlayerComponent extends Component {
    private static final Logger log = Logger.getLogger(PlayerComponent.class.getName());
    private PhysicsComponent physics;
    private int health = 80;
    private int maxHealth = 100;
    private double speed = 150.0D;

    @Override
    public void onAdded() {
        log.debug("PlayerComponent added to entity: ", this.getEntity().getType());
        this.physics = this.entity.getComponent(PhysicsComponent.class);
        if (this.physics == null) {
            log.error("PhysicsComponent is null for entity: ", this.getEntity().getType());
        }
    }

    @Override
    public void onUpdate(final double tpf) {

    }

    public void moveRight() {
        if (this.physics != null) {
            this.physics.setVelocityX(this.speed);
        } else {
            log.error("PhysicsComponent is null, cannot move right");
        }
    }

    public void moveLeft() {
        if (this.physics != null) {
            this.physics.setVelocityX(-this.speed);
        } else {
            log.error("PhysicsComponent is null, cannot move left");
        }
    }

    public void moveUp() {
        if (this.physics != null) {
            this.physics.setVelocityY(-this.speed);
        } else {
            log.error("PhysicsComponent is null, cannot move up");
        }
    }

    public void moveDown() {
        if (this.physics != null) {
            this.physics.setVelocityY(this.speed);
        } else {
            log.error("PhysicsComponent is null, cannot move down");
        }
    }

    public void stop() {
        if (this.physics != null) {
            this.physics.setVelocityX(0);
            this.physics.setVelocityY(0);
        } else {
            log.error("PhysicsComponent is null, cannot stop");
        }
    }

    public void takeDamage(final int damage) {
        this.health -= damage;
        if (this.health <= 0) {
            //die();
        }
    }

    public void heal(final int amount) {
        this.health += amount;
        if (this.health > this.maxHealth) {
            this.health = this.maxHealth;
        }
    }

    public int getHealth() {
        return this.health;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }
}
