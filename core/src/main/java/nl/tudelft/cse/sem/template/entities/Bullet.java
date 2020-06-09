package nl.tudelft.cse.sem.template.entities;

//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.audio.Music;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Bullet extends Entity {

    private transient float heading;
    private transient float speed;
    private transient ShapeRenderer shapeRenderer;


    /**
     * Constructor for the Entity class.
     *
     * @param x This is the x coordinate.
     * @param y This is the y coordinate
     */
    public Bullet(float x, float y, float heading, float speed, ShapeRenderer shapeRenderer) {
        super(x, y);
        this.heading = heading;
        this.speed = speed;
        this.shapeRenderer = shapeRenderer;
        this.size = 2.0f;

        this.vel = new Vector2((float) Math.sin(Math.toRadians(-heading))
                * speed, (float) Math.cos(Math.toRadians(-heading)) * speed);
    }

    @Override
    public void render(SpriteBatch batch) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.identity();
        shapeRenderer.translate(this.pos.x, this.pos.y, 0);
        shapeRenderer.rotate(0f, 0f, 1f, this.heading);
        shapeRenderer.rect(-0.5f * this.size, -2.5f * this.size, this.size, 5 * this.size);
        shapeRenderer.translate(-this.pos.x, -this.pos.y, 0);
        shapeRenderer.end();
    }

    @Override
    public void update(List<Boolean> inputs) {
        this.pos.add(this.vel);
    }

    public float getHeading() {
        return this.heading;
    }

    public void setHeading(float heading) {
        this.heading = heading;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public ShapeRenderer getShapeRenderer() {
        return this.shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }
}