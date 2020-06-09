package nl.tudelft.cse.sem.template.views.score;

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
import nl.tudelft.cse.sem.template.views.AppScreen;
import nl.tudelft.cse.sem.template.views.MainMenuScreen;
import nl.tudelft.cse.sem.template.views.authentication.LoginScreen;

public abstract class ScoreScreen extends AppScreen {
    protected transient Texture texture;
    protected transient Stage stage;
    protected transient Skin skin;
    protected transient String mainMessage;
    protected transient int scoreVal;

    /**
     * Initializes the end of game page.
     *
     * @param app         application running
     * @param mainMessage the main menu
     * @param scoreVal    the value of the current score
     */
    public ScoreScreen(App app, String mainMessage, int scoreVal) {
        super(app);
        this.scoreVal = scoreVal;
        this.mainMessage = mainMessage;
    }


    /**
     * A method that is called when showing content on the UI.
     */
    @Override
    public void show() {
        // Make sure user is logged in otherwise kick to log in screen
        ensureUserLoggedIn();

        texture = new Texture("Images/small_star_background.jpg");
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json")); // maybe no

        User user = Config.getLoggedInUser();

        // Check whether this is the highest score so far
        if (user.getHighScore() < scoreVal) {
            user.setHighScore(scoreVal);
        }

        Table table = createTable(scoreVal, mainMessage); // TODO main message from other classes
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }


    /**
     * Called every timeframe to handle changes to the ui.
     *
     * @param delta the timeframe
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        getApp().getBatch().begin();

        getApp().getBatch().draw(texture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        getApp().getBatch().end();

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }


    /**
     * Creates a table view with the given parameters.
     *
     * @param scoreVal    the value of the score to be displayed
     * @param mainMessage Main message in center top of screen
     * @return the table object
     */
    private Table createTable(int scoreVal, String mainMessage) {
        // create table
        Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);

        Label message = new Label(mainMessage, skin);
        message.setAlignment(Align.center);

        // Display score
        Label score = new Label("Score: " + scoreVal, skin);
        score.setAlignment(Align.center);

        // Make high score menu button
        final TextButton mainMenu = new TextButton("Main Menu", skin, "default");

        mainMenu.addListener(listenerSwitchToMainMenu());

        // Adding Heading-Buttons to the cue
        table.add(message).width(460).height(110);
        table.row();

        table.add(score).spaceBottom(100).width(460).height(110);
        table.row();

        table.add(mainMenu).spaceBottom(20).width(200).height(100);
        table.row();

        return table;
    }

    /**
     * A button listener that switches to main menu.
     *
     * @return the listener
     */
    private ClickListener listenerSwitchToMainMenu() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getApp().setScreen(new MainMenuScreen(getApp()));
            }
        };
    }

    /**
     * Makes sure the user is logged in otherwise the screen goes back to the login screen.
     */
    protected void ensureUserLoggedIn() {
        // Make sure user is authenticated
        if (!Config.isUserLoggedIn()) {
            System.out.println("throw back to login");
            getApp().setScreen(new LoginScreen(getApp()));
        }
    }
}
