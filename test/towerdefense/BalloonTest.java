package towerdefense;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import towerdefense.cell.*;
import towerdefense.util.Direction;

import java.util.*;

public class BalloonTest {
    
    private Balloon b1, b2, b3, b4;
    private ArrayList<Cell> testPath;
    private Balloon randomBalloon;
    
    @BeforeEach
    public void setUp() {
        b1 = new Balloon(30, 1, 0, 1, Direction.droite);
        b2 = new Balloon(40, 2, 0, 3, Direction.bas);
        b3 = new Balloon(80, 4, 3, 5, Direction.gauche);
        b4 = new Balloon(50, 1, 5, 3, Direction.haut);
        
        testPath = new ArrayList<>();
        testPath.add(new BalloonCell(0, 0, new ArrayList<>(), 0));
        testPath.add(new BalloonCell(1, 0, new ArrayList<>(), 1));
        testPath.add(new BalloonCell(1, 1, new ArrayList<>(), 2));
        testPath.add(new BalloonCell(2, 1, new ArrayList<>(), 3));
        randomBalloon = new Balloon(2, 3, testPath);
    }
    
    @Test
    public void testConstructors() {

        assertEquals(0, b1.getX(), "The X coordinate must be correct");
        assertEquals(1, b1.getY(), "The Y coordinate must be correct");
        assertEquals(30, b1.getSpeed(), "The speed must be correct");
        assertEquals(1, b1.getResistance(), "The resistance must be correct");
        assertEquals(Direction.droite, b1.getDirection(), "The direction must be correct");
        
        assertEquals(0, randomBalloon.getX(), "The initial X-coordinate must be that of the first cell in the path");
        assertEquals(0, randomBalloon.getY(), "The initial Y-coordinate must be that of the first cell in the path");
        assertEquals(2, randomBalloon.getSpeed(), "The speed must be correct");
        assertEquals(3, randomBalloon.getResistance(), "The resistance must be correct");
        assertNull(randomBalloon.getDirection(), "The direction must be null for a random ball");
        
        for (Cell cell : testPath) {
            assertTrue(cell instanceof BalloonCell, "The path must contain only BalloonCell elements");
        }
    }
    
    @Test
    public void testPositiveResistanceBalloon() {
        assertTrue(b1.getResistance() > 0, "The resistance must be positive");
        assertTrue(b2.getResistance() > 0, "The resistance must be positive");
        assertTrue(b3.getResistance() > 0, "The resistance must be positive");
        assertTrue(b4.getResistance() > 0, "The resistance must be positive");
        assertTrue(randomBalloon.getResistance() > 0, "The resistance must be positive");
    }
    
    @Test
    public void testGetSpeed() {
        assertEquals(30, b1.getSpeed(), "The speed must be correct");
        assertEquals(40, b2.getSpeed(), "The speed must be correct");
        assertEquals(80, b3.getSpeed(), "The speed must be correct");
        assertEquals(50, b4.getSpeed(), "The speed must be correct");
        assertEquals(2, randomBalloon.getSpeed(), "The speed must be correct");
    }
    
    @Test
    public void testGetResistance() {
        assertEquals(1, b1.getResistance(), "The resistance must be correct");
        assertEquals(2, b2.getResistance(), "The resistance must be correct");
        assertEquals(4, b3.getResistance(), "The resistance must be correct");
        assertEquals(1, b4.getResistance(), "The resistance must be correct");
        assertEquals(3, randomBalloon.getResistance(), "The resistance must be correct");
    }
    
    @Test
    public void testGetX() {
        assertEquals(0, b1.getX(), "The X coordinate must be correct");
        assertEquals(0, b2.getX(), "The X coordinate must be correct");
        assertEquals(3, b3.getX(), "The X coordinate must be correct");
        assertEquals(5, b4.getX(), "The X coordinate must be correct");
        assertEquals(0, randomBalloon.getX(), "The X coordinate must be correct");
    }
    
    @Test
    public void testGetY() {
        assertEquals(1, b1.getY(), "The Y coordinate must be correct");
        assertEquals(3, b2.getY(), "The Y coordinate must be correct");
        assertEquals(5, b3.getY(), "The Y coordinate must be correct");
        assertEquals(3, b4.getY(), "The Y coordinate must be correct");
        assertEquals(0, randomBalloon.getY(), "The Y coordinate must be correct");
    }
    
    @Test
    public void testGetDirection() {
        assertEquals(Direction.droite, b1.getDirection(), "The direction must be correct");
        assertEquals(Direction.bas, b2.getDirection(), "The direction must be correct");
        assertEquals(Direction.gauche, b3.getDirection(), "The direction must be correct");
        assertEquals(Direction.haut, b4.getDirection(), "The direction must be correct");
        assertNull(randomBalloon.getDirection(), "The direction must be null for a random balloon");
    }
    
    @Test
    public void testMoveClassicBalloon() {
        b1.moveClassicBalloon();
        assertEquals(1, b1.getX(), "The balloon must move one space to the right");
        assertEquals(1, b1.getY(), "The Y-coordinate must not change");
        
        b2.moveClassicBalloon();
        assertEquals(0, b2.getX(), "The X-coordinate must not change");
        assertEquals(4, b2.getY(), "The balloon must move down one space");
        
        b3.moveClassicBalloon();
        assertEquals(2, b3.getX(), "The balloon must move one space to the left");
        assertEquals(5, b3.getY(), "The Y-coordinate must not change");
        
        b4.moveClassicBalloon();
        assertEquals(5, b4.getX(), "The X-coordinate must not change");
        assertEquals(2, b4.getY(), "The ball must move up one space");
    }
    
    @Test
    public void testMultipleMovesClassicBalloon() {
        b1.moveClassicBalloon();
        b1.moveClassicBalloon();
        assertEquals(2, b1.getX(), "The ball must move two spaces to the right");
        assertEquals(1, b1.getY(), "The Y-coordinate must not change");
        
        b2.moveClassicBalloon();
        b2.moveClassicBalloon();
        b2.moveClassicBalloon();
        assertEquals(0, b2.getX(), "The X-coordinate must not change");
        assertEquals(6, b2.getY(), "The balloon must move three spaces down");
    }
    
    @Test
    public void testMoveRandomBalloon() {
        assertEquals(0, randomBalloon.getX(), "Incorrect initial X position");
        assertEquals(0, randomBalloon.getY(), "Incorrect initial Y position");
        
        randomBalloon.moveRandomBalloon();
        assertEquals(1, randomBalloon.getX(), "The balloon must move to the next cell on the path");
        assertEquals(0, randomBalloon.getY(), "The balloon must move to the next cell on the path");
        
        randomBalloon.moveRandomBalloon();
        assertEquals(1, randomBalloon.getX(), "The balloon must move to the next cell on the path");
        assertEquals(1, randomBalloon.getY(), "The balloon must move to the next cell on the path");
        
        randomBalloon.moveRandomBalloon();
        assertEquals(2, randomBalloon.getX(), "The balloon must move to the next cell on the path");
        assertEquals(1, randomBalloon.getY(), "The balloon must move to the next cell on the path");
    }
    
    @Test
    public void testMoveRandomBalloonAtEndOfPath() {
        ArrayList<Cell> singleCellPath = new ArrayList<>();
        singleCellPath.add(new BalloonCell(2, 2, new ArrayList<>(), 0));
        Balloon singlePathBalloon = new Balloon(1, 1, singleCellPath);
        
        singlePathBalloon.moveRandomBalloon();
        assertEquals(2, singlePathBalloon.getX(), "The balloon must not move if it has reached the end of the path");
        assertEquals(2, singlePathBalloon.getY(), "The balloon must not move if it has reached the end of the path");
    }
    
    @Test
    public void testBalloonSpawnInTheBorder() {
        assertTrue(b1.getX() >= 0, "X must be greater than or equal to 0");
        assertTrue(b1.getY() >= 0, "Y must be greater than or equal to 0");
        assertTrue(b2.getX() >= 0, "X must be greater than or equal to 0");
        assertTrue(b2.getY() >= 0, "Y must be greater than or equal to 0");
    }
    
    @Test
    public void testBalloonPathOrder() {
        ArrayList<Cell> orderedPath = new ArrayList<>();
        orderedPath.add(new BalloonCell(0, 0, new ArrayList<>(), 0));
        orderedPath.add(new BalloonCell(0, 1, new ArrayList<>(), 1));
        orderedPath.add(new BalloonCell(1, 1, new ArrayList<>(), 2));
        orderedPath.add(new BalloonCell(2, 1, new ArrayList<>(), 3));
        
        Balloon orderedBalloon = new Balloon(1, 1, orderedPath);
        
        assertEquals(0, orderedBalloon.getX());
        assertEquals(0, orderedBalloon.getY());
        
        orderedBalloon.moveRandomBalloon();
        assertEquals(0, orderedBalloon.getX());
        assertEquals(1, orderedBalloon.getY());
        
        orderedBalloon.moveRandomBalloon();
        assertEquals(1, orderedBalloon.getX());
        assertEquals(1, orderedBalloon.getY());
        
        orderedBalloon.moveRandomBalloon();
        assertEquals(2, orderedBalloon.getX());
        assertEquals(1, orderedBalloon.getY());
    }

    @Test
    public void balloonIsNotFrozenAtCreation(){
        assertFalse(b1.isFrozen());
        assertFalse(randomBalloon.isFrozen());
    }

    @Test
    public void balloonCanBeFrozen(){
        b1.freeze();
        assertTrue(b1.isFrozen());
        randomBalloon.freeze();
        assertTrue(randomBalloon.isFrozen());
    }

    @Test
    public void balloonCanBeUnfrozen(){
        b1.freeze();
        assertTrue(b1.isFrozen());
        b1.unfreeze();
        assertFalse(b1.isFrozen());
        randomBalloon.freeze();
        assertTrue(randomBalloon.isFrozen());
        randomBalloon.unfreeze();
        assertFalse(randomBalloon.isFrozen());
    }

    @Test
    public void balloonIsNotAtEndProgressOnCreation(){
        assertFalse(b1.isAtEndProgress());
        assertFalse(randomBalloon.isAtEndProgress());
    }
}