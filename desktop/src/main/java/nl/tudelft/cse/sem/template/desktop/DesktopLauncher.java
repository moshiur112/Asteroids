package nl.tudelft.cse.sem.template.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import nl.tudelft.cse.sem.template.App;

public class DesktopLauncher {
    /**
     * The main run method of the desktop version of the app.
     *
     * @param arg commandline arguments
     */
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        new LwjglApplication(new App(), config);
    }
}
