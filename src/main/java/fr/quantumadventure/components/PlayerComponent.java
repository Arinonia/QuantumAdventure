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

    private double dampingFactor = 0.9;

    private boolean movingRight = false;
    private boolean movingLeft = false;
    private boolean movingUp = false;
    private boolean movingDown = false;

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
        updateMovement();

        if (!isMovingInAnyDirection()) {
            applyDamping();
        }
    }

    private boolean isMovingInAnyDirection() {
        return movingRight || movingLeft || movingUp || movingDown;
    }

    private void applyDamping() {
        if (physics == null) return;

        double vx = physics.getVelocityX();
        double vy = physics.getVelocityY();

        if (Math.abs(vx) < 5.0) {
            vx = 0;
        } else {
            vx *= dampingFactor;
        }

        if (Math.abs(vy) < 5.0) {
            vy = 0;
        } else {
            vy *= dampingFactor;
        }

        physics.setVelocityX(vx);
        physics.setVelocityY(vy);
    }

    private void updateMovement() {
        if (physics == null) return;

        double vx = 0;
        double vy = 0;

        if (movingRight) vx += speed;
        if (movingLeft) vx -= speed;
        if (movingDown) vy += speed;
        if (movingUp) vy -= speed;

        if (vx != 0 && vy != 0) {
            double factor = 1 / Math.sqrt(2);
            vx *= factor;
            vy *= factor;
        }

        physics.setVelocityX(vx);
        physics.setVelocityY(vy);
    }

    public void moveRight() {
        if (this.physics != null) {
            movingRight = true;
            updateMovement();
        } else {
            log.error("PhysicsComponent is null, cannot move right");
        }
    }

    public void moveLeft() {
        if (this.physics != null) {
            movingLeft = true;
            updateMovement();
        } else {
            log.error("PhysicsComponent is null, cannot move left");
        }
    }

    public void moveUp() {
        if (this.physics != null) {
            movingUp = true;
            updateMovement();
        } else {
            log.error("PhysicsComponent is null, cannot move up");
        }
    }

    public void moveDown() {
        if (this.physics != null) {
            movingDown = true;
            updateMovement();
        } else {
            log.error("PhysicsComponent is null, cannot move down");
        }
    }

    public void stop() {
        if (this.physics != null) {
            movingRight = false;
            movingLeft = false;
            movingUp = false;
            movingDown = false;
            updateMovement();
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