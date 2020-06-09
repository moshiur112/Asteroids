package nl.tudelft.cse.sem.template.views.authentication;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.util.List;
import nl.tudelft.cse.sem.template.App;
import nl.tudelft.cse.sem.template.controller.AuthController;
import nl.tudelft.cse.sem.template.views.AppScreen;


public abstract class AuthenticationScreen extends AppScreen {

    protected transient TextField username;
    protected transient TextField password;
    protected transient AuthController controller = AuthController.getInstance();
    transient Texture backGround;
    transient Stage stage;
    transient Skin skin;

    /**
     * Constructor initializes the authentication screen with the app running.
     * @param app the tunning app
     */
    public AuthenticationScreen(App app) {
        super(app);


    }

    /**
     * Method called when you want to show the elements on the UI.
     */
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("neon/skin/neon-ui.json"));
        backGround = new Texture(Gdx.files.internal("Images/small_star_background.jpg"));
        username = new TextField("", skin);
        password = new TextField("", skin);
        password.setPasswordMode(true);
        password.setPasswordCharacter('*');

        // create table
        Table table = new Table(skin);
        table.setFillParent(true);
        stage.addActor(table);


        // Adding Heading-Buttons to the cue
        table.padTop(20);

        Label usernameLabel = new Label("Username: ", skin);
        table.add(usernameLabel).spaceBottom(30).height(50).padLeft(90);
        table.add(username).spaceBottom(30).width(300).height(50).expandX().padRight(60);
        table.row();

        Label passwordLabel = new Label("Password: ", skin);
        table.add(passwordLabel).spaceBottom(50).height(50).padLeft(90);
        table.add(password).spaceBottom(50).width(300).height(50).expandX().padRight(60);
        table.row();

        List<Button> buttons = initButtons();

        table.add(buttons.get(0)).spaceBottom(30).width(250).height(80).colspan(2);
        table.row();

        table.add(buttons.get(1)).spaceBottom(20).width(200).height(70).colspan(2);
        table.row();

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        app.getBatch().begin();
        app.getBatch().draw(backGround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        app.getBatch().end();

        stage.act(Gdx.graphics.getDeltaTime());


        stage.draw();
    }

    /**
     * Initialize the buttons that have to be used and the functions they execute.
     *
     * @return list of initialized buttons
     */
    public abstract List<Button> initButtons();
}
