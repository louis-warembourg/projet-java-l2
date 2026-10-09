package towerdefense.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

public class AskTest {
    
    private Ask ask;
    
    @BeforeEach
    public void before() {
        ask = new Ask();
    }
    
    /**
     * Helper method to simulate user input
     */
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
        ask = new Ask(); // Recréer Ask avec le nouveau flux d'entrée
    }
    
    @Test
    public void testIsNumberWithValidNumber() {
        assertTrue(ask.isNumber("123"));
        assertTrue(ask.isNumber("0"));
        assertTrue(ask.isNumber("999999"));
    }
    
    @Test
    public void testIsNumberWithInvalidInput() {
        assertFalse(ask.isNumber("123a"));
        assertFalse(ask.isNumber(""));
        assertFalse(ask.isNumber("abc"));
    }
    
    
}