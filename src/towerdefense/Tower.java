package towerdefense;
import java.util.*; 

/**  a class for Tower */
public abstract class Tower {
    /** Refund percentage when selling an upgrade (80% by default) */
    public static final double UPGRADE_REFUND_PERCENTAGE = 0.8;

    /**the x-coordinate of the tower */
    protected int x;

    /**the y-coordinate of the tower */
    protected int y;

    /**the current projectile of the tower */
    protected Projectile currentProjectile;

    /**the shooting range of the tower */
    protected int range;

    /**the cost of the tower */
    protected int cost;

    /**the available upgrades of the tower */
    protected ArrayList<Upgrade> availableUpgrades;

    /**the current upgrades of the tower */
    protected ArrayList<Upgrade> currentUpgrades;

    /**the price of the tower when sold*/
    protected int discardPrice;

    /**the freezing capacity of the tower */
    protected double freezeCapacity;

    /**the freezing time of the tower */
    protected int freezeTime;

    /**the shooting speed of the tower */
    protected int shootingSpeed;
    /**  keeping initial values  so as we can be able to remove thier upgrades*/
    protected int baseRange;
    protected int baseShootingSpeed;

    /**
    * builds a new generic Tower
    * @param x the x-coordinate of the tower
    * @param y the y-coordinate of the tower
    * @param cost the cost of the tower
    * @param range the range in which the tower can shoot
    * @param shootingSpeed the shooting speed of the tower
    * @param currentProjectile the current projectile of the tower
    * @param freezeTime the freezing time of the tower
    * @param freezeCapacity the freezing capacity of the tower
    */
    public Tower(int x, int y, Projectile currentProjectile, int range, int cost, double freezeCapacity, int freezeTime, int shootingSpeed){
        this.x = x;
        this.y = y;
        this.currentProjectile = currentProjectile;
        this.range = range;
        this.baseRange = range;
        this.cost = cost;
        this.freezeCapacity = freezeCapacity;
        this.freezeTime = freezeTime; 
        this.shootingSpeed = shootingSpeed;
        this.baseShootingSpeed = shootingSpeed;
        this.currentUpgrades = new ArrayList<>();
        this.availableUpgrades = new ArrayList<>();
    }

// ==================== METHOD GET ====================


    /**
    * get the position x of the tower
    * @return the x position of the tower
    */
    public int getX(){
        return this.x;
    }

    /**
    * get the position y of the tower
    * @return the y position of the tower
    */
    public int getY(){
        return this.y;
    }

    /**
    * get the strength of the current projectile
    * @return the strength of the current projectile
    */
    public int getStrength(){
        return this.getProjectile().getStrength();
    }

    /**
    * provides the coordinates of the cell where the tower
    * @return the coordinates of the cell where the tower is
    */
    public int[] getCurrentCell(){
        return new int[]{this.x, this.y};
    }

    /**
    * provides the projectile of the tower
    * @return this tower's current projectile
    */
    public Projectile getProjectile(){
        return this.currentProjectile;
    }

    /**
     * provides the shooting speed of the tower
     * @return this tower's shooting speed
     */
    public int getShootingSpeed(){
        return this.shootingSpeed;
    }

    /**
    * provides the projectile of the tower
    * @return this tower's cost
    */
    public int getCost(){
        return this.cost;
    }

    /**
    * provides the range where the tower can shoot 
    * @return the range of cells where the tower can shoot 
    */
    public int getRange(){
        return this.range;
    }

    /**
    * get the cost of each upgrade
    * @param upgr the upgrade to get the cost for
    * @return this upgrade's cost
    */
    public abstract int getUpgradeCost(Upgrade upgr);

    /**
    * Gives a list of the currently applied Upgrades 
    * @return a list of Upgrades 
    */
    public ArrayList<Upgrade> getAvailableUpgrades(){
        return this.availableUpgrades;
    }

    /**
    * Gives a list of the currently applied Upgrades 
    * @return a list of Upgrades 
    */
    public ArrayList<Upgrade> getCurrentUpgrades(){
        return this.currentUpgrades;
    }


// ==================== METHOD SET ====================

    /**
    * changes the current projectile with a new one
    * @param newProjectile the projectile to replace with
    */
    public void setProjectile(Projectile newProjectile){
        this.currentProjectile = newProjectile;
    }

    /**
    * modifies the range of the tower 
    * @param range the new range to replace with
    */
    public void setRange(int range){
        this.range = range;
    }

    /**
    * modifies the shooting speed of the tower
    * @param shootingSpeed the new shooting speed of the tower
    */
    public void setShootingSpeed(int shootingSpeed){
        this.shootingSpeed = shootingSpeed;
    }

// ==================== PREDICATE ====================

    /**
    * verifies if a tower is a freezing tower
    * @return true if is freezing tower and false if not
    */
    public boolean isFrozenTower(){
        return false;
    }

    /**
    * verifies if a tower is a slow tower
    * @return true if is slow tower and false if not
    */
    public boolean isSlowTower(){
        return false;
    }

    /**
    * verifies if a tower is a bomb tower
    * @return true if is bomb tower and false if not
    */
    public boolean isBombTower(){
        return false;
    }

// ==================== UPGRADE METHOD ====================

    /**
    * Applies an upgrade to the tower if possible.
    * Checks availability, resource sufficiency and upgrade duplication.
    * @param upgr the upgrade to apply
    * @param player the player performing the upgrade
    * @exception IllegalArgumentException if upgrade unavailable or insufficient fund
    */
    public void upgrade(Upgrade upgr, Player player) throws IllegalArgumentException {
        if (!availableUpgrades.contains(upgr))
            throw new IllegalArgumentException("This upgrade is not available for this Tower.");

        if (currentUpgrades.contains(upgr))
            throw new IllegalArgumentException("This upgrade has already been apply.");

        int upgradeCost = getUpgradeCost(upgr);
        if (player.getResources() < upgradeCost)
            throw new IllegalArgumentException("Insuficient resources for an Upgrade.");

        player.spendResources(upgradeCost);
        makeUpgrade(upgr);

        currentUpgrades.add(upgr);
        availableUpgrades.remove(upgr);
    }

    /**
    * Sells an upgrade and refunds part of its cost.
    * The player receives 80% of the upgrade price.
    * @param upgr the upgrade to sell
    * @param player the player receiving refund
    * @exception IllegalArgumentException if upgrade unavailable or insufficient fund
    */
    public void sellUpgrade(Upgrade upgr, Player player) throws IllegalArgumentException {

        if (!currentUpgrades.contains(upgr))
            throw new IllegalArgumentException("This upgrade is not currently applied.");

        // Remove upgrade effect
        removeUpgrade(upgr);

        currentUpgrades.remove(upgr);

        int upgradeCost = getUpgradeCost(upgr);
        int refund = (int) (upgradeCost * UPGRADE_REFUND_PERCENTAGE); // 80% refund

        player.addResources(refund);

        availableUpgrades.add(upgr);
    }

    /**
    * performs the upgrade according to its type
    * @param u the upgrade to be made
    */
    public void makeUpgrade(Upgrade u){
        switch (u.getUpgradeType()){
            case POWER:
                this.getProjectile().changeStrength(u.getNbIncrease());
                break;
            case SPEED:
                this.setShootingSpeed((int) (this.getShootingSpeed() / (1 + u.getPercentage()/100)));
                break;
            case RANGE:
                this.setRange((int) (this.getRange() * (1 + u.getPercentage()/100)));
                break;
            case MODIFY_PROJECTILE:
                this.setProjectile(u.getGivenProjectile());
                break;
        }
    }

    /**
    * remove an upgrade
    * @param u the upgrade to be removed
    * @exception IllegalArgumentException if update cannot be removed
    */
    public void removeUpgrade(Upgrade u) throws IllegalArgumentException{
        switch (u.getUpgradeType()){
            case POWER:
                this.getProjectile().changeStrength(-u.getNbIncrease());
                break;
            case SPEED:
                this.setShootingSpeed((int) (this.getShootingSpeed() * (1 + u.getPercentage()/100)));
                break;
            case RANGE:
                this.setRange((int) (this.getRange() * (1 + -u.getPercentage()/100)));
                break;
            case MODIFY_PROJECTILE:
                throw new IllegalArgumentException("This upgrade cannot be removed since it changes the projectile of the tower.");
        }
    }


// ==================== SHOOT METHOD ====================

    /**
    * shoots on a balloon if it is in range
    * @param target the balloon to be shot
    */
    public void shoot(Balloon target){
        this.getProjectile().hurtBalloon(target);
    }

// ==================== DISPLAY METHOD ====================

    /**
    * a method which return a string presentation of the tower
    * @return res a string with the name of the tower, the position and the potential upgrade
    */
    public String print(){
        String res = this.toString() + " at position (" + this.getX() + "," + this.getY() + ")" ;
        if(!this.getCurrentUpgrades().isEmpty()){
            res = res + " with ";
            for(Upgrade u :this.getCurrentUpgrades()){
                res = res + u.getUpgradeType() + " upgrade ";
            }
        }
        return res;
    }



}