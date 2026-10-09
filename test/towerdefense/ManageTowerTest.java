package towerdefense;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import towerdefense.tower.*;
import towerdefense.util.Direction;
import towerdefense.cell.*;
import towerdefense.projectile.*;

import java.util.*;

public class ManageTowerTest {
    
    private Board classicBoard;
    private Board randomBoard;
    private Tower dartTower;
    private Tower bombTower;
    private Tower eliteTower;
    private Tower freezeTower;
    private Tower slowTower;
    private ManageTower mtDart;
    private ManageTower mtBomb;
    private ManageTower mtElite;
    private ManageTower mtFreeze;
    private ManageTower mtSlow;
    private Balloon balloon1;
    private Balloon balloon2;
    private Balloon balloon3;
    
    @BeforeEach
    public void setUp() {
        this.classicBoard = Board.createBoardClassic(10, 10);
        this.randomBoard = Board.createBoardRandom(10, 10);
        
        this.dartTower = new DartMonkeyTower(5, 5,  new Dart());
        this.bombTower = new BombMonkeyTower(3, 3, new Bomb());
        this.eliteTower = new EliteMonkeyTower(2, 2, new VeryPointyDart());
        this.freezeTower = new FreezeTower(4, 4);
        this.slowTower = new SlowTower(6, 6);
        
        this.mtDart = new ManageTower(dartTower, classicBoard);
        this.mtBomb = new ManageTower(bombTower, classicBoard);
        this.mtElite = new ManageTower(eliteTower, classicBoard);
        this.mtFreeze = new ManageTower(freezeTower, classicBoard);
        this.mtSlow = new ManageTower(slowTower, classicBoard);
        
        this.balloon1 = new Balloon(30, 3, 5, 3, Direction.droite);
        this.balloon2 = new Balloon(40, 2, 4, 4, Direction.bas);
        this.balloon3 = new Balloon(50, 1, 3, 3, Direction.haut);
    }
    
    @Test
    public void testIncreaseShootingClock() {
        int initialTime = mtDart.getShootingTime();
        mtDart.increaseShootingClock();
        assertEquals(initialTime + 1, mtDart.getShootingTime());
        
        mtDart.increaseShootingClock();
        mtDart.increaseShootingClock();
        assertEquals(initialTime + 3, mtDart.getShootingTime());
    }
    
    @Test
    public void testResetShootClock() {
        mtDart.increaseShootingClock();
        mtDart.increaseShootingClock();
        assertTrue(mtDart.getShootingTime() > 0);
        
        mtDart.resetShootClock();
        assertEquals(0, mtDart.getShootingTime());
    }
    
    @Test
    public void testCanShoot() {
        assertFalse(this.mtDart.canShoot());
        
        Cell cell = classicBoard.getCell(5, 5);
        cell.addBalloon(this.balloon1);
        this.mtDart.identifyTarget(false);
        assertTrue(this.mtDart.canShoot());
        
        int cooldown = dartTower.getShootingSpeed() / 100;
        for (int i = 0; i <= cooldown; i++) {
            this.mtDart.increaseShootingClock();
        }
        assertTrue(this.mtDart.canShoot());
        //assertTrue(this.balloon1.isDead());
        //assertFalse(this.mtDart.canShoot());
    }
    
    @Test
    public void testShootingSpeedIsReset() {
        assertTrue(this.mtDart.shootingSpeedIsReset());
        
        this.mtDart.increaseShootingClock();
        assertFalse(this.mtDart.shootingSpeedIsReset());
        
        this.mtDart.resetShootClock();
        assertTrue(this.mtDart.shootingSpeedIsReset());
    }
    
    @Test
    public void testGetCellsInRangeForNormalTower() {
        Map<Cell, Integer> cellsInRange = mtDart.getCellsInRange();
        assertNotNull(cellsInRange);
        assertFalse(cellsInRange.isEmpty());
        
        Cell towerCell = classicBoard.getCell(5, 5);
        assertTrue(cellsInRange.containsKey(towerCell));
        
        Cell farCell = classicBoard.getCell(0, 0);
        assertFalse(cellsInRange.containsKey(farCell));
    }
    
    @Test
    public void testGetCellsInRangeForEliteTower() {
        Map<Cell, Integer> cellsInRange = mtElite.getCellsInRange();
        assertNotNull(cellsInRange);
        
        Cell corner1 = classicBoard.getCell(0, 0);
        Cell corner2 = classicBoard.getCell(9, 9);
        assertTrue(cellsInRange.containsKey(corner1));
        assertTrue(cellsInRange.containsKey(corner2));
    }
    
    @Test
    public void testGetCornersOfRange() {
        ArrayList<Cell> corners = mtDart.getCornersOfRange();
        assertNotNull(corners);
        
        for (Cell corner : corners) {
            assertTrue(mtDart.getCellsInRange().containsKey(corner));
        }
    }
    
    @Test
    public void testIdentifyTargetWithNoBalloons() {
        Balloon target = mtDart.identifyTarget(false);
        assertNull(target);
        assertNull(mtDart.getTarget());
    }
    
    @Test
    public void testIdentifyTargetWithBalloonInRange() {
        Cell cell = classicBoard.getCell(5, 5);
        cell.addBalloon(balloon1);
        
        Balloon target = mtDart.identifyTarget(false);
        assertNotNull(target);
        assertEquals(balloon1, target);
        assertEquals(balloon1, mtDart.getTarget());
    }
    
    @Test
    public void testIdentifyTargetWithMultipleBalloons() {
        Cell cell1 = classicBoard.getCell(5, 5);
        Cell cell2 = classicBoard.getCell(4, 5);
        Cell cell3 = classicBoard.getCell(5, 4);
        
        Balloon nearBalloon = new Balloon(30, 1, 5, 5, Direction.droite);
        nearBalloon.setProgress(90);
        
        Balloon farBalloon = new Balloon(30, 1, 4, 5, Direction.droite);
        farBalloon.setProgress(10);
        
        Balloon mediumBalloon = new Balloon(30, 1, 5, 4, Direction.droite);
        mediumBalloon.setProgress(50);
        
        cell1.addBalloon(nearBalloon);
        cell2.addBalloon(farBalloon);
        cell3.addBalloon(mediumBalloon);
        
        Balloon target = mtDart.identifyTarget(false);
        
        assertNotNull(target);
    }
    
    @Test
    public void testIdentifyTargetOnRandomBoard() {
        ArrayList<Cell> path = randomBoard.getPath();
        assertNotNull(path);
        assertFalse(path.isEmpty());
        
        Cell pathCell = path.get(path.size() / 2);
        Balloon randomBalloon = new Balloon(30, 1, pathCell.getX(), pathCell.getY(), null);
        pathCell.addBalloon(randomBalloon);
        
        Tower tower = new DartMonkeyTower(pathCell.getX() + 1, pathCell.getY(), null);
        ManageTower mt = new ManageTower(tower, randomBoard);
        
        Balloon target = mt.identifyTarget(true);
        assertNotNull(target);
        assertEquals(randomBalloon, target);
    }
    
    @Test
    public void testGetMinDistanceClassic() {
        Cell cell = classicBoard.getCell(5, 5);
        Cell prevCell = classicBoard.nextCell(cell, Direction.gauche);
        Cell nextCell = classicBoard.nextCell(cell, Direction.droite);
        
        Map<Cell, Integer> range = mtDart.getCellsInRange();
        
        Balloon balloonRight = new Balloon(30, 1, 5, 5, Direction.droite);
        int distance = mtDart.getMinDistance(range, cell, prevCell, nextCell, false, balloonRight);
        assertTrue(distance >= 0);

        Balloon balloonLeft = new Balloon(30, 1, 5, 5, Direction.gauche);
        distance = mtDart.getMinDistance(range, cell, prevCell, nextCell, false, balloonLeft);
        assertTrue(distance >= 0);
    }
    
    @Test
    public void testGetMinDistanceRandom() {
        if (randomBoard.getPath().size() < 3) return;
        
        ArrayList<Cell> path = randomBoard.getPath();
        Cell cell = path.get(1);
        Cell prevCell = path.get(0);
        Cell nextCell = path.get(2);
        
        int towerX = cell.getX() + 1;
        int towerY = cell.getY();
        if (towerX < randomBoard.getX()) {
            Tower tower = new DartMonkeyTower(towerX, towerY, null);
            ManageTower mt = new ManageTower(tower, randomBoard);
            
            Balloon balloon = new Balloon(30, 1, cell.getX(), cell.getY(), null);
            
            Map<Cell, Integer> range = mt.getCellsInRange();
            
            int distance = mt.getMinDistance(range, cell, prevCell, nextCell, true, balloon);
            assertTrue(distance >= 0);
        }
    }
    
    @Test
    public void testShoot() {
        this.classicBoard.getCell(5, 5).addBalloon(this.balloon1);
        this.mtDart.identifyTarget(false);
        this.mtDart.resetShootClock();
        
        int initialResistance = this.balloon1.getResistance();
        this.mtDart.shoot();
        assertEquals(initialResistance, this.balloon1.getResistance());
        
        int cooldown = this.dartTower.getShootingSpeed() / 100;
        for (int i = 0; i <= cooldown; i++) {
            this.mtDart.increaseShootingClock();
        }
        this.mtDart.shoot();
        assertTrue((this.balloon1.getResistance() < initialResistance) || (this.balloon1.getResistance() == initialResistance - 1), "The balloon should have lost some of its air");
        assertEquals(0, this.mtDart.getShootingTime(), "The shot timer should be reset");
    }
    
    @Test
    public void testShootWithDeadBalloon() {
        Cell cell = classicBoard.getCell(5, 5);
        cell.addBalloon(balloon1);
        
        //mtDart.identifyTarget(false);
        
        int cooldown = dartTower.getShootingSpeed() / 100;
        for (int i = 0; i <= cooldown; i++) {
            mtDart.increaseShootingClock();
        }
        
        int initialShootTime = mtDart.getShootingTime();
        mtDart.shoot();
        
        assertEquals(initialShootTime, mtDart.getShootingTime());
    }
    
    @Test
    public void testIntegrationWithMultipleTowers() {
        Cell cell1 = classicBoard.getCell(5, 5);
        Cell cell2 = classicBoard.getCell(4, 5);
        
        cell1.addBalloon(balloon1);
        cell2.addBalloon(balloon2);
        
        mtDart.identifyTarget(false);
        ManageTower mtDart2 = new ManageTower(new DartMonkeyTower(4, 5, null), classicBoard);
        mtDart2.identifyTarget(false);
        
        assertNotNull(mtDart.getTarget());
        assertNotNull(mtDart2.getTarget());
    }

    @Test
    public void testGetCellsInRange(){
        assertTrue(this.mtDart.getCellsInRange().containsKey(this.classicBoard.getCell(4,5)));
        assertTrue(this.mtDart.getCellsInRange().containsKey(this.classicBoard.getCell(4,4)));
        assertFalse(this.mtDart.getCellsInRange().containsKey(this.classicBoard.getCell(0,7)));
        assertFalse(this.mtDart.getCellsInRange().containsKey(this.classicBoard.getCell(1,4)));
    }

    @Test
    public void testGetCellsInRangeForBombTower(){
        assertTrue(this.mtBomb.getCellsInRange().containsKey(this.classicBoard.getCell(3,4)));
        assertTrue(this.mtBomb.getCellsInRange().containsKey(this.classicBoard.getCell(4,4)));
        assertFalse(this.mtBomb.getCellsInRange().containsKey(this.classicBoard.getCell(0,7)));
        assertFalse(this.mtBomb.getCellsInRange().containsKey(this.classicBoard.getCell(1,6))); 
    }

    @Test
    public void testGetCellsInRangeForFreezeTower(){
        assertTrue(this.mtFreeze.getCellsInRange().containsKey(this.classicBoard.getCell(3,4)));
        assertTrue(this.mtFreeze.getCellsInRange().containsKey(this.classicBoard.getCell(4,4)));
        assertFalse(this.mtFreeze.getCellsInRange().containsKey(this.classicBoard.getCell(0,7)));
        assertFalse(this.mtFreeze.getCellsInRange().containsKey(this.classicBoard.getCell(1,6)));
    }

    @Test
    public void testGetCellsInRangeForSlowTower(){
        assertTrue(this.mtSlow.getCellsInRange().containsKey(this.classicBoard.getCell(7,6)));
        assertTrue(this.mtSlow.getCellsInRange().containsKey(this.classicBoard.getCell(6,5)));
        assertFalse(this.mtSlow.getCellsInRange().containsKey(this.classicBoard.getCell(0,3)));
        assertFalse(this.mtSlow.getCellsInRange().containsKey(this.classicBoard.getCell(1,2)));
    }

    @Test
    public void testCannotShootRightAfterShooting() {
        classicBoard.getCell(5,5).addBalloon(balloon1);
        mtDart.identifyTarget(false);
        assertTrue(mtDart.canShoot());
        mtDart.shoot(); 
        assertFalse(mtDart.canShoot()); 
        assertEquals(0, mtDart.getShootingTime());
    }

    @Test
    public void testFreezeTowerFreezesTarget() {
        classicBoard.getCell(4,4).addBalloon(balloon2);
        mtFreeze.identifyTarget(false);
        mtFreeze.resetShootClock();
        for (int i = 0; i < freezeTower.getShootingSpeed()/100; i++) {
            mtFreeze.increaseShootingClock();
        }
        mtFreeze.shoot();
        assertTrue(balloon2.isFrozen());
    }

    @Test
    public void testDoesNotShootAtDeadBalloon() {
        classicBoard.getCell(5,5).addBalloon(balloon1);
        mtDart.identifyTarget(false);
        balloon1.hurt(0); 
        assertFalse(mtDart.canShoot());
        int before = mtDart.getShootingTime();
        mtDart.shoot();
        assertEquals(before, mtDart.getShootingTime());
    }

    @Test
    public void testTargetClosestToEnd() {
        Balloon closer = new Balloon(30, 1, 6, 5, Direction.droite);
        Balloon farther = new Balloon(30, 1, 4, 5, Direction.droite);

        classicBoard.getCell(6, 5).addBalloon(closer);
        classicBoard.getCell(4, 5).addBalloon(farther);

        mtDart.identifyTarget(false);

        assertEquals(closer, mtDart.getTarget(),
            "The tower must target the ball closest to the end (x=6)");
    }

    @Test
    public void testBombTowerHitsMultipleBalloons() {
        Balloon target = new Balloon(30, 5, 5, 3, Direction.droite);
        Balloon west = new Balloon(30, 4, 4, 3, Direction.droite);
        Balloon east = new Balloon(30, 4, 6, 3, Direction.gauche);
        Balloon north = new Balloon(30, 4, 5, 2, Direction.bas);  
        Balloon south = new Balloon(30, 4, 5, 4, Direction.bas);   
        Balloon far = new Balloon(30, 4, 2, 3, Direction.droite);

        classicBoard.getCell(5,3).addBalloon(target);
        classicBoard.getCell(4,3).addBalloon(west);
        classicBoard.getCell(6,3).addBalloon(east);
        classicBoard.getCell(5,2).addBalloon(north);
        classicBoard.getCell(5,4).addBalloon(south);
        classicBoard.getCell(2,3).addBalloon(far);

        mtBomb.identifyTarget(false);
        assertEquals(target, mtBomb.getTarget(), "The target should be the ball at (5,3)");

        mtBomb.resetShootClock();
        int cooldown = bombTower.getShootingSpeed() / 100;
        for (int i = 0; i < cooldown; i++) {
            mtBomb.increaseShootingClock();
        }
        assertTrue(mtBomb.canShoot());

        int targetRes = target.getResistance();
        int westRes   = west.getResistance();
        int eastRes   = east.getResistance();
        int northRes  = north.getResistance();
        int southRes  = south.getResistance();
        int farRes    = far.getResistance();

        mtBomb.shoot();

        assertEquals(targetRes - 2, target.getResistance(), "The target balloon must lose 2 HP");
        assertEquals(westRes - 2, west.getResistance(), "The balloon at (4,3) must lose 2 HP");
        assertEquals(eastRes - 2, east.getResistance(), "The balloon at (6,3) must lose 2 HP");
        assertEquals(northRes - 2, north.getResistance(), "The balloon at (5,2) must lose 2 HP");
        assertEquals(southRes - 2, south.getResistance(), "The balloon at (5,4) must lose 2 HP");
        assertEquals(farRes, far.getResistance(), "A ball outside the zone must not take damage");
        assertEquals(0, mtBomb.getShootingTime());
    }
}
