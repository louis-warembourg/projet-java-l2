package towerdefense.tower;

import java.util.ArrayList;

import towerdefense.*;
import towerdefense.util.*;
//import towerdefense.projectile.*;



/** A class for dart monkey tower */
public class DartMonkeyTower extends Tower{

    /**
     * builds a new dart monkey tower which inherits Tower
     * @param x the x-coordinate of the tower 
     * @param y the y-coordinate of the tower 
     * @param currentProjectile the projectile held by the tower
     * @see Tower
     */
    public DartMonkeyTower(int x, int y, Projectile currentProjectile){
        super(x, y, currentProjectile, 100, 200,0, 0, 1000);
        this.availableUpgrades = new ArrayList<>();
        this.availableUpgrades.add(new Upgrade(UpgradeType.POWER, 1));
        this.availableUpgrades.add(new Upgrade(UpgradeType.RANGE, 25.0, currentProjectile));
        this.availableUpgrades.add(new Upgrade(UpgradeType.SPEED, 25.0, currentProjectile));   
    }

    /**
     * a string representation of the dart monkey tower
     */
    public String toString(){
        return "DT";
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
                return 250;
            case RANGE:
                return 100;
            case SPEED:
                return 150;
            default:
            throw new IllegalArgumentException("Unknown upgrade type: " + upgr.getUpgradeType());
        }
    }

    //TODO
}