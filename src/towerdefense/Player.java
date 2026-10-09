package towerdefense;

/**
 * Represents a player in the tower defense game.
 * The player starts with a fixed amount of resources and lives.
 * The player can buy towers, upgrade them, or sell upgrades as long as they have enough resources.
 */
public class Player {

    /** the amount of resources (credits) the player currently has */
    private int resources;

    /** the number of lives the player currently has */
    private int lives;

    /**
     * Builds a new Player with initial resources and lives.
     * Resources: 2500
     * Lives: 20
     */
    public Player() {
        this.resources = 2500;
        this.lives = 20;
    }

// ==================== METHOD GET ====================

    /**
     * Gets the current amount of resources of the player.
     * @return the player's current resources
     */
    public int getResources() {
        return this.resources;
    }

    /**
     * Gets the current number of lives of the player.
     * @return the player's remaining lives
     */
    public int getLives() {
        return this.lives;
    }


// ==================== PREDICATE ====================

    /**
     * Checks if the player is still alive (has at least 1 life).
     * @return true if the player has lives remaining, false otherwise
     */
    public boolean isAlive() {
        return this.lives > 0;
    }

// ==================== RESSOURCES METHOD ====================
    /**
     * Spends a specified amount of resources.
     * Throws IllegalArgumentException if resources are insufficient.
     * @param amount the amount of resources to spend
     * @exception IllegalArgumentException if the player doesn't have insufficient ressources
     */
    public void spendResources(int amount) throws IllegalArgumentException {
        if (amount <= 0) return;
        if (amount > this.resources) {
            throw new IllegalArgumentException("Insufficient resources!");
        }
        this.resources -= amount;
    }

    /**
     * Adds a specified amount of resources to the player.
     * @param amount the amount of resources to add
     */
    public void addResources(int amount) {
        if (amount <= 0) return;
        this.resources += amount;
    }

// ==================== LIVES METHOD ====================

    /**
     * Decreases the player's lives by a specified amount.
     * If lives drop below 0, sets them to 0.
     * @param amount the number of lives to lose
     */
    public void loseLives(int amount) {
        if (amount <= 0) return;
        this.lives -= amount;
        if (this.lives < 0) this.lives = 0;
    }

// ==================== DISPLAY METHOD ====================

    /**
     * Returns a string representation of the player's resources and lives.
     * @return string representation of the player
     */
    @Override
    public String toString() {
        return "Player{resources=" + resources + ", lives=" + lives + "}";
    }
}