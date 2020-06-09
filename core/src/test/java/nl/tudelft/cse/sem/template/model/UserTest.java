package nl.tudelft.cse.sem.template.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;


class UserTest {
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final int HIGH_SCORE = 0;


    @Test
    public void testEqualsSameReference() {
        User user = new User(USERNAME, PASSWORD, HIGH_SCORE);
        assertEquals(user, user);
    }

    @Test
    public void testEqualsDifferentReference() {
        User user1 = new User(USERNAME, PASSWORD, HIGH_SCORE);
        User user2 = new User(USERNAME, PASSWORD, HIGH_SCORE);

        assertEquals(user1, user2);
    }

    @Test
    public void testEqualsNull() {
        User user = new User(USERNAME, PASSWORD, HIGH_SCORE);
        assertNotEquals(user, null);
    }

    @Test
    public void testEqualsDifferentClass() {
        User user = new User(USERNAME, PASSWORD, HIGH_SCORE);
        Object object = DbConnection.getConnection();
        assertNotEquals(user, object);
    }

}