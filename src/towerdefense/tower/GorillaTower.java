package towerdefense.tower;

import java.util.ArrayList;

import towerdefense.*;
import towerdefense.util.*;
//import towerdefense.projectile.*;


/** A class for gorilla tower
 */
public class GorillaTower extends Tower{

    /**
     * builds a new gorilla tower which inherits Tower
     * @param x the x-coordinate of the tower 
     * @param y the y-coordinate of the tower 
     * @param currentProjectile the projectile held by the tower
     * @see Tower
     */
    public GorillaTower(int x, int y, Projectile currentProjectile){
        super(x, y, currentProjectile, 200, 1200,0, 0, 300);
        this.availableUpgrades = new ArrayList<>();
        this.availableUpgrades.add(new Upgrade(UpgradeType.POWER, 2));
        this.availableUpgrades.add(new Upgrade(UpgradeType.RANGE, 50.0, currentProjectile));
        this.availableUpgrades.add(new Upgrade(UpgradeType.SPEED, 200.0, currentProjectile));
    }

    /**
     * a string representation of the elite monkey tower
     */
    public String toString(){
        return "GT";
    }
    
    /**
     * get the cost of each upgrade
     * @param upgr the upgrade associated to the tower
     * @return the cost of the upgrade depending on the UpgradeType
     * @see Tower
     */
    public int getUpgradeCost(Upgrade upgr) {
        switch (upgr.getUpgradeType()){
            case POWER:
                return 600;
            case RANGE:
                return 400;
            case SPEED:
                return 1000;
            default:
            throw new IllegalArgumentException("Unknown upgrade type: " + upgr.getUpgradeType());
        }
    }

    //TODO
}