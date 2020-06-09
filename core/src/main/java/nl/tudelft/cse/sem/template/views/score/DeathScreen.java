package nl.tudelft.cse.sem.template.views.score;

import nl.tudelft.cse.sem.template.App;

public class DeathScreen extends ScoreScreen {

    /**
     * Constructor initializes DeathScreen.
     * @param app running app
     * @param finalScore the final score of the user before dying
     */
    public DeathScreen(App app, int finalScore) {
        super(app, "GAME OVER", finalScore);
    }

}
