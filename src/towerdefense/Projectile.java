package towerdefense;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import towerdefense.tower.EliteMonkeyTower;

/**a class for projectile*/
public class Projectile{
    /**the strength of the projectile, will be modified with upgrades*/
    protected int strength;

    /**the towers which use the projectile */
    protected ArrayList<Tower> assignedTowers;

    /**the range of the projectile */
    protected int range;

    /**
     * builds a new projectile with minimum strength 1
     */
    public Projectile(){
        this.strength = 1;
        this.assignedTowers = new ArrayList<Tower>();
        this.range = 0;
    }

// ==================== METHOD GET ====================

    /**
     * provides the strength of the balloon
     * @return this balloon's strength
     */
    public int getStrength(){
        return this.strength;
    }

    /**
     * get the range of the projectile
     * @return this projectile's range
     */
    public int getRange(){
        return this.range;
    }

// ==================== DAMAGE METHOD ====================


    /**
     * makes damage to the balloon on which it is shot
     * @param target the balloon to be hurt
     */
    public void hurtBalloon(Balloon target){
        target.hurt(target.getResistance()-this.strength);
    }


    /**
     * changes the strength of the projectile
     * @param addStrength the int of increase to be applied to the strength
     */
    public void changeStrength(int addStrength){
        this.strength += addStrength;
    }

}