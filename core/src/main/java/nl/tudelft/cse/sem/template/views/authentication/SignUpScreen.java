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


public class SignUpScreen extends AuthenticationScreen {

    public SignUpScreen(App app) {
        super(app);
    }

    @Override
    public List<Button> initButtons() {
        List<Button> buttons = new ArrayList<>();
        buttons.add(new TextButton("Signup", skin, "default"));
        buttons.add(new TextButton("Back To Login Page", skin, "default"));

        buttons.get(0).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                signUp();
            }
        });

        buttons.get(1).addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                getApp().setScreen(new LoginScreen(getApp()));
            }
        });
        return buttons;
    }

    /**
     * Method attempts to signup user with the given parameters.
     * @param username the given username
     * @param password the given password
     */
    public void handleSignUp(String username, String password) {
        boolean signUpSuccess = controller.handleSignUp(username, password);

        if (!signUpSuccess) {
            System.out.println("Signup failed!!");
            // TODO moshi message?
            return;
        }
        System.out.println("User signup success");

        if (!controller.loginUser(username, password)) {
            System.out.println("User sign in failed!!");
            // TODO moshi message?
            return;
        }
        getApp().setScreen(new MainMenuScreen(getApp()));
    }

    /**
     * Method to handle signing a user up when he clicks sign up button.
     */
    public void signUp() {
        handleSignUp(username.getText(), password.getText());
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

}
