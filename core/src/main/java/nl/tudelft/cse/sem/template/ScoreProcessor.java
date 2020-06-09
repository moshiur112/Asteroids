package nl.tudelft.cse.sem.template;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import nl.tudelft.cse.sem.template.entities.Player;

/**
 * A class to handle processing the score.
 */
public class ScoreProcessor {
    private static final Map<Player, Integer> players =
            Collections.synchronizedMap(new HashMap<>());
    private transient int idleScore;
    private transient int timerPeriod;
    private transient Timer timer;

    // How much the score for each asteroid level increases
    private transient int scoreRamp;

    /**
     * Constructor initializes the score processor.
     */
    public ScoreProcessor() {
        timer = new Timer();
        timerPeriod = 1000;
        timer.scheduleAtFixedRate(new ScoreProcessorTimerTask(this), 0, timerPeriod);

        idleScore = 1;
        scoreRamp = 10;
    }

    /**
     * Adds a player to the game.
     * @param player the player to add
     */
    public void addPlayer(Player player) {
        synchronized (players) {
            if (player == null || players.containsKey(player)) {
                return;
            }
            players.put(player, 0);
        }
    }

    /**
     * Updates the score of a player possibly when he hits an asteroid or just as time advances.
     * @param player the player
     * @param hit indicating whether the increase is because of a hit
     * @param level the current level
     * @return the new score
     */
    public int updateScore(Player player, boolean hit, int level) {
        synchronized (players) {
            int newScore = this.idleScore + getScore(player);

            newScore += (hit) ? this.scoreRamp * level : 0;
            this.setScore(player, newScore);

            return newScore;
        }
    }

    /**
     * Score getter for players.
     * @param player the player to get score from
     * @return the score of the player
     */
    public int getScore(Player player) {
        synchronized (players) {
            return players.get(player);
        }
    }

    /**
     * Setter for the score of a specific player.
     * @param player the player
     * @param score the new score
     */
    public void setScore(Player player, Integer score) {
        synchronized (players) {
            players.replace(player, score);
        }
    }

    /**
     * Handles updating all the players scores.
     */
    @SuppressWarnings("PMD") // Invalid UR anomaly
    public List<Integer> updateAll() {
        synchronized (players) {
            Set<Map.Entry<Player, Integer>> set = players.entrySet();
            LinkedList<Integer> scores = new LinkedList<>();

            for (Map.Entry<Player, Integer> element : set) {
                scores.add(updateScore(element.getKey(), false, 0));
            }
            return scores;
        }
    }
}
