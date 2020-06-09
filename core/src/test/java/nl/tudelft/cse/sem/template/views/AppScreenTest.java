package nl.tudelft.cse.sem.template.views;

import static org.junit.jupiter.api.Assertions.assertEquals;

import nl.tudelft.cse.sem.template.App;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test for the AppScreen class.
 */
class AppScreenTest {
    private transient AppScreen appScreen;
    private transient App app;

    /**
     * Set up initialization for the test.
     */
    @BeforeEach
    public void setUp() {
        app = new App();
        appScreen = new AppScreen(app);
    }

    /**
     * Test the App getter.
     */
    @Test
    public void testAppGetter() {
        System.out.println(appScreen);
        System.out.println(app);
        assertEquals(app, appScreen.getApp());
    }

    /**
     * Test the App setter.
     */
    @Test
    public void testSetter() {
        App app2 = new App();
        appScreen.setApp(app2);
        assertEquals(app2, appScreen.getApp());
    }


}