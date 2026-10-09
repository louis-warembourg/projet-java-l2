package towerdefense;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import towerdefense.cell.*;
import towerdefense.util.Direction;
import towerdefense.tower.*;
import towerdefense.projectile.*;

import java.util.*;

public class GameTest {
    
    private Game game;
    private Player player;
    private Random rand;
    
    @BeforeEach
    public void setUp() {
        this.player = new Player();
        this.game = new Game(player);
        this.rand = new Random();
    }

     @Test
    public void testConstructor() {
        assertNotNull(game, "The game must not be canceled");
        assertEquals(0, game.getCurrentTick(), "The initial tick must be 0");
        assertEquals(player, game.getPlayer(), "The player must be properly assigned");
        assertEquals(0, game.getGameMode(), "The default game mode must be 0 (random)");
        assertEquals(10, game.getLength(), "The default length should be 10");
        assertEquals(10, game.getWidth(), "The default width should be 10");
        assertEquals(5, game.getNbBalloons(), "The default number of balls should be 5");
    }

    @Test
    public void testGetSetGameMode() {
        game.setGameMode(1);
        assertEquals(1, game.getGameMode(), "The game mode must be changed to 1");
        
        game.setGameMode(0);
        assertEquals(0, game.getGameMode(), "The game mode must be set to 0");
    }

    @Test
    public void testGetSetLengthAndWidth() {
        game.setLength(15);
        game.setWidth(20);
        
        assertEquals(15, game.getLength(), "The length needs to be changed");
        assertEquals(20, game.getWidth(), "The width needs to be changed");
    }

    @Test
    public void testGetSetNbBalloons() {
        game.setNbBalloons(10);
        assertEquals(10, game.getNbBalloons(), "The number of balloons needs to be changed");
        
        game.setNbBalloons(3);
        assertEquals(3, game.getNbBalloons(), "The number of balloons needs to be changed");
    }

    @Test
    public void testGetSetPlayer() {
        Player newPlayer = new Player();
        game.setPlayer(newPlayer);
        assertEquals(newPlayer, game.getPlayer(), "The player needs to be modified");
    }

    @Test
    public void testGetManageBalloon() {
        Board board = Board.createBoardClassic(10, 10);
        Balloon b1 = new Balloon(30, 1, 0, 0, Direction.droite);
        Balloon b2 = new Balloon(40, 2, 1, 1, Direction.bas);
        
        ArrayList<ManageBalloon> managers = new ArrayList<>();
        ManageBalloon mb1 = new ManageBalloon(b1, board);
        ManageBalloon mb2 = new ManageBalloon(b2, board);
        managers.add(mb1);
        managers.add(mb2);
        
        ManageBalloon result = game.getManageBalloon(managers, b1);
        assertEquals(mb1, result, "Must return the correct manager");
        
        result = game.getManageBalloon(managers, b2);
        assertEquals(mb2, result, "Must return the correct manager");
        
        Balloon b3 = new Balloon(50, 3, 2, 2, Direction.haut);
        result = game.getManageBalloon(managers, b3);
        assertNull(result, "Should return null if the balloon is not found");
    }

    @Test
    public void testRemoveBalloonAtEnd() {
        Board board = Board.createBoardClassic(5, 5);
        Balloon balloon = new Balloon(30, 1, 2, 2, Direction.droite);
        Map<Balloon, Integer> exitTimes = new HashMap<>();
        ArrayList<Balloon> exitedBalloons = new ArrayList<>();
        
        board.setBalloon(balloon, 2, 2);
        game.removeBalloonAtEnd(board, balloon, exitedBalloons, exitTimes);
        
        assertTrue(exitedBalloons.contains(balloon), "The balloon must be added to the list of removed balloons");
        assertTrue(exitTimes.containsKey(balloon), "The exit time must be recorded");
        assertEquals(0, exitTimes.get(balloon), "The exit time must match the current tick");
        
        Cell cell = board.getCell(2, 2);
        assertFalse(cell.getBalloonList().contains(balloon), "The balloon must be removed from the cell");
    }

    @Test
    public void testBalloonProgressNotFrozen() {
        Board board = Board.createBoardClassic(10, 10);
        Balloon balloon = new Balloon(30, 5, 5, 5, Direction.droite);
        ManageBalloon mb = new ManageBalloon(balloon, board);
        ArrayList<Balloon> deadBalloons = new ArrayList<>();
        ArrayList<Balloon> exitedBalloons = new ArrayList<>();
        
        int initialProgress = balloon.getProgress();
        game.balloonProgress(deadBalloons, exitedBalloons, balloon, board, mb);
        
        assertTrue(balloon.getProgress() > initialProgress, "Progress must continue if the ball isn't frozen");
        assertFalse(deadBalloons.contains(balloon), "The balloon must not be dead");
        assertFalse(exitedBalloons.contains(balloon), "The balloon must not be taken out of play");
    }

    @Test
    public void testBalloonProgressFrozen() {
        Board board = Board.createBoardClassic(10, 10);
        Balloon balloon = new Balloon(30, 5, 5, 5, Direction.droite);
        ManageBalloon mb = new ManageBalloon(balloon, board);
        ArrayList<Balloon> deadBalloons = new ArrayList<>();
        ArrayList<Balloon> exitedBalloons = new ArrayList<>();
        
        balloon.freeze();
        int initialProgress = balloon.getProgress();
        game.balloonProgress(deadBalloons, exitedBalloons, balloon, board, mb);
        
        assertEquals(initialProgress, balloon.getProgress(), "The score should not increase if the balloon is frozen");
        assertTrue(balloon.getFrozenTime() > 0, "The freeze time must be increased");
    }

    @Test
    public void testBalloonProgressAtEndOfCell() {
        Board board = Board.createBoardClassic(10, 10);
        Balloon balloon = new Balloon(100, 5, 5, 5, Direction.droite);
        ManageBalloon mb = new ManageBalloon(balloon, board);
        ArrayList<Balloon> deadBalloons = new ArrayList<>();
        ArrayList<Balloon> exitedBalloons = new ArrayList<>();
        
        balloon.setProgress(95);
        game.balloonProgress(deadBalloons, exitedBalloons, balloon, board, mb);
        
        assertTrue(balloon.getProgress() >= 100, "Progress must reach 100");
    }

    @Test
    public void testBalloonIsDeadAfterShoot() {
        Board board = Board.createBoardClassic(10, 10);
        Balloon balloon = new Balloon(30, 1, 5, 5, Direction.droite);
        Tower tower = new DartMonkeyTower(5, 5, new Dart());
        ManageTower mt = new ManageTower(tower, board);
        
        board.setBalloon(balloon, 5, 5);
        
        mt.identifyTarget(false);
        for (int i = 0; i <= tower.getShootingSpeed() / 100; i++) {
            mt.increaseShootingClock();
        }
        
        mt.shoot();
        assertTrue(balloon.isDead(), "The balloon must be dead after losing all its bounce");
    }
}