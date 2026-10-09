package towerdefense;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import towerdefense.tower.*;
import towerdefense.projectile.*;
import towerdefense.util.UpgradeType;

public class UpgradeTest {
    private Upgrade u1;
    private Upgrade u2;
    private Upgrade u3;
    private Upgrade u4;
    private Projectile extra;

    @BeforeEach
    public void before(){
        this.extra = new ExtraBomb();
        this.u1 = new Upgrade(UpgradeType.POWER, 1);
        this.u2 = new Upgrade(UpgradeType.SPEED, 25.0, null);
        this.u3 = new Upgrade(UpgradeType.RANGE, 50.0, null);
        this.u4 = new Upgrade(UpgradeType.MODIFY_PROJECTILE, 0, extra);
    }

    @Test
    public void testGetUpgradeType(){
        assertEquals(UpgradeType.POWER, this.u1.getUpgradeType());
        assertEquals(UpgradeType.SPEED, this.u2.getUpgradeType());
        assertEquals(UpgradeType.RANGE, this.u3.getUpgradeType());
        assertEquals(UpgradeType.MODIFY_PROJECTILE, this.u4.getUpgradeType());
    }

    @Test
    public void testGetPercentage(){
        assertEquals(0, this.u1.getPercentage());
        assertEquals(25.0, this.u2.getPercentage());
        assertEquals(50.0, this.u3.getPercentage());
        assertEquals(0, this.u4.getPercentage());
    }

    @Test
    public void testGetGivenProjectile(){
        assertNull(this.u1.getGivenProjectile());
        assertNull(this.u2.getGivenProjectile());
        assertNull(this.u3.getGivenProjectile());
        assertNotNull(this.u4.getGivenProjectile());
    }

    @Test
    public void testGetNbIncrease(){
        assertEquals(1, this.u1.getNbIncrease());
        assertEquals(0, this.u2.getNbIncrease());
        assertEquals(0, this.u3.getNbIncrease());
        assertEquals(0, this.u4.getNbIncrease());
    }
}
