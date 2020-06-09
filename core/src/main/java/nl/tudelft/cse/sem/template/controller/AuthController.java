package nl.tudelft.cse.sem.template.controller;

import nl.tudelft.cse.sem.template.config.Config;
import nl.tudelft.cse.sem.template.model.DbConnection;
import nl.tudelft.cse.sem.template.model.User;

public class AuthController {
    private static AuthController _controller;
    private transient DbConnection connection;

    private AuthController() {
    }

    public static AuthController getInstance() {
        return getInstanceWithDbConnection(DbConnection.getConnection());
    }

    protected static AuthController getInstanceWithDbConnection(DbConnection connection) {
        if (_controller == null) {
            _controller = new AuthController();
            _controller.connection = connection;
        }
        return _controller;
    }

    public boolean handleSignUp(String username, String password) {
        return connection.createUser(username, password);
    }

    /**
     * Handles logging in a user.
     *
     * @param username username
     * @param password password
     * @return the just logged in user
     */
    public boolean loginUser(String username, String password) {
        User user = connection.loginUser(username, password);
        if (user == null) {
            return false;
        }
        Config.setUser(user);
        return true;
    }
}
