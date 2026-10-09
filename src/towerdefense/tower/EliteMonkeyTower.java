package towerdefense.tower;

import java.util.ArrayList;

import towerdefense.*;
import towerdefense.util.*;
//import towerdefense.projectile.*;


/**  A class for elite monkey tower */
public class EliteMonkeyTower extends Tower{

    /**
     * builds a new elite monkey tower which inherits Tower
     * @param x the x-coordinate of the tower 
     * @param y the y-coordinate of the tower 
     * @param currentProjectile the projectile held by the tower
     * @see Tower
     */
    public EliteMonkeyTower(int x, int y, Projectile currentProjectile){
        super(x, y, currentProjectile,0, 500,0, 0, 2000); 
        this.availableUpgrades = new ArrayList<>();
        this.availableUpgrades.add(new Upgrade(UpgradeType.POWER, 2));
        this.availableUpgrades.add(new Upgrade(UpgradeType.SPEED, 25.0, currentProjectile));   
    }

    /**
     * a string representation of the elite monkey tower
     */
    public String toString(){
        return "ET";
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
                return 300;
            case SPEED:
                return 200;
            default:
            throw new IllegalArgumentException("Unknown upgrade type: " + upgr.getUpgradeType());
        }
    }

    //TODO
}