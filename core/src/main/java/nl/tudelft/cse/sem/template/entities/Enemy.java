package nl.tudelft.cse.sem.template.entities;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Enemy extends SentientEntity {

    private transient Random randomGenerator;
    private transient double movingChance;
    private transient double shootingChance;

    /**
     * Constructor for the Entity class.
     *
     * @param x This is the x coordinate.
     * @param y This is the y coordinate
     */
    public Enemy(float x, float y, float enemySize) {
        super(x, y, enemySize);

        this.rotates = false;
        this.heading = 0;
        this.speed = 3;
        this.borderRange = 10;
        this.vel = new Vector2(speed, speed);

        this.bullets = new ArrayList<>();
        this.randomGenerator = new Random();
        this.movingChance = 2.3;
        this.shootingChance = 2.4;
    }

    @Override
    public void updatePosition(List<Boolean> inputs) {
        this.pos.add(this.vel);

        // Change heading
        if (this.randomGenerator.nextGaussian() > movingChance) {
            this.heading = determineHeading();
            this.vel.x =  (float) (speed * Math.sin(-Math.toRadians(this.heading)));
            this.vel.y = (float) (speed * Math.cos(Math.toRadians(this.heading)));
        }

        // Shoot bullets
        if (this.randomGenerator.nextGaussian() > shootingChance) {
            this.bullets.add(new Bullet(
                this.pos.x, this.pos.y, determineHeading(), 10f, this.shapeRenderer
            ));
        }
    }

    public float determineHeading() {
        return (this.heading + 45.0f * (float) new Random().nextGaussian()) % 360;
    }

    public Random getRandomGenerator() {
        return randomGenerator;
    }

    public void setRandomGenerator(Random randomGenerator) {
        this.randomGenerator = randomGenerator;
    }

    public double getMovingChance() {
        return movingChance;
    }

    public void setMovingChance(double movingChance) {
        this.movingChance = movingChance;
    }

    public double getShootingChance() {
        return this.shootingChance;
    }

    public void setShootingChance(double shootingChance) {
        this.shootingChance = shootingChance;
    }
}
