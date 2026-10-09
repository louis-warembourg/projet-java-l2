package towerdefense;

import java.util.List;

/**
 * An interface for cell methods
 */
public interface Cellable {

    /**
     * Returns the list of balloons currently in the cell.
     * @return list of balloons
     */
    List<Balloon> getBalloonList();

    /**
     * Adds a balloon to the cell.
     * @param balloon the balloon to add
     */
    void addBalloon(Balloon balloon);

    /**
     * Removes a balloon from the cell.
     * @param balloon the balloon to remove
     */
    void removeBalloon(Balloon balloon);

    /**
     * Checks whether the cell has no balloon.
     * @return true if no balloon is present
     */
    boolean hasNoBalloon();

    /**
     * Adds a tower to the cell.
     * @param tower the tower to add
     */
    void addTower(Tower tower);

    /**
     * Removes a tower from the cell.
     */
    void removeTower();

    /**
     * Sell the tower from the cell.
     * @return true if the tower was successfully sold, false otherwise
     */
    boolean sellTower();
}