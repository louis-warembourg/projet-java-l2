package towerdefense.tower;

import java.util.ArrayList;

import towerdefense.*;
import towerdefense.util.*;

/** A class for freeze tower

*/
public class FreezeTower extends Tower{

    /**
     * builds a new freeze tower which inherits Tower
     * @param x the x-coordinate of the tower 
     * @param y the y-coordinate of the tower 
     * @see Tower
     */
    public FreezeTower(int x, int y){
        super(x, y, null, 100, 400, 1.0, 300, 1500);
        this.availableUpgrades = null;
    }

    /**
     * a string representation of the elite monkey tower
     */
    public String toString(){
        return "FT";
    }
    
    /**
     * shoots on a balloon if it is in range
     * @param target the balloon to be shot
     */
    public void shoot(Balloon target){
        if (!target.isFrozen() && target.getFrozenTime() == 0){
            target.freeze();
            System.out.println("FT successfully froze balloon!");
        }
    }

    /**
     * get the strength of the tower
     * @see Tower
     */
    public int getStrength(){
        return 0;
    }

    /**
     * get the cost of each upgrade
     * @param upgr the upgrade associated to the tower
     * @return the cost of the upgrade depending on the UpgradeType
     * @see Tower
     */
    public int getUpgradeCost(Upgrade upgr) {
        switch (upgr.getUpgradeType()){
            default:
            throw new IllegalArgumentException("Unknown upgrade type: " + upgr.getUpgradeType());
        }
    }

    public boolean isFrozenTower(){
        return true;
    }
}