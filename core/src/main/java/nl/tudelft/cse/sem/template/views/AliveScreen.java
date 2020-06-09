package nl.tudelft.cse.sem.template.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import nl.tudelft.cse.sem.template.App;
import nl.tudelft.cse.sem.template.ScoreProcessor;
import nl.tudelft.cse.sem.template.entities.Asteroid;
import nl.tudelft.cse.sem.template.entities.Enemy;
import nl.tudelft.cse.sem.template.entities.Entity;
import nl.tudelft.cse.sem.template.entities.Player;
import nl.tudelft.cse.sem.template.views.score.DeathScreen;



public class AliveScreen extends AppScreen {

    protected transient List<Entity> asteroids = new ArrayList<>();
    protected transient List<Entity> enemies = new ArrayList<>();
    protected transient ExtendViewport viewport =
            new ExtendViewport(app.getWidth(), app.getHeight(), app.getCamera());
    protected transient Player player;
    protected transient List<Boolean> inputs = new ArrayList<>(
            Arrays.asList(false, false, false, false, false));
    protected transient List<Entity> players = new ArrayList<>();
    protected transient int wave = 0;

    // Score display
    private transient ScoreProcessor scoreProcessor;
    private transient SpriteBatch statusBatch;
    private transient BitmapFont statusFont;

    /**
     * Initialize AliveScreen with a ScoreProcessor.
     *
     * @param app Call app class.
     */
    public AliveScreen(App app) {
        super(app);
    }

    /**
     * Generates a given number of Asteroid objects.
     *
     * @param numAsteroids number of asteroids to generate
     * @return list of generated asteroids
     */
    protected List<Entity> getAsteroids(int numAsteroids) {
        List<Entity> asteroidsList = new ArrayList<>();

        for (int i = 0; i < numAsteroids; i++) {
            int level = 1 + (new Random()).nextInt(3) + 1;
            float newX = getAsteroidCoordinate(app.getWidth(), 30f, player.getPos().x);
            float newY = getAsteroidCoordinate(app.getHeight(), 30f, player.getPos().y);

            Asteroid newAsteroid = new Asteroid(newX, newY,
                    (float) Math.random() * 4 + 2, 15 / (float) level,
                    0.05f, 3, (float) Math.random() * 360);

            newAsteroid.setShapeRenderer(app.getShapeRenderer());
            newAsteroid.setCamera(app.getCamera());
            asteroidsList.add(newAsteroid);
        }
        return asteroidsList;
    }

    /**
     * Generates an asteroid coordinate outside of the player region between 0 - rangeFactor.
     *
     * @param rangeFactor max coordinate value
     * @param threshold min distance between the player and an asteroid
     * @param playerPos position of the player
     * @return float between 0 - rangeFactor
     */
    private float getAsteroidCoordinate(float rangeFactor, float threshold, float playerPos) {
        float x = (float) Math.random() * rangeFactor;

        while(Math.abs(x - playerPos) < threshold) {
            x = (float) Math.random() * rangeFactor;
        }
        return x;
    }

    /**
     * Generates a given number of Enemy objects.
     *
     * @param numEnemies the number of enemies to generate
     * @return a list of generated enemies
     */
    protected List<Entity> getEnemies(int numEnemies) {
        List<Entity> enemiesList = new ArrayList<>();

        for (int i = 0; i < numEnemies; i++) {

            Enemy newEnemy = new Enemy(50, 50, players.get(0).getSize());
            newEnemy.setSprite("ufo.png");
            newEnemy.setShapeRenderer(app.getShapeRenderer());
            enemiesList.add(newEnemy);
        }
        return enemiesList;
    }

    /**
     * Keyboard handler.
     * @param keyCode the code of the button on the keyboard
     */
    protected void handleKeyDown(int keyCode) {
        if (Input.Keys.SPACE == keyCode) {
            inputs.set(4, true);
        }
        if (Input.Keys.DOWN == keyCode) {
            inputs.set(2, true);
        }
    }

    /**
     * Calls the update update function in all entities.
     *
     * @param inputs   list of boolean inputs
     * @param entities list of entities to be updated
     */
    protected void updateEntities(List<Boolean> inputs, List<Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(inputs);
        }
    }

    /**
     * Calls the render update function in all entities.
     *
     * @param entities list of entities to be rendered
     */
    protected void renderEntities(List<Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(app.getBatch());
        }
    }

    /**
     * Checks collisions between all given entities.
     *
     * @param players   list of players
     * @param asteroids list of asteroids
     * @param enemies   list of enemies
     */
    private void checkAllCollisions(List<Entity> players, List<Entity> asteroids,
                                    List<Entity> enemies) {

        Player player = (Player) players.get(0);
        checkPlayerAsteroidCollisions(player, asteroids);
        checkPlayerEnemyCollisions(player, enemies);
    }

    /**
     * Checks collisions between a player and list of enemies, and acts accordingly.
     *
     * @param player  player to check collisions with
     * @param enemies list of enemies ot be checked
     */
    private void checkPlayerEnemyCollisions(Player player, List<Entity> enemies) {
        List<Enemy> removableEnemies = new ArrayList<>();
        List<Entity> removableBullets = new ArrayList<>();

        // Check Players and Enemies
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = (Enemy) enemies.get(i);

            for (int j = 0; j < enemy.getBullets().size(); j++) {
                for (int r = 0; r < player.getBullets().size(); r++) {
                    if (player.intersects(enemy.getBullets().get(j))) {
                        died();
                    }
                    if (enemy.intersects(player.getBullets().get(r))) {
                        removableEnemies.add(enemy);
                        removableBullets.add(player.getBullets().get(r));
                    }
                }
            }
        }
        player.getBullets().removeAll(removableBullets);
        enemies.removeAll(removableEnemies);
    }

    /**
     * Checks collisions between a player and list of asteroids, and acts accordingly.
     *
     * @param player    player to check collisions with
     * @param asteroids list of asteroids ot be checked
     */
    private void checkPlayerAsteroidCollisions(Player player, List<Entity> asteroids) {
        List<Asteroid> removableAsteroids = new ArrayList<>();
        List<Entity> removableBullets = new ArrayList<>();

        // Check players and asteroids
        for (int i = 0; i < asteroids.size(); i++) {
            for (int j = 0; j < player.getBullets().size(); j++) {
                if (player.getBullets().get(j).intersects(asteroids.get(i))) {
                    Asteroid asteroid = (Asteroid) asteroids.get(i);
                    asteroids.addAll(asteroid.split());
                    scoreProcessor.updateScore(player, true, asteroid.getLevel());

                    removableAsteroids.add(asteroid);
                    removableBullets.add(player.getBullets().get(j));
                }
                if (player.intersects(asteroids.get(i))) {
                    died();
                }
            }
        }
        asteroids.removeAll(removableAsteroids);
        player.getBullets().removeAll(removableBullets);
    }

    /**
     * Generates a new wave.
     */
    public void newWave() {
        int asteroidsNum = wave * 3 + 5;
        int enemiesNum = (wave > 3) ? wave / 2 + 1 : 0;

        asteroids = getAsteroids(asteroidsNum);
        enemies = getEnemies(enemiesNum);
    }

    /**
     * Initializes the players.
     *
     * @param numberOfPlayers number of players to be initialized
     * @return list of initialized players
     */
    private List<Entity> initializePlayers(int numberOfPlayers) {
        List<Entity> players = new ArrayList<>();

        for (int i = 0; i < numberOfPlayers; i++) {
            float playerSize = 50f;
            player = new Player(viewport.getMinWorldWidth() / 2 - playerSize / 2,
                    viewport.getMinWorldHeight() / 2 - playerSize / 2, playerSize);
            player.setSprite("Images/player.png");
            player.setScale(.6f);
            player.setShapeRenderer(app.getShapeRenderer());
            players.add(player);
        }
        return players;
    }

    /**
     * Calls if the player has died. Currently calls the DeathScreen.
     */
    public void died() {
        getApp().setScreen(new DeathScreen(getApp(), scoreProcessor.getScore(player)));
    }

    @Override
    public void show() {
        this.scoreProcessor = new ScoreProcessor();
        this.statusBatch = new SpriteBatch();
        this.statusFont = new BitmapFont();

        this.statusFont.getData().setScale(.7f);

        OrthographicCamera camera = new OrthographicCamera();
        ExtendViewport viewport = new ExtendViewport(1280, 720, camera);
        Gdx.graphics.setWindowedMode(
                (int) viewport.getMinWorldWidth(), (int) viewport.getMinWorldHeight());

        players = initializePlayers(1);
        scoreProcessor.addPlayer((Player) players.get(0));
        newWave();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keyCode) {
                handleKeyDown(keyCode);
                return true;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        inputs.set(0, Gdx.input.isKeyPressed(Input.Keys.UP));
        inputs.set(1, Gdx.input.isKeyPressed(Input.Keys.RIGHT));
        inputs.set(3, Gdx.input.isKeyPressed(Input.Keys.LEFT));

        if (enemies.size() == 0 && asteroids.size() == 0) {
            wave++;
            newWave();
        }

        // Update all the elements
        updateEntities(inputs, players);
        updateEntities(null, asteroids);
        updateEntities(null, enemies);
        renderEntities(players);
        renderEntities(enemies);
        renderEntities(asteroids);

        // Check for collisions
        checkAllCollisions(players, asteroids, enemies);

        // Update score
        statusBatch.begin();
        statusFont.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        statusFont.draw(statusBatch, "Score: " + scoreProcessor.getScore(player),
                20,   450);
        statusFont.draw(statusBatch, "Wave: " + wave,
                570, 450);
        statusBatch.end();

        inputs.set(4, false);
        inputs.set(2, false);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        app.getBatch().setProjectionMatrix(app.getCamera().combined);
    }

    @Override
    public void dispose() {
        app.getBatch().dispose();
    }

    public ScoreProcessor getScoreProcessor() {
        return scoreProcessor;
    }

    public void setScoreProcessor(ScoreProcessor scoreProcessor) {
        this.scoreProcessor = scoreProcessor;
    }
}
