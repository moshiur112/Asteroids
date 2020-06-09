package nl.tudelft.cse.sem.template.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

class BulletTest {

    private transient Bullet bullet;

    private static final int Y_POS = 100;
    private static final int X_POS = 100;
    private static final float HEADING = 0.0f;
    private static final float SPEED = 25f;
    private ShapeRenderer shapeRenderer;

    /**
     * Sets up the testing environment.
     * @throws IllegalAccessException if access is illegal
     */
    @BeforeEach
    public void setUp() throws IllegalAccessException {
        PowerMockito.mock(Gdx.class);

        Application app = mock(Application.class, Mockito.RETURNS_DEEP_STUBS);
        Field field = PowerMockito.field(Gdx.class, "app");
        field.set(Gdx.class, app);

        PowerMockito.when(Gdx.app.getGraphics().getHeight()).thenReturn(720);
        PowerMockito.when(Gdx.app.getGraphics().getWidth()).thenReturn(1280);

        shapeRenderer = PowerMockito.mock(ShapeRenderer.class);
        bullet = new Bullet(X_POS, Y_POS, HEADING, SPEED, shapeRenderer);
    }

    @Test
    public void testUpdateVelocity() {
        assertEquals(new Vector2(-0.0f, SPEED), bullet.vel);
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        bullet.update(inputs);
        assertEquals(new Vector2(-0.0f, SPEED), bullet.vel);
    }

    @Test
    public void testUpdatePosition() {
        assertEquals(new Vector2(-0.0f, SPEED), bullet.vel);
        assertEquals(new Vector2(X_POS, Y_POS), bullet.pos);

        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        bullet.update(inputs);
        assertEquals(new Vector2(X_POS, Y_POS + SPEED), bullet.pos);

        bullet.update(inputs);
        assertEquals(new Vector2(X_POS, Y_POS + 2 * SPEED), bullet.pos);
    }

    @Test
    void setHeading() {
        assertEquals(HEADING, bullet.getHeading());
        bullet.setHeading(1.0f);
        assertEquals(1.0f, bullet.getHeading());
    }

    @Test
    void getHeading() {
        assertEquals(HEADING, bullet.getHeading());
        bullet.setHeading(1.0f);
        assertEquals(1.0f, bullet.getHeading());
    }

    @Test
    void setSpeed() {
        assertEquals(SPEED, bullet.getSpeed());
        bullet.setSpeed(50.0f);
        assertEquals(50.0f, bullet.getSpeed());
    }

    @Test
    void getSpeed() {
        assertEquals(SPEED, bullet.getSpeed());
    }

    @Test
    void setShapeRenderer() {
        assertEquals(shapeRenderer, bullet.getShapeRenderer());
        ShapeRenderer temp = PowerMockito.mock(ShapeRenderer.class);
        bullet.setShapeRenderer(temp);
        assertEquals(temp, bullet.getShapeRenderer());
    }

    @Test
    void getShapeRenderer() {
        assertEquals(shapeRenderer, bullet.getShapeRenderer());
    }
}