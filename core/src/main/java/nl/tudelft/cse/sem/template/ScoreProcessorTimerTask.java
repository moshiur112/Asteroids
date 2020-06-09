package nl.tudelft.cse.sem.template;

import java.util.TimerTask;

public class ScoreProcessorTimerTask extends TimerTask {
    private transient ScoreProcessor scoreProcessor;

    public ScoreProcessorTimerTask(ScoreProcessor scoreProcessor) {
        this.scoreProcessor = scoreProcessor;
    }

    @Override
    public void run() {
        synchronized (scoreProcessor) {
            scoreProcessor.updateAll();
        }
    }
}
