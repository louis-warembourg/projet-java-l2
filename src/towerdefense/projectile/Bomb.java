package towerdefense.projectile;

import towerdefense.*;

/** A class for bomb which inherits projectile */
public class Bomb extends Projectile{

    /**
     * builds a new bomb with minimum strength 2
     */
    public Bomb(){
        super();
        this.strength = 2;
        this.range = 100;
    }

// ==================== DISPLAY METHOD ====================

    /**
     * Print the name of the projectile
     * @return the name of the projectile
     */
    public String toString(){
        return "Bomb";
    }
}