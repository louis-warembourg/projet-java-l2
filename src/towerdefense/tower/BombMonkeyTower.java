package towerdefense.tower;

import java.util.ArrayList;

import towerdefense.*;
import towerdefense.projectile.Bomb;
import towerdefense.projectile.ExtraBomb;
import towerdefense.util.*;


/**  A class for bomb monkey tower  */
public class BombMonkeyTower extends Tower{

    /**
     * builds a new bomb monkey tower which inherits Tower
     * @param x the x-coordinate of the tower 
     * @param y the y-coordinate of the tower 
     * @param currentProjectile the projectile held by the tower
     * @see Tower
     */
    public BombMonkeyTower(int x, int y, Projectile currentProjectile){
        super(x, y, currentProjectile, 150, 600,0, 0, 1800);
        this.availableUpgrades = new ArrayList<>();
        this.availableUpgrades.add(new Upgrade(UpgradeType.POWER, 1));
        this.availableUpgrades.add(new Upgrade(UpgradeType.RANGE, 50.0, new Bomb()));
        this.availableUpgrades.add(new Upgrade(UpgradeType.SPEED, 25.0, new Bomb()));
        this.availableUpgrades.add(new Upgrade(UpgradeType.MODIFY_PROJECTILE, 0,  new ExtraBomb()));
    }

    /**
     * a string representation of the bomb monkey tower
     */
    public String toString(){
        return "BT";
    }

    /**
     * get the cost of each upgrade
     * @see Tower
     */
    public int getUpgradeCost(Upgrade upgr) {
        switch (upgr.getUpgradeType()){
            case POWER:
                return 200;
            case RANGE:
                return 250;
            case SPEED:
                return 300;
            case MODIFY_PROJECTILE:
                return 400;
            default:
            throw new IllegalArgumentException("Unknown upgrade type: " + upgr.getUpgradeType());
        }
    }

    /**
     * confirms if this tower controls projectile of type bomb
     * @return returns true if this tower controls projectile of type bomb
     */
    public boolean isBombTower(){
        return true;
    }

        /**
     * remove an upgrade
     * @param u the upgrade to be removed
     * @throws IllegalArgumentException if update cannot be removed
     */
    public void removeUpgrade(Upgrade u) throws IllegalArgumentException{
        switch (u.getUpgradeType()){
            case POWER:
                this.getProjectile().changeStrength(u.getNbIncrease());
                break;
            case SPEED:
                this.setShootingSpeed((int) (this.getShootingSpeed() * (1 - u.getPercentage()/100)));
                break;
            case RANGE:
                this.setRange((int) (this.getRange() * (1 - u.getPercentage()/100)));
                break;
            case MODIFY_PROJECTILE:
                this.setProjectile(new Bomb());
        }
    }
}