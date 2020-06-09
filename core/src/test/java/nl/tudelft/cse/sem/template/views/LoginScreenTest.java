package nl.tudelft.cse.sem.template.views;

import static org.mockito.Matchers.any;

import nl.tudelft.cse.sem.template.App;
import nl.tudelft.cse.sem.template.controller.AuthController;
import nl.tudelft.cse.sem.template.views.authentication.LoginScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


class LoginScreenTest {
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final int HIGH_SCORE = 2;

    @Mock
    private transient AuthController controller;

    @Mock
    transient App mockApp;

    @InjectMocks
    transient LoginScreen loginScreen;

    @BeforeEach
    public void setUp() {
        mockApp = Mockito.mock(App.class);
        loginScreen = new LoginScreen(mockApp);
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleUserLoginSuccessfulTest() {
        Mockito.doReturn(true).when(controller).loginUser(USERNAME, PASSWORD);
        Mockito.doNothing().when(mockApp).setScreen(any(MainMenuScreen.class));

        loginScreen.handleLogin(USERNAME, PASSWORD);

        Mockito.verify(mockApp, Mockito.times(1))
                .setScreen(any(MainMenuScreen.class));
    }

    @Test
    public void handleUserLoginNotSuccessfulTest() {
        // controller.loginUser(username, password)
        Mockito.doReturn(false).when(controller).loginUser(USERNAME, PASSWORD);

        loginScreen.handleLogin(USERNAME, PASSWORD);
        Mockito.verify(mockApp, Mockito.times(0))
                .setScreen(any(MainMenuScreen.class));
    }


}