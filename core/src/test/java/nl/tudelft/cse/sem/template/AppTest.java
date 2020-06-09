package nl.tudelft.cse.sem.template;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;



public class AppTest {
    transient App app = new App();

    @Test
    public void simpleTest() {
        assertEquals(720, app.getHeight());
    }
}
