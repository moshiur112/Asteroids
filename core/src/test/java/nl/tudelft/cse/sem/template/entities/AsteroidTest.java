package nl.tudelft.cse.sem.template.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

class AsteroidTest {

    private transient Asteroid asteroid;

    private static final int Y_POS = 100;
    private static final int X_POS = 100;
    private static final float SPEED = 10;
    private static final float RIGID = 10;
    private static final float ROTATION = 10;
    private static final int LEVEL = 3;
    private static final float ANGLE = 0;

    private transient ShapeRenderer shapeRenderer = Mockito.mock(ShapeRenderer.class);
    private transient OrthographicCamera camera = Mockito.mock(OrthographicCamera.class);

    @BeforeEach
    public void setUp() throws IllegalAccessException {
        PowerMockito.mock(Gdx.class);
        Application app = mock(Application.class, Mockito.RETURNS_DEEP_STUBS);

        Field field = PowerMockito.field(Gdx.class, "app");
        field.set(Gdx.class, app);

        PowerMockito.when(Gdx.app.getGraphics().getHeight()).thenReturn(720);
        PowerMockito.when(Gdx.app.getGraphics().getWidth()).thenReturn(1280);

        asteroid = new Asteroid(X_POS, Y_POS, SPEED, RIGID, ROTATION, LEVEL, ANGLE);
        asteroid.setShapeRenderer(shapeRenderer);
        asteroid.setCamera(camera);
    }

    @Test
    public void testOffscreenTop() {
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        asteroid.setPos(new Vector2(0.0f, -10000f));
        asteroid.update(inputs);
        assertEquals(asteroid.getPos(), new Vector2(asteroid.getPos().x,
                Gdx.app.getGraphics().getHeight()));
    }

    @Test
    public void testOffscreenRight() {
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        asteroid.setPos(new Vector2(10000f, 0.0f));
        asteroid.update(inputs);
        assertEquals(asteroid.getPos(), new Vector2(-asteroid.getSize(), asteroid.getPos().y));
    }

    @Test
    public void testOffscreenBottom() {
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        asteroid.setPos(new Vector2(0.0f, 10000f));
        asteroid.update(inputs);
        assertEquals(asteroid.getPos(), new Vector2(asteroid.getPos().x, -asteroid.getSize()));
    }

    @Test
    public void testOffscreenLeft() {
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        asteroid.setPos(new Vector2(-10000f, 0.0f));
        asteroid.update(inputs);
        assertEquals(asteroid.getPos(), new Vector2(Gdx.app.getGraphics().getWidth(),
                asteroid.getPos().y));
    }

    @Test
    public void testGetShapeRenderer() {
        assertEquals(shapeRenderer, asteroid.getShapeRenderer());
    }

    @Test
    public void testSetShapeRenderer() {
        ShapeRenderer temp = Mockito.mock(ShapeRenderer.class);
        asteroid.setShapeRenderer(temp);
        assertEquals(temp, asteroid.getShapeRenderer());
    }

    @Test
    public void testGetCamera() {
        assertEquals(camera, asteroid.getCamera());
    }

    @Test
    public void testSetCamera() {
        OrthographicCamera temp = Mockito.mock(OrthographicCamera.class);
        asteroid.setCamera(temp);
        assertEquals(temp, asteroid.getCamera());
    }

    @Test
    void getDetail() {
        asteroid.setDetail(10);
        assertEquals(10, asteroid.getDetail());
    }

    @Test
    void setDetail() {
        asteroid.setDetail(10);
        assertEquals(10, asteroid.getDetail());
    }

    @Test
    void getSize() {
        asteroid.setSize(10);
        assertEquals(10, asteroid.getSize());
    }

    @Test
    void setSize() {
        asteroid.setSize(10);
        assertEquals(10, asteroid.getSize());
    }

    @Test
    void getBorderRange() {
        asteroid.setBorderRange(10.0f);
        assertEquals(10.0f, asteroid.getBorderRange());
    }

    @Test
    void setBorderRange() {
        asteroid.setBorderRange(10.0f);
        assertEquals(10.0f, asteroid.getBorderRange());
    }

    @Test
    void getRotation() {
        asteroid.setRotation(10.0f);
        assertEquals(10.0f, asteroid.getRotation());
    }

    @Test
    void setRotation() {
        asteroid.setRotation(10.0f);
        assertEquals(10.0f, asteroid.getRotation());
    }

    @Test
    void getRotationSpeed() {
        asteroid.setRotationSpeed(10.0f);
        assertEquals(10.0f, asteroid.getRotationSpeed());
    }

    @Test
    void setRotationSpeed() {
        asteroid.setRotationSpeed(10.0f);
        assertEquals(10.0f, asteroid.getRotationSpeed());
    }

    @Test
    void getAngle() {
        asteroid.setAngle(10.0f);
        assertEquals(10.0f, asteroid.getAngle());
    }

    @Test
    void setAngle() {
        asteroid.setAngle(10.0f);
        assertEquals(10.0f, asteroid.getAngle());
    }

    @Test
    void getLevel() {
        asteroid.setLevel(1);
        assertEquals(1, asteroid.getLevel());
    }

    @Test
    void setLevel() {
        asteroid.setLevel(1);
        assertEquals(1, asteroid.getLevel());
    }
}