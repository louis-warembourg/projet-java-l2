package towerdefense.projectile;

import towerdefense.*;

/** A class for extra bomb which inherits projectile */
public class ExtraBomb extends Projectile{
    
    /**
     * builds a new extra bomb with minimum strength 2
     */
    public ExtraBomb(){
        super();
        this.strength = 2;
        this.range = 200;
    }

// ==================== DISPLAY METHOD ====================

    /**
     * Print the name of the projectile
     * @return the name of the projectile
     */
    public String toString(){
        return "ExtraBomb";
    }


}