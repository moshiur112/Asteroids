package nl.tudelft.cse.sem.template.entities;

import java.util.ArrayList;
import java.util.List;

public class Player extends SentientEntity {
    private transient float maxVel;

    /**
     * This is the constructor of the Player class.
     *
     * @param x          This is the x coordinate.
     * @param y          This is the y coordinate.
     * @param playerSize This passes the size of the player.
     */
    public Player(float x, float y, float playerSize) {
        super(x, y, playerSize);

        this.rotates = true;
        this.heading = 0;
        this.rotationSpeed = 4;
        this.speed = 0.15f;
        this.borderRange = 10;
        this.maxVel = 8;
        this.bullets = new ArrayList<>();
    }

    @Override
    public void updatePosition(List<Boolean> inputs) {
        this.pos.add(this.vel);

        if (inputs.get(0)) {
            float newX = this.vel.x + this.speed * (float) Math.sin(-Math.toRadians(this.heading));
            float newY = this.vel.y + this.speed * (float) Math.cos(Math.toRadians(this.heading));
            float newSpeed = (float) Math.sqrt(Math.pow(newX, 2) + Math.pow(newY, 2));

            if (newSpeed <= this.maxVel) {
                this.vel.x = newX;
                this.vel.y = newY;
            } else {
                this.vel.x = newX * maxVel / newSpeed;
                this.vel.y = newY * maxVel / newSpeed;
            }
        }
        if (inputs.get(1)) {
            this.heading -= rotationSpeed;
        }
        if (inputs.get(3)) {
            this.heading += rotationSpeed;
        }
        if (inputs.get(4)) {
            bullets.add(
                    new Bullet(
                            this.pos.x + this.size / 2,
                            this.pos.y + this.size / 2,
                            this.heading, 10f,
                            this.shapeRenderer
                    )
            );
        }
    }


}
