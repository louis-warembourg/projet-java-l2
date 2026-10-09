package towerdefense;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import towerdefense.cell.*;
import towerdefense.util.Direction;
import towerdefense.tower.*;
import towerdefense.projectile.*;

import java.util.*;

public class InitTest {
    private Init initRandom;
    private Init initClassic;
    private int gameModeRandom;
    private int gameModeClassic;
    private Random rand;

    @BeforeEach
    public void setUp(){
        this.gameModeRandom = 0;
        this.gameModeClassic = 1;
        this.initRandom = new Init(this.gameModeRandom);
        this.initClassic = new Init(this.gameModeClassic);
        this.rand = new Random();
    }

    @Test
    public void testGenerateBoardRandomMode() {
        Board board = this.initRandom.generateBoard(10,10);
        
        assertNotNull(board, "The array must not be empty");
        assertNotNull(board.getPath(), "The path to the random plate board not be null");
        assertFalse(board.getPath().isEmpty(), "The path to the random board must not be empty");
    }

    @Test
    public void testGenerateBoardClassicMode() {
        Board board = this.initClassic.generateBoard(10,10);
        assertNotNull(board, "The board must not be empty");
        assertNull(board.getPath(), "The path to the classic board must be null");
    }

    @Test
    public void testCreateBalloonsRandomMode() {
        Board board = Board.createBoardRandom(10, 10);
        ArrayList<Cell> path = board.getPath();
        
        ArrayList<Balloon> balloons = this.initRandom.createBalloons(3, this.rand, path, board, board.getX(), board.getY());
        
        assertNotNull(balloons, "The list of balloons must not be empty");
        assertEquals(3, balloons.size(), "The number of balls created must match");
        
        for (Balloon b : balloons) {
            assertNotNull(b, "Each balloon must be created");
            assertNull(b.getDirection(), "The ball in random mode has no direction");
        }
    }

    @Test
    public void testCreateBalloonsClassicMode() {
        Board board = Board.createBoardClassic(5, 5);
        
        ArrayList<Balloon> balloons = this.initClassic.createBalloons(3, rand, null, board, board.getX(), board.getY());
        
        assertNotNull(balloons, "The list of balloons must not be empty");
        assertEquals(3, balloons.size(), "The number of balls created must match");
        
        for (Balloon b : balloons) {
            assertNotNull(b, "Each balloon must be created");
            assertNotNull(b.getDirection(), "In classic mode, the balloon must have a direction");
            assertTrue(b.getX() >= 0 && b.getX() < 5, "The balloon must remain within the boundaries of the court");
            assertTrue(b.getY() >= 0 && b.getY() < 5, "The balloon must remain within the boundaries of the court");
        }
    }

    @Test
    public void testCreateManageBalloons() {
        Board board = Board.createBoardRandom(10, 10);
        ArrayList<Balloon> balloons = new ArrayList<>();
        balloons.add(new Balloon(30, 1, 0, 0, Direction.droite));
        balloons.add(new Balloon(40, 2, 1, 1, Direction.bas));
        
        ArrayList<ManageBalloon> managers = this.initRandom.createManageBalloons(balloons, board);
        
        assertNotNull(managers, "The list of managers must not be empty");
        assertEquals(2, managers.size(), "The number of managers must match the number of balloons");
        
        for (int i = 0; i < balloons.size(); i++) {
            assertEquals(balloons.get(i), managers.get(i).getBalloon(), "The manager must be assigned to the correct team");
            assertEquals(board, managers.get(i).getBoard(), "The manager must have the right setup");
        }
    }

    @Test
    public void testCreateManageTowers() {
        Board board = Board.createBoardClassic(10, 10);
        ArrayList<Tower> towers = new ArrayList<>();
        towers.add(new DartMonkeyTower(1, 1, null));
        towers.add(new FreezeTower(2, 2));
        
        ArrayList<ManageTower> managers = this.initClassic.createManageTowers(towers, board);
        
        assertNotNull(managers, "The list of managers must not be empty");
        assertEquals(2, managers.size(), "The number of managers must match the number of rounds");
        
        for (int i = 0; i < towers.size(); i++) {
            assertEquals(towers.get(i), managers.get(i).getTower(), "The manager must be assigned to the correct tower");
            assertEquals(board, managers.get(i).getBoard(), "The manager must have the right setup");
        }
    }
}