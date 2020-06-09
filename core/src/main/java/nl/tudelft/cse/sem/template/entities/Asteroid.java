package nl.tudelft.cse.sem.template.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic obstacle class Asteroid.
 */
public class Asteroid extends Entity {

    // ShapeRenderer and camera of the asteroid, set to those of the game
    private transient ShapeRenderer shapeRenderer;
    private transient OrthographicCamera camera;

    // Detail of the asteroid = amount of points on the asteroids
    private transient int detail;

    // Range for it to be off the screen to appear on the other size
    private transient float borderRange;

    // All the vertices for the asteroids --> i = x, i + 1 = y, i + 2 = x ...
    private transient float[] vertices;

    // Roughness offset value for each vertex
    private transient float[] roughness;

    // Start rotation of the asteroid
    private transient float rotation = 0;

    // Rotation speed of the asteroid
    private transient float rotationSpeed;
    private transient float angle;
    private transient int level;

    /**
     * Constructor of an asteroid.
     *
     * @param x             x position
     * @param y             y position
     * @param speed         speed --> (0.5 * speed, 1.5 * speed)
     * @param rigidness     rigidness --> roughness(0.5 * rigidness, 1.5 * rigidness)
     * @param rotationSpeed rotationSpeed --> (0.5 * rotationSpeed, 1.5 * rotationSpeed)
     */
    public Asteroid(float x, float y, float speed,
                    float rigidness, float rotationSpeed, int level, float angle) {
        super(x, y);

        // Set attributes
        this.borderRange = 10;
        this.level = level;
        this.angle = angle;

        // Generated attributes
        this.vel = generateVelocity(speed, angle);
        this.size = (float) Math.pow((Math.random() * 2 + 2), level);

        // set the rotationSpeed to (0.5 * rotationSpeed, 1.5 * rotationSpeed)
        this.rotationSpeed = (float) Math.random() * rotationSpeed - rotationSpeed / 2;

        // Set the amount of points to the size, if uneven remove one
        detail = ((int) size % 2 != 0) ? (int) size + 5 : (int) size + 6;
        createVertices(detail, rigidness);
    }

    /**
     * Generates a random speed around a given value.
     *
     * @param speed target speed around which to generate a random speed
     * @param angle angle of the asteroid
     * @return Random x,y velocity in range (0.5 * speed, 1.5 * speed)
     */
    private Vector2 generateVelocity(float speed, float angle) {
        double randomSpeed = (Math.random() * speed - speed / 2);
        double x = randomSpeed * Math.cos(Math.toRadians(angle));
        double y = randomSpeed * Math.sin(Math.toRadians(angle));

        return new Vector2((float) x, (float) y);
    }

    /**
     * Set the position and roughness for all the vertices.
     *
     * @param detail    number of vertices of the asteroid
     * @param rigidness amount of variety in curvature
     */
    private void createVertices(int detail, float rigidness) {
        // Create vertices and roughness arrays based on the size
        vertices = new float[detail];
        roughness = new float[detail];

        for (int i = 0; i < detail; i += 2) {
            roughness[i] = (float) Math.random() * rigidness + rigidness / 2;
            roughness[i + 1] = (float) Math.random() * rigidness + rigidness / 2;
            vertices[i] = this.pos.x + (float) Math.cos(Math.PI * 2 / detail * i)
                    * this.size + roughness[i];
            vertices[i + 1] = this.pos.y + (float) Math.sin(Math.PI * 2 / detail
                    * (i + 1)) * this.size + roughness[i + 1];
        }
    }

    /**
     * Render method for a single asteroid's changes over frames of time.
     *
     * @param batch SpriteBatch to know how to render the sprite.
     */
    @Override
    public void render(SpriteBatch batch) {
        // Set shapeRenderer settings
        shapeRenderer.identity();
        Gdx.gl.glLineWidth(3);
        shapeRenderer.setProjectionMatrix(camera.combined);

        // start drawing our custom object as a combination as line
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.polygon(vertices);
        shapeRenderer.end();
    }

    /**
     * Method to handle updating a single asteroid.
     *
     * @param inputs the inputs that are passed to the method.
     */
    @Override
    public void update(List<Boolean> inputs) {
        // update the rotation and position
        this.rotation += this.rotationSpeed;
        this.pos.add(vel);

        // update the position of all the vertices
        for (int i = 0; i < detail; i += 2) {
            vertices[i] = this.pos.x + (float) Math.cos(Math.PI * 2 / detail * i + this.rotation)
                    * (this.size + roughness[i]);
            vertices[i + 1] = this.pos.y
                    + (float) Math.sin(Math.PI * 2 / detail * i + this.rotation)
                    * (this.size + roughness[i + 1]);
        }
        checkWrap();
    }

    /**
     * Checks if the asteroid has passed the border of
     * the screen, and, if so, wraps it around the screen.
     */
    private void checkWrap() {
        // asteroid disappears to the right of the screen, reset position to left
        if (this.pos.x > Gdx.app.getGraphics().getWidth() + this.size + this.borderRange) {
            resetPosition(false);
            this.pos.x = -this.size;
        }
        // asteroid disappears to the top of the screen, reset position to bottom
        if (this.pos.y > Gdx.app.getGraphics().getHeight() + this.size + this.borderRange) {
            resetPosition(true);
            this.pos.y = -this.size;
        }
        // asteroid disappears to the left of the screen, reset position to right
        if (this.pos.x + this.size + this.borderRange < 0) {
            resetPosition(false);
            this.pos.x = Gdx.app.getGraphics().getWidth();
        }
        // asteroid disappears to the bottom of the screen, reset to the top
        if (this.pos.y + this.size + this.borderRange < 0) {
            resetPosition(true);
            this.pos.y = Gdx.app.getGraphics().getHeight();
        }
    }

    /**
     * Resets the position of all vertices when wrapping around the screen.
     *
     * @param vertical boolean value telling if the asteroid
     *                 is wrapping horizontally (left, right) or vertically (top , down)
     */
    private void resetPosition(boolean vertical) {
        if (vertical) {
            for (int i = 1; i < detail; i += 2) {
                vertices[i] = vertices[i] + Gdx.app.getGraphics().getHeight() + 2
                        * this.size + this.borderRange;
            }
        } else {
            for (int i = 0; i < detail; i += 2) {
                vertices[i] = vertices[i] + Gdx.app.getGraphics().getWidth() + 2
                        * this.size + this.borderRange;
            }
        }
    }

    /**
     * Splits the asteroid and returns a number of new ones.
     *
     * @return list of asteroids
     */
    public List<Asteroid> split() {
        // Lowest level at which the level will still split
        int splitThreshold = 2;
        List<Asteroid> asteroids = new ArrayList<>();

        if (level > splitThreshold) {
            // Number of asteroids the current one will split into
            int numberOfAsteroids = 2;

            for (int i = 0; i < numberOfAsteroids; i++) {
                Asteroid newAsteroid = new Asteroid(this.pos.x, this.pos.y,
                        (4 - level) * 5, 15.0f / (level - 1),
                        this.rotationSpeed * 10, level - 1, this.angle);
                newAsteroid.setShapeRenderer(shapeRenderer);
                newAsteroid.setCamera(camera);
                asteroids.add(newAsteroid);
            }
        }
        return asteroids;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public OrthographicCamera getCamera() {
        return this.camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public float getBorderRange() {
        return borderRange;
    }

    public void setBorderRange(float borderRange) {
        this.borderRange = borderRange;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public float getRotationSpeed() {
        return rotationSpeed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
