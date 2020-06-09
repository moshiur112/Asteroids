package nl.tudelft.cse.sem.template.entities;

import static org.powermock.api.mockito.PowerMockito.mock;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;

import java.lang.reflect.Field;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;

class EnemyTest {

    private transient Enemy enemy;

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

        enemy = new Enemy(X_POS, Y_POS, SIZE);
    }

    @Test
    public void testMoving() {
        Random randomMock = Mockito.mock(Random.class);
        Mockito.doReturn(1.0).when(randomMock).nextGaussian();

        enemy.setRandomGenerator(randomMock);
        enemy.setShootingChance(2.0);
        enemy.setMovingChance(0.0);
        float oldHeading = enemy.getHeading();
        enemy.updatePosition(null);

        Assert.assertNotEquals(oldHeading, enemy.getHeading());
    }

    @Test
    public void testShooting() {
        Random randomMock = Mockito.mock(Random.class);
        Mockito.doReturn(1.0).when(randomMock).nextGaussian();

        enemy.setRandomGenerator(randomMock);
        enemy.setShootingChance(0.0);
        enemy.setMovingChance(2.0);
        enemy.updatePosition(null);

        Assert.assertEquals(enemy.getBullets().size(), 1);
    }
}