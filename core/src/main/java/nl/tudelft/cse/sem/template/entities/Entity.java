package nl.tudelft.cse.sem.template.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.List;


public abstract class Entity {

    protected transient Vector2 pos;
    protected transient Vector2 vel;
    // TODO acc currently not used, should be used in player
    // protected transient Vector2 acc;
    protected transient float size;


    /**
     * Constructor for the Entity class.
     *
     * @param x This is the x coordinate.
     * @param y This is the y coordinate
     */
    public Entity(float x, float y) {
        this.pos = new Vector2(x, y);
        this.vel = new Vector2(0, 0);
        // TODO acc currently not used, should be used in player
        // this.acc = new Vector2(0, 0);
    }

    public abstract void render(SpriteBatch batch);

    public abstract void update(List<Boolean> inputs);

    public Vector2 getPos() {
        return this.pos;
    }

    public void setPos(Vector2 pos) {
        this.pos = pos;
    }

    public float getSize() {
        return this.size;
    }

    public void setSize(float size) {
        this.size = size;
    }

    /**
     * Checks if there's an intersection between two entities.
     * @param that other entity
     * @return boolean indicating whether there has been a collision or not
     */
    public boolean intersects(Entity that) {
        return Math.pow(this.pos.x - that.pos.x + this.size / 2 + that.size / 2, 2) // (x1 - x2 + s1/2 + s2/2)^2
                                                                                                +
                + Math.pow(this.pos.y + this.size / 2 - that.pos.y + that.size / 2, 2) // (y1 - y2 + s1/2 + s2/2)^2
                < Math.pow(this.size + that.size, 2);
    }
}
