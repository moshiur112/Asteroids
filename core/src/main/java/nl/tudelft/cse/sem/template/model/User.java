package nl.tudelft.cse.sem.template.model;

import java.util.Objects;

/**
 * Class that represents a user.
 */
public class User {
    private transient String username;
    private transient String password;
    private int highScore;

    /**
     * Constructor.
     *
     * @param username  username
     * @param password  password
     * @param highScore the high score
     */
    public User(String username, String password, int highScore) {
        this.username = username;
        this.password = password;
        this.highScore = highScore;
    }

    /**
     * Getter for the username.
     *
     * @return username
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * getter for the password.
     *
     * @return password
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for the high score.
     *
     * @return highScore
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Setter for the highScore.
     *
     * @param highScore new highScore
     */
    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return getUsername().equals(user.getUsername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUsername(), getPassword(), getHighScore());
    }
}
