package towerdefense;
import towerdefense.util.*;

/**a class for projectile */
public class Upgrade{
    /**the type of upgrade applied */
    private UpgradeType upgradeType;

    /** the flat increase applied (used for POWER upgrades) */
    private int nbIncrease;

    /**the percentage of upgrade applied */
    private double percentage;

    /**the projectile of the tower to be upgraded */
    private Projectile givenProjectile;

    /**
     * builds a new upgrade 
     * @param upgradeType the type of ugrade to be created
     * @param percentage the percentage of the upgrade applied
     * @param givenProjectile the projectile on which an upgrade
     */
    public Upgrade(UpgradeType upgradeType, double percentage, Projectile givenProjectile){
        this.upgradeType = upgradeType;
        this.percentage = percentage;
        this.givenProjectile = givenProjectile;
        this.nbIncrease =0;  // default for non-flat upgrades
    }
    
     /**
     * builds a new upgrade (for POWER)
     * @param upgradeType the type of upgrade
     * @param nbIncrease the flat increase value
     */
    public Upgrade(UpgradeType upgradeType, int nbIncrease) {
        this.upgradeType = upgradeType;
        this.nbIncrease = nbIncrease;
        this.percentage = 0; // default for non-percentage upgrades
        this.givenProjectile = null;
    }


// ==================== METHOD GET ====================

     /**
      * Get the upgrade type
      * @return this upgrade's type
      */
    public UpgradeType getUpgradeType(){
        return this.upgradeType;
    }

    /**
     * get the percentage of an upgrade
     * @return this upgrade's percentage
     */
    public double getPercentage(){
        return this.percentage;
    }

    /**
     * get the new projectile provided by an update
     * @return this update's projectile
     */
    public Projectile getGivenProjectile(){
        return this.givenProjectile;
    }

     /**
      * Get the flat increase (for POWER)
      * @return this update's flat increase
      */
    public int getNbIncrease() {
        return this.nbIncrease;
    }

// ==================== DISPLAY METHOD ====================

    /**
     * a string representation of an update
     */
    public String toString(){
        switch(this.upgradeType){
            case POWER:
                return "(Increase of "+this.upgradeType.name()+" by "+this.nbIncrease+")";
            case RANGE:
                return "(Increase of "+this.upgradeType.name()+" by "+this.percentage+" percent)";
            case SPEED:
                return "(Increase of "+this.upgradeType.name()+" by coefficient of "+this.percentage+" percent)";
            case MODIFY_PROJECTILE:
                return "(Modification of projectile to "+this.givenProjectile+")";
            default:
                return "Unknown Update!!!";
        }
    }

}