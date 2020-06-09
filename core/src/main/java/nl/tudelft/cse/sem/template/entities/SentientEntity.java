package nl.tudelft.cse.sem.template.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;

public abstract class SentientEntity extends Entity {

    protected transient Sprite sprite;
    protected transient float size;
    protected transient float heading;
    protected transient float speed;
    protected transient float rotationSpeed;
    protected transient boolean rotates;
    protected transient float borderRange;
    protected transient ShapeRenderer shapeRenderer;
    protected transient List<Entity> bullets;

    /**
     * Constructor for the Entity class.
     *
     * @param x This is the x coordinate.
     * @param y This is the y coordinate
     */
    public SentientEntity(float x, float y, float size) {
        super(x, y);

        this.size = size;
    }

    public void setSprite(String filename) {
        this.sprite = new Sprite(new Texture(filename));
    }

    /**
     * Rotates the sprite.
     */
    public void rotateSprite() {
        if (this.rotates) {
            this.sprite.setRotation(this.heading);
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.begin();

        this.sprite.draw(batch);
        this.sprite.setOrigin(this.size / 2, this.size / 2);
        this.sprite.setPosition(this.pos.x, this.pos.y);
        this.sprite.setSize(this.size, this.size);
        rotateSprite();

        batch.end();

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).render(batch);
        }
    }

    @Override
    public void update(List<Boolean> inputs) {
        // Check if entity is outside the screen, and if so repositions it
        wrapAround();

        updatePosition(inputs);

        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).update(inputs);
        }
    }

    /**
     * Checks if the entity is out of the screen bounds, and, if so, puts it on the other side.
     */
    public void wrapAround() {
        if (this.pos.x > Gdx.app.getGraphics().getWidth() + this.borderRange) {
            this.pos.x = -this.size;
        }
        if (this.pos.x + this.size + this.borderRange < 0) {
            this.pos.x = Gdx.app.getGraphics().getWidth();
        }
        if (this.pos.y > Gdx.app.getGraphics().getHeight() + this.borderRange) {
            this.pos.y = -this.size;
        }
        if (this.pos.y + this.size + this.borderRange < 0) {
            this.pos.y = Gdx.app.getGraphics().getHeight();
        }

        bullets.removeIf(bullet -> bullet.getPos().x > Gdx.app.getGraphics().getWidth()
                || bullet.getPos().y > Gdx.app.getGraphics().getHeight());
    }

    public abstract void updatePosition(List<Boolean> inputs);

    public float getSize() {
        return this.size;
    }

    public void setSize(float size) {
        this.size = size;
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

    public float getRotationSpeed() {
        return this.rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getBorderRange() {
        return this.borderRange;
    }

    public void setBorderRange(float borderRange) {
        this.borderRange = borderRange;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public List<Entity> getBullets() {
        return this.bullets;
    }

    public void setScale(float scale) {
        this.sprite.setScale(scale);
    }
}
