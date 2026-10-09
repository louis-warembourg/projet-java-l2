package towerdefense;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import towerdefense.tower.*;


public class TowerTest {
    private Tower bomb;
    private Tower dart;
    private Tower elite;
    private Tower freeze;
    private Tower gorilla;
    private Tower needle;
    private Tower slow;
    
    @BeforeEach
    public void before(){
        bomb = new BombMonkeyTower(0, 0, null);
        dart = new DartMonkeyTower(1, 1, null);
        elite = new EliteMonkeyTower(2, 2, null);
        freeze = new FreezeTower(3, 3);
        gorilla = new GorillaTower(4, 4, null);
        needle = new NeedleMonkeyTower(5, 5, null);
        slow = new SlowTower(6, 6);
    }

    @Test
    public void testGetCurrentCell(){
        assertEquals(0, bomb.getCurrentCell()[0]);
        assertEquals(0, bomb.getCurrentCell()[1]);
        assertEquals(4, gorilla.getCurrentCell()[0]);
        assertEquals(4, gorilla.getCurrentCell()[1]);
    }

    @Test
    public void testGetRange(){
        assertEquals(150, bomb.getRange());
        assertEquals(100, dart.getRange());
        assertEquals(0, elite.getRange());
        assertEquals(100, freeze.getRange());
        assertEquals(200, gorilla.getRange());
        assertEquals(80, needle.getRange());
        assertEquals(100, slow.getRange());
    }

    @Test
    public void testGetCost(){
        assertEquals(600, bomb.getCost());
        assertEquals(200, dart.getCost());
        assertEquals(500, elite.getCost());
        assertEquals(400, freeze.getCost());
        assertEquals(1200, gorilla.getCost());
        assertEquals(350, needle.getCost());
        assertEquals(500, slow.getCost());
    }
}