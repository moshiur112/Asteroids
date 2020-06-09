package nl.tudelft.cse.sem.template.views;

import static org.mockito.Matchers.any;

import nl.tudelft.cse.sem.template.App;
import nl.tudelft.cse.sem.template.controller.AuthController;
import nl.tudelft.cse.sem.template.views.authentication.SignUpScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


class SignupScreenTest {

    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final int HIGH_SCORE = 2;

    @Mock
    transient AuthController mockController;

    @InjectMocks
    transient SignUpScreen signupScreen;

    @Mock
    transient App mockApp;

    @BeforeEach
    public void setUp() {
        mockApp = Mockito.mock(App.class);
        signupScreen = new SignUpScreen(mockApp);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleSignUpSuccessfulTest() {
        Mockito.doNothing().when(mockApp).setScreen(any(MainMenuScreen.class));

        Mockito.doReturn(true).when(mockController).handleSignUp(USERNAME, PASSWORD);
        Mockito.doReturn(true).when(mockController).loginUser(USERNAME, PASSWORD);


        signupScreen.handleSignUp(USERNAME, PASSWORD);
        Mockito.verify(mockApp, Mockito.times(1)).setScreen(any(MainMenuScreen.class));
    }

    @Test
    public void handleSignUpNotSuccessfulTest() {
        Mockito.doNothing().when(mockApp).setScreen(any(MainMenuScreen.class));

        Mockito.doReturn(false).when(mockController).handleSignUp(USERNAME, PASSWORD);

        signupScreen.handleSignUp(USERNAME, PASSWORD);

        Mockito.verify(mockApp, Mockito.times(0))
                .setScreen(any(MainMenuScreen.class));
    }

    @Test
    public void handleSignUpSuccessfulButLoginNot() {
        Mockito.doNothing().when(mockApp).setScreen(any(MainMenuScreen.class));

        Mockito.doReturn(true).when(mockController).handleSignUp(USERNAME, PASSWORD);
        Mockito.doReturn(false).when(mockController).loginUser(USERNAME, PASSWORD);


        signupScreen.handleSignUp(USERNAME, PASSWORD);
        Mockito.verify(mockApp, Mockito.times(0)).setScreen(any(MainMenuScreen.class));
    }

}