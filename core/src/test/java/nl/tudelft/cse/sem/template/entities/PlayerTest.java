package nl.tudelft.cse.sem.template.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;


public class PlayerTest {

    private transient Player player;

    private static final float SIZE = 32f;
    private static final int Y_POS = 100;
    private static final int X_POS = 100;

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

        player = new Player(X_POS, Y_POS, SIZE);
    }

    @Test
    public void testUpdatePosition() {
        assertEquals(new Vector2(0.0f, 0.0f), player.vel);
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(true, false, false, false, false));
        player.update(inputs);
        assertEquals(new Vector2(0.0f, player.getSpeed()), player.vel);
    }

    @Test
    public void testUpdateRotationSpeedLeft() {
        assertEquals(0.0f, player.getHeading());
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, true, false));
        player.update(inputs);
        assertEquals(4.0f, player.getHeading());
    }

    @Test
    public void testUpdateRotationSpeedRight() {
        assertEquals(0.0f, player.getHeading());
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, true, false, false, false));
        player.update(inputs);
        assertEquals(-4.0f, player.getHeading());
    }

    @Test
    public void testOffscreenTop() {
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        player.setPos(new Vector2(0.0f, -10000f));
        player.update(inputs);
        assertEquals(player.getPos(),
                new Vector2(player.getPos().x, Gdx.app.getGraphics().getHeight()));
    }

    @Test
    public void testOffscreenRight() {
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        player.setPos(new Vector2(10000f, 0.0f));
        player.update(inputs);
        assertEquals(player.getPos(), new Vector2(-player.getSize(), player.getPos().y));
    }

    @Test
    public void testOffscreenBottom() {
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        player.setPos(new Vector2(0.0f, 10000f));
        player.update(inputs);
        assertEquals(player.getPos(), new Vector2(player.getPos().x, -player.getSize()));
    }

    @Test
    public void testOffscreenLeft() {
        ArrayList<Boolean> inputs =
                new ArrayList<>(Arrays.asList(false, false, false, false, false));
        player.setPos(new Vector2(-10000f, 0.0f));
        player.update(inputs);
        assertEquals(player.getPos(),
                new Vector2(Gdx.app.getGraphics().getWidth(), player.getPos().y));
    }

    @Test
    public void testGetSize() {
        assertEquals(SIZE, player.getSize());
    }

    @Test
    public void testSetSize() {
        float newSize = 12f;
        player.setSize(newSize);
        assertEquals(newSize, player.getSize());
    }

    @Test
    public void testGetHeading() {
        assertEquals(0, player.getHeading());
    }

    @Test
    public void testSetHeading() {
        float newHeading = 10f;
        player.setHeading(newHeading);
        assertEquals(newHeading, player.getHeading());
    }

    @Test
    public void testSetSpeed() {
        float newSpeed = 10f;
        player.setSpeed(newSpeed);
        assertEquals(newSpeed, player.getSpeed());
    }

    @Test
    public void testGetRotationSpeed() {
        assertEquals(4, player.getRotationSpeed());
    }

    @Test
    public void testSetRotationSpeed() {
        float newRotationSpeed = 14f;
        player.setRotationSpeed((newRotationSpeed));
        assertEquals(newRotationSpeed, player.getRotationSpeed());
    }

    @Test
    public void testGetBorderRange() {
        assertEquals(10, player.getBorderRange());
    }

    @Test
    public void testSetBorderRange() {
        float newBorderRange = 16f;
        player.setBorderRange((newBorderRange));
        assertEquals(newBorderRange, player.getBorderRange());
    }

}
