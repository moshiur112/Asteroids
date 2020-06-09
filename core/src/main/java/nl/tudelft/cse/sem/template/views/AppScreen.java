package nl.tudelft.cse.sem.template.views;

import com.badlogic.gdx.ScreenAdapter;
import nl.tudelft.cse.sem.template.App;

public class AppScreen extends ScreenAdapter {
    protected App app;

    public AppScreen(App app) {
        this.app = app;
    }

    public App getApp() {
        return app;
    }

    public void setApp(App app) {
        this.app = app;
    }
}
