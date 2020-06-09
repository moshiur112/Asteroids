package nl.tudelft.cse.sem.template.config;

import nl.tudelft.cse.sem.template.model.User;

public class Config {
    private static User user;


    public static boolean isUserLoggedIn() {
        return user != null;
    }


    public static void setUser(User loggedInUser) {
        user = loggedInUser;
    }

    public static User getLoggedInUser() {
        return user;
    }

}
