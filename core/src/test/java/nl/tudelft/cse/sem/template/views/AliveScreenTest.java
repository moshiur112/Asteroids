package nl.tudelft.cse.sem.template.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import nl.tudelft.cse.sem.template.App;
import nl.tudelft.cse.sem.template.ScoreProcessor;
import nl.tudelft.cse.sem.template.entities.Asteroid;
import nl.tudelft.cse.sem.template.entities.Bullet;
import nl.tudelft.cse.sem.template.entities.Entity;
import nl.tudelft.cse.sem.template.entities.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;


class AliveScreenTest {
    private static final int NUM_ASTEROIDS = 3;
    private static final int NUM_PLAYERS = 2;
    private static final int NUM_BULLETS = 7;

    transient AliveScreen aliveScreen;

    @Mock
    transient App appMock;
    transient List<Entity> asteroids = new ArrayList<>();
    transient List<Entity> players = new ArrayList<>();
    transient List<Entity> bullets = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        appMock = Mockito.mock(App.class, Mockito.RETURNS_DEEP_STUBS);
        aliveScreen = new AliveScreen(appMock);

        for (int i = 0; i < NUM_PLAYERS; i++) {
            Player mockPlayer = Mockito.mock(Player.class);
            Mockito.doNothing().when(mockPlayer).update(any(ArrayList.class));
            players.add(mockPlayer);
        }

        for (int i = 0; i < NUM_ASTEROIDS; i++) {
            Asteroid mockAsteroid = Mockito.mock(Asteroid.class);
            Mockito.doNothing().when(mockAsteroid).update(any(ArrayList.class));
            asteroids.add(mockAsteroid);
        }

        for (int i = 0; i < NUM_BULLETS; i++) {
            Bullet mockBullet = Mockito.mock(Bullet.class);
            Mockito.doNothing().when(mockBullet).render(any());
            bullets.add(mockBullet);
        }
    }

    @Test
    public void testCreateAsteroids() {
        int numAsteroids = 3;

        Mockito.when(appMock.getCamera()).thenReturn(Mockito.mock(OrthographicCamera.class));
        Mockito.when(appMock.getShapeRenderer()).thenReturn(Mockito.mock(ShapeRenderer.class));

        aliveScreen.player = new Player(40.2f, 80.2f, 4.4f);
        List<Entity> asteroids = aliveScreen.getAsteroids(numAsteroids);
        assertTrue(asteroids != null);
        assertEquals(asteroids.size(), numAsteroids);
    }

    @Test
    public void testHandleKeyDownIsSpace() {
        int spaceKeyCode = Input.Keys.SPACE;
        aliveScreen.handleKeyDown(spaceKeyCode);

        assertTrue(aliveScreen.inputs.get(4));
        assertFalse(aliveScreen.inputs.get(2));
    }

    @Test
    public void testHandleKeyDownIsDown() {
        int spaceKeyCode = Input.Keys.DOWN;
        aliveScreen.handleKeyDown(spaceKeyCode);

        assertTrue(aliveScreen.inputs.get(2));
        assertFalse(aliveScreen.inputs.get(4));
    }

    @Test
    public void testHandleKeyDownIsNotSpaceNorDown() {
        int spaceKeyCode = Input.Keys.UP;
        aliveScreen.handleKeyDown(spaceKeyCode);

        assertFalse(aliveScreen.inputs.get(4));
        assertFalse(aliveScreen.inputs.get(2));
    }

    @Test
    public void testUpdateEntitiesWithNewInputs() {
        aliveScreen.updateEntities(aliveScreen.inputs, players);
        aliveScreen.updateEntities(aliveScreen.inputs, asteroids);

        for (int i = 0; i < NUM_PLAYERS; i++) {
            Mockito.verify(players.get(i), Mockito.times(1))
                    .update(any(ArrayList.class));
        }

        for (int i = 0; i < NUM_ASTEROIDS; i++) {
            Mockito.verify(asteroids.get(i), Mockito.times(1))
                    .update(any(ArrayList.class));
        }
    }

    @Test
    public void testRenderPlayerBullets() {

        aliveScreen.renderEntities(bullets);
        for (int i = 0; i < NUM_BULLETS; i++) {
            Mockito.verify(bullets.get(i), Mockito.times(1))
                    .render(any());
        }
    }

    @Test
    public void testRenderAsteroids() {
        aliveScreen.renderEntities(asteroids);
        for (int i = 0; i < NUM_ASTEROIDS; i++) {
            Mockito.verify(asteroids.get(i), Mockito.times(1))
                    .render(any());
        }
    }

    @Test
    public void testDrawAndRenderPlayers() throws IllegalAccessException {
        SpriteBatch mockSpriteBatch = Mockito.mock(SpriteBatch.class);
        Mockito.doNothing().when(mockSpriteBatch).begin();
        Mockito.doNothing().when(mockSpriteBatch).draw(
                any(Texture.class), anyInt(), anyInt(), anyInt(), anyInt());
        Mockito.doNothing().when(mockSpriteBatch).end();

        Graphics mockGraphics = Mockito.mock(Graphics.class);
        Mockito.doReturn(0).when(mockGraphics).getWidth();
        Mockito.doReturn(0).when(mockGraphics).getHeight();


        Field field = PowerMockito.field(Gdx.class, "graphics");
        field.set(Gdx.class, mockGraphics);


        Mockito.doReturn(mockSpriteBatch).when(appMock).getBatch();

        for (int i = 0; i < NUM_PLAYERS; i++) {
            Mockito.doNothing().when(players.get(i)).render(any(SpriteBatch.class));
        }
        aliveScreen.renderEntities(players);
    }

//    @Test
//    public void testBasicCollision() {
//        for (int i = 0; i < bullets.size(); i++) {
//            Bullet mockBullet = (Bullet) bullets.get(i);
//            Mockito.doReturn(false).when(mockBullet).intersects(any(Asteroid.class));
//        }
//        Mockito.doReturn(true).when(bullets.get(0)).intersects(asteroids.get(0));
//        Mockito.doReturn(bullets).when((Player) players.get(0)).getBullets();
//
//        List<Asteroid> newAsteroids = new ArrayList<>(
//                Arrays.asList(Mockito.mock(Asteroid.class), Mockito.mock(Asteroid.class)));
//        Mockito.doReturn(newAsteroids).when((Asteroid) asteroids.get(0)).split();
//
//        aliveScreen.checkAsteroidCollisions(asteroids, players, new LinkedList<>());
//
//        assertEquals(NUM_BULLETS - 1, bullets.size());
//        assertEquals(NUM_ASTEROIDS + 1, asteroids.size());
//    }
}