package towerdefense;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.util.*;
import towerdefense.cell.*;
import towerdefense.tower.*;
import towerdefense.util.Direction;

public class CellTest {
    private BalloonCell c1;
    private TowerCell c2;
    private MixedCell c3;
    private Balloon b1;
    private Balloon b2;
    private Tower t1;
    private ArrayList<Balloon> ballons;

    @BeforeEach
    public void before() {
        ballons = new ArrayList<>();
        c1 = new BalloonCell(0, 0, ballons,0);
        c2 = new TowerCell(1, 1);
        c3 = new MixedCell(2, 2, new ArrayList<>());
        b1 = new Balloon(5, 1, 0, 1, Direction.droite);
        b2 = new Balloon(3, 2, 0, 3, Direction.bas);
        t1 = new SlowTower(2, 2);
    }
    @Test
    public void testAddBalloonInABalloonCell() {

        assertTrue(c1.hasNoBalloon());
        // add the ballon in the cell
        c1.addBalloon(b1);
        assertEquals(1, c1.getBalloonList().size());

    }
    @Test
    public void AddBalloonAndSetTowerInAMixedCell() {
        assertTrue(c3.hasNoBalloon());
        assertFalse(c3.hasTower());
        // add the ballon in the cell
        c3.addBalloon(b1);
        assertEquals(1, c3.getBalloonList().size());
        c3.addTower(t1);
        assertTrue(c3.hasTower());
    }
    @Test
    public void removeABallonInABalloonCell() {
        c1.addBalloon(b1);
        assertEquals(1, c1.getBalloonList().size());
        c1.removeBalloon(b1);
        assertTrue(c1.hasNoBalloon());
    }
    @Test
    public void removeBalloonAndSellTowerInAMixedCell() {
        // add the ballon in the cell
        c3.addBalloon(b1);
        c3.addBalloon(b2);
        assertEquals(2, c3.getBalloonList().size());
        // add the tower in the cell
        c3.addTower(t1);
        assertTrue(c3.hasTower());
        // sell the tower
        c3.sellTower();
        assertFalse(c3.hasTower());
        // remove balloon
        c3.removeBalloon(b1);
        assertEquals(1, c3.getBalloonList().size());
    }

    @Test
    public void testSetTower() {
        Tower t = new SlowTower(1, 1);
        c2.addTower(t);
        assertTrue(c2.hasTower());
    }
     @Test
    public void testSellTower() {
        Tower t = new SlowTower(1, 1);
        c2.addTower(t);
        c2.sellTower();
        assertFalse(c2.hasTower());
    }

}
