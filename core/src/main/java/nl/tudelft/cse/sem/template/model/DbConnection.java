package nl.tudelft.cse.sem.template.model;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Class that represents the connection to the database.
 */
public class DbConnection {
    private static DbConnection singletonConnection; // Singleton
    transient Connection connection;

    private DbConnection() {
    }

    /**
     * Singleton constructor sorta.
     *
     * @return Connection object to db
     */
    public static DbConnection getConnection() {
        if (singletonConnection == null) {
            singletonConnection = new DbConnection();
            singletonConnection.createConnection();
        }
        return singletonConnection;
    }

    /**
     * Establishes a connection with the database.
     */
    public void createConnection() {
        try {
            DbConfig config = new DbConfig();
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = String.format(
                    "jdbc:mysql://%s/%s?user=%s&password=%s&serverTimezone=UTC",
                    config.host, config.dbName, config.dbUser, config.dbPassword
            );
            this.connection = DriverManager.getConnection(url);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Class not found jdbc or sql exception happened");
            e.printStackTrace();
        }
    }

    /**
     * Retrieves user from database if he exists.
     *
     * @param username username
     * @return the user if he exists, otherwise returns null.
     */
    @SuppressWarnings("PMD")
    protected User getUser(String username) {
        User user = null;
        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement = connection
                .prepareStatement("SELECT * FROM User WHERE username = ?")) {

            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next() && resultSet.getString("username").equals(username)) {
                user = new User(
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getInt("highScore")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return user;
        } finally {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return user;
    }

    /**
     * Checks if the password that is given is same as the hashed one.
     *
     * @param givenPassword password user gives
     * @param password      hashed password from db
     * @return boolean indicating whether it was successfully verified
     */
    private boolean verifyPassword(String givenPassword, String password) {
        return BCrypt.verifyer().verify(givenPassword.toCharArray(), password).verified;
    }



    /**
     * Log in user by checking credentials.
     *
     * @param username username
     * @param password password
     * @return the User object retrieved from database
     */
    public User loginUser(String username, String password) {
        User user = getUser(username);

        return (user != null && verifyPassword(password, user.getPassword())) ? user : null;
    }

    /**
     * Creates a user.
     *
     * @param username the username
     * @param password the password
     * @return boolean indicating whether user has been created successfully
     */
    public boolean createUser(String username, String password) {
        if (getUser(username) != null) {
            System.out.println("User exists already with this username!");
            return false;
        }
        try (PreparedStatement preparedStatement = connection
                .prepareStatement("INSERT INTO User VALUES (?, ?, 0)")) {
            preparedStatement.setString(1, username);
            String hashedPassword = BCrypt.withDefaults().hashToString(5, password.toCharArray());
            preparedStatement.setString(2, hashedPassword);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Sets the high score of the user to the new one if it was actually higher.
     *
     * @param user  logged in user
     * @param score new score
     * @return the highest score, -1 if user does not exist
     */
    public int setHighScore(User user, int score) {
        if (user == null) {
            return -1;
        }
        int oldScore = user.getHighScore();

        if (score > oldScore) {
            user.setHighScore(score);

            try (PreparedStatement preparedStatement = connection
                    .prepareStatement("SELECT * FROM User WHERE username = ?")) {
                preparedStatement.setInt(1, score);
                preparedStatement.setString(2, user.getUsername());
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return user.getHighScore();
    }
}
