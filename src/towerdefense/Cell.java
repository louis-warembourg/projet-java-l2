package towerdefense;

import java.util.ArrayList;

/**
 * Abstract class representing a cell in the grid.
 */
public abstract class Cell implements Cellable {
    
    /**the x-coordinate of the cell */
    protected int x;

    /**the y-coordinate of the cell */
    protected int y;

    /**
     * builds a new abstract cell
     * @param x the x-coordinate of the cell
     * @param y the y-coordinate of the cell
     */
    protected Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

// ==================== METHOD GET ==================== 

    /**
     * get the x-coordinate of the cell
     * @return this cell's x-coordinate 
     */
    public int getX() {
        return x;
    }

    /**
     * get the y-coordinate of the cell
     * @return this cell's y-coordinate 
     */
    public int getY() {
        return y;
    }

    /**
     * get the list of the balloons
     * @return a list of balloons 
     */
    public ArrayList<Balloon> getBalloonList() {
        return new ArrayList<>(); 
    }

    public Tower getTower(){
        return null;
    }

// ==================== METHOD BALLOON ==================== 
    
    /**
     * verifies if a cell has no balloon 
     * @return true if a cell has no balloon and false if not
     */
    public boolean hasNoBalloon() {
        return true;
    }

    /**
     * places a balloon to a cell
     * @param balloon the balloon to be placed
     */
    public void addBalloon(Balloon balloon) {
    }
    
    /**
     * removes a balloon from a cell
     * @param balloon the balloon to be removed
     */
    public void removeBalloon(Balloon balloon) {
    }
    


    // ==================== METHOD TOWER  ==================== 
    
    /**
     * verifies if a cell has a tower
     * @return true if a cell has a tower and false if not
     */
    public boolean hasTower(){
        return true;
    }

    /**
     * adds a tower to a cell
     * @param tower the tower to be added
     */
    public void addTower(Tower tower) {
    }
    
    /**
     * removes a tower from a cell
     */
    public void removeTower() {
    }

    /**
     * sells a tower
     */
    public boolean sellTower(){
        return false;
    }

    
}


