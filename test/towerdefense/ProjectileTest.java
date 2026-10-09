package towerdefense;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import towerdefense.projectile.*;
//import towerdefense.tower.*;
//import java.util.*;
import towerdefense.util.Direction;

public class ProjectileTest{
    private Projectile needle;
    private Projectile bomb;
    private Projectile extrabomb;
    private Projectile dart;
    private Projectile pointydart;
    private Projectile verypointydart;
    private Balloon b1;

    @BeforeEach
    public void before(){
        this.needle = new Needle();
        this.bomb = new Bomb();
        this.extrabomb = new ExtraBomb();
        this.dart = new Dart();
        this.pointydart = new PointyDart();
        this.verypointydart = new VeryPointyDart();
        this.b1 = new Balloon(80, 12, 3, 5, Direction.gauche);
    }

    @Test
    public void testGetStrength(){
        assertEquals(1, this.needle.getStrength());
        assertEquals(2, this.bomb.getStrength());
        assertEquals(2, this.extrabomb.getStrength());
        assertEquals(1, this.dart.getStrength());
        assertEquals(2, this.pointydart.getStrength());
        assertEquals(4, this.verypointydart.getStrength());
    }

    @Test
    public void testHurtBalloon(){
        this.needle.hurtBalloon(b1);
        assertEquals(11, b1.getResistance());
        this.bomb.hurtBalloon(b1);
        assertEquals(9, b1.getResistance());
        this.extrabomb.hurtBalloon(b1);
        assertEquals(7, b1.getResistance());
        this.dart.hurtBalloon(b1);
        assertEquals(6, b1.getResistance());
        this.pointydart.hurtBalloon(b1);
        assertEquals(4, b1.getResistance());
        this.verypointydart.hurtBalloon(b1);
        assertEquals(0, b1.getResistance());
    }

    @Test
    public void testAddStrength(){
        this.needle.changeStrength(1);
        assertEquals(2, this.needle.getStrength());
        this.bomb.changeStrength(1);
        assertEquals(3, this.bomb.getStrength());
        this.extrabomb.changeStrength(1);
        assertEquals(3, this.extrabomb.getStrength());
        this.dart.changeStrength(1);
        assertEquals(2, this.dart.getStrength());
        this.pointydart.changeStrength(1);
        assertEquals(3, this.pointydart.getStrength());
        this.verypointydart.changeStrength(1);
        assertEquals(5, this.verypointydart.getStrength());
    }

    @Test
    public void testRemoveStrenght(){
        this.bomb.changeStrength(-1);
        assertEquals(1, this.bomb.getStrength());
        this.extrabomb.changeStrength(-1);
        assertEquals(1, this.extrabomb.getStrength());
        this.pointydart.changeStrength(-1);
        assertEquals(1, this.pointydart.getStrength());
        this.verypointydart.changeStrength(-1);
        assertEquals(3, this.verypointydart.getStrength());
    }
}