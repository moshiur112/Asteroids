package nl.tudelft.cse.sem.template.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import nl.tudelft.cse.sem.template.App;
import nl.tudelft.cse.sem.template.config.Config;
import nl.tudelft.cse.sem.template.model.User;
import nl.tudelft.cse.sem.template.views.score.HighScoreScreen;

/**
 * The main menu screen that the user gets when he opens the app.
 */
public class MainMenuScreen extends AppScreen {

    transient Texture mainMenuBG;
    transient Stage stage;
    transient Skin skin;

    /**
     * Constructor for the screen.
     *
     * @param app the application
     */
    public MainMenuScreen(App app) {
        super(app);
    }

    /**
     * Initial display method.
     */
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
        mainMenuBG = new Texture(Gdx.files.internal("Images/small_star_background.jpg"));

        if (!Config.isUserLoggedIn()) {
            // TODO maybe throw back to login screen
            System.out.println("throw back to login");
        }

        // create table
        Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        // Buttons
        final TextButton playButton = new TextButton("Play", skin, "default");
        final TextButton highScoreButton = new TextButton("HighScores", skin, "default");

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getApp().setScreen(new AliveScreen(getApp()));
            }
        });

        highScoreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getApp().setScreen(new HighScoreScreen(getApp()));
            }
        });

        // Welcome user
        User user = Config.getLoggedInUser();
        Label username = new Label("Welcome " + user.getUsername() + "!", skin);
        username.setAlignment(Align.center);

        // Make table
        table.add(username).spaceBottom(50).width(250).height(70);
        table.row();

        table.add(playButton).spaceBottom(20).width(250).height(100);
        table.row();

        table.add(highScoreButton).spaceBottom(20).width(200).height(80);
        table.row();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Render method that updates every time frame.
     *
     * @param delta the time frame
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        app.getBatch().begin();

        app.getBatch().draw(mainMenuBG, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        app.getBatch().end();

        // TODO are they in the right place?
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    /**
     * Method that gets called when you hide the screen.
     */
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
