package nl.tudelft.cse.sem.template;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import nl.tudelft.cse.sem.template.views.authentication.LoginScreen;


public class App extends Game {
    private transient OrthographicCamera camera;
    private transient SpriteBatch batch;
    private transient ShapeRenderer shapeRenderer;
    private transient BitmapFont font;
    private transient int width = 1280;
    private transient int height = 720;
    private transient Music inGameSound;


    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        font = new BitmapFont();
        setScreen(new LoginScreen(this));
        inGameSound = Gdx.audio.newMusic(Gdx.files.internal("Sound/totally_not_halo.mp3"));
        inGameSound.setLooping(true);
        inGameSound.play();
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public BitmapFont getFont() {
        return font;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
