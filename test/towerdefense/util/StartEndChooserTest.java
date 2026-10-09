package towerdefense.util;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

//import java.util.*;

public class StartEndChooserTest{
    private Object[][] tab;
    private int[][] start_end;

    @BeforeEach
    public void before(){
        this.tab = new Object[10][12];
        this.start_end = new int[][]{{0, 3}, {7, 11}};
    }

    @Test
    public void positionIsValidIfOnBorder(){
        int[] pos = StartEndChooser.generateCoordinates(this.tab);
        int r = pos[0];
        int c = pos[1];
        assertTrue(StartEndChooser.isValidBorder(r, c, this.tab));
    }

    @Test
    public void startEndValidIfNotInSameBorder(){
        int[][] start_end = StartEndChooser.generateStartEnd(this.tab);
        int[] start = start_end[0];
        int[] end = start_end[1];
        assertFalse(StartEndChooser.sameBorder(start, end, this.tab));
    }

    @Test
    public void startRowCanBeAccessed(){
        assertEquals(3, StartEndChooser.getStartRow(this.start_end));
    }

    @Test
    public void startColCanBeAccessed(){
        assertEquals(0, StartEndChooser.getStartCol(this.start_end));
    }

    @Test
    public void endRowCanBeAccessed(){
        assertEquals(11, StartEndChooser.getEndRow(this.start_end));
    }

    @Test
    public void endColCanBeAccessed(){
        assertEquals(7, StartEndChooser.getEndCol(this.start_end));
    }
}