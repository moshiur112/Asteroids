package nl.tudelft.cse.sem.template.views.authentication;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.cse.sem.template.App;
import nl.tudelft.cse.sem.template.views.MainMenuScreen;


public class LoginScreen extends AuthenticationScreen {

    public LoginScreen(App app) {
        super(app);
    }

    @Override
    public List<Button> initButtons() {
        List<Button> buttons = new ArrayList<>();

        buttons.add(new TextButton("Login", skin, "default"));
        buttons.add(new TextButton("Signup", skin, "default"));

        buttons.get(0).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                logInUser();
            }
        });

        buttons.get(1).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getApp().setScreen(new SignUpScreen(getApp()));
            }

        });
        return buttons;
    }

    /**
     * Handle when a user clicks log in.
     */
    private void logInUser() {
        handleLogin(username.getText(), password.getText());
    }

    /**
     * Method attempts to login the user using the controller.
     * @param username the given username
     * @param password the given password
     */
    public void handleLogin(String username, String password) {
        if (!controller.loginUser(username, password)) {
            System.out.println("Sign in failed");
            // TODO moshi display message
            return;
        }
        System.out.println("Sign in success");
        getApp().setScreen(new MainMenuScreen(getApp()));
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}
