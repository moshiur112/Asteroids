package nl.tudelft.cse.sem.template.views.score;

import nl.tudelft.cse.sem.template.App;
import nl.tudelft.cse.sem.template.config.Config;

public class HighScoreScreen extends ScoreScreen {
    public HighScoreScreen(App app) {
        super(app, "Welcome!!", Config.getLoggedInUser().getHighScore());
    }

}
