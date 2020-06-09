package nl.tudelft.cse.sem.template.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;



@SuppressWarnings("PMD")
public class DbConnectionTest {

    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final int HIGH_SCORE = 0;

    @InjectMocks
    transient DbConnection dbConnection;

    @Mock
    transient Connection connection;

    /**
     * Set up for the tests.
     */
    @BeforeEach
    public void setUp() {
        dbConnection = DbConnection.getConnection();
        connection = Mockito.mock(Connection.class, RETURNS_DEEP_STUBS);
        //you have this line to make line 33 work
        MockitoAnnotations.initMocks(this);
    }
//makes sure that the connection field is not null and the mock is injected properly
    @Test
    public void createsSingletonWhenNull() {
        Assert.assertNotNull(DbConnection.getConnection());
    }

    @Test
    public void singletonReturnsSameWhenAlreadyExists() {
        DbConnection dbConnection = DbConnection.getConnection();
        assertEquals(dbConnection, DbConnection.getConnection());
    }


    @Test
    public void testLogInUserDoesNotExist() throws SQLException {
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.doNothing().when(mockPreparedStatement).setString(1, USERNAME);

        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.doReturn(false).when(mockResultSet).next();
        Mockito.doReturn(mockResultSet).when(mockPreparedStatement).executeQuery();
//        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        Mockito.doReturn(mockPreparedStatement).when(connection).prepareStatement(anyString());

        mockResultSet.close();
        Assert.assertNull(dbConnection.loginUser(USERNAME, PASSWORD));
    }

    @Test
    public void testLogInUserExistsButPasswordNotVerified() throws SQLException {
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.doNothing().when(mockPreparedStatement).setString(1, USERNAME);

        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.doReturn(true).when(mockResultSet).next();
        Mockito.doReturn(USERNAME).when(mockResultSet).getString("username");
        Mockito.doReturn(PASSWORD).when(mockResultSet).getString("password");
        Mockito.doReturn(HIGH_SCORE).when(mockResultSet).getInt("highScore");

        // Return the mockResultSet when mockPreparedStatement.executeQuery() is called
        Mockito.doReturn(mockResultSet).when(mockPreparedStatement).executeQuery();
//        Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        
        Mockito.doReturn(mockPreparedStatement).when(connection).prepareStatement(anyString());

        mockResultSet.close();
        Assert.assertNull(dbConnection.loginUser(USERNAME, PASSWORD));
    }

    @Test
    public void testLogInUserExistsWeirdDiffResultFromDb() throws SQLException {
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.doNothing().when(mockPreparedStatement).setString(1, USERNAME);

        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.doReturn(true).when(mockResultSet).next();
        Mockito.doReturn(USERNAME + "Oeps not same").when(mockResultSet).getString("username");

        // Return the mockResultSet when mockPreparedStatement.executeQuery() is called
        Mockito.doReturn(mockResultSet).when(mockPreparedStatement).executeQuery();

        Mockito.doReturn(mockPreparedStatement).when(connection).prepareStatement(anyString());

        Assert.assertNull(dbConnection.loginUser(USERNAME, PASSWORD));
    }

    @Test
    public void testLogInUserExistsWithPasswordVerification() throws SQLException {
        String hashedPassword = BCrypt.withDefaults().hashToString(5, PASSWORD.toCharArray());
        // Mock PreparedStatement
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.doNothing().when(mockPreparedStatement).setString(1, USERNAME);

        //Mock the resul set from prepared set
        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.doReturn(true).when(mockResultSet).next();
        Mockito.doReturn(USERNAME).when(mockResultSet).getString("username");
        Mockito.doReturn(hashedPassword).when(mockResultSet).getString("password");
        Mockito.doReturn(HIGH_SCORE).when(mockResultSet).getInt("highScore");

        // Return the mockResultSet when mockPreparedStatement.executeQuery() is called
        Mockito.doReturn(mockResultSet).when(mockPreparedStatement).executeQuery();

        Mockito.doReturn(mockPreparedStatement).when(connection).prepareStatement(anyString());

        Assert.assertEquals(
                dbConnection.loginUser(USERNAME, PASSWORD),
                new User(
                        USERNAME,
                        hashedPassword,
                        HIGH_SCORE
                )
        );
    }

    @Test
    public void setHighScoreLowerScoreTest() {
        int lowerScore = 2;
        int higherScore = 10;
        User user = new User(USERNAME, PASSWORD, higherScore);
        assertEquals(dbConnection.setHighScore(user, lowerScore), higherScore);
    }

    @Test
    public void setHighScoreActuallyHigherScore() throws SQLException {
        int newHigherScore = 10;
        User user = new User(USERNAME, PASSWORD, HIGH_SCORE);
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.doReturn(mockPreparedStatement).when(connection).prepareStatement(anyString());
        assertEquals(dbConnection.setHighScore(user, newHigherScore), newHigherScore);
    }

    @Test
    public void setHighScoreNullUserGiven() {
        assertEquals(dbConnection.setHighScore(null, 1), -1);
    }

    @Test
    public void createUserUserExistsTest() throws SQLException {
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.doReturn(mockPreparedStatement).when(connection).prepareStatement(anyString());

        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.doReturn(false).when(mockResultSet).next();

        Mockito.doReturn(mockResultSet).when(mockPreparedStatement).executeQuery();
        assertTrue(dbConnection.createUser(USERNAME, PASSWORD));
    }

    @Test
    public void createUserSuccessTest() throws SQLException {
        PreparedStatement mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        Mockito.doReturn(mockPreparedStatement).when(connection).prepareStatement(anyString());

        ResultSet mockResultSet = Mockito.mock(ResultSet.class);
        Mockito.doReturn(true).when(mockResultSet).next();
        Mockito.doReturn(USERNAME).when(mockResultSet).getString("username");

        Mockito.doReturn(mockResultSet).when(mockPreparedStatement).executeQuery();
        assertFalse(dbConnection.createUser(USERNAME, PASSWORD));
    }

    @Test
    public void getUserWithNullResultSet() throws SQLException {
        Mockito.doThrow(SQLException.class).when(connection).prepareStatement(anyString());
        User user = dbConnection.getUser("bla");
        assertNull(user);
    }
}
