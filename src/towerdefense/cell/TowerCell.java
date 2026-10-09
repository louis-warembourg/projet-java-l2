package towerdefense.cell;
import towerdefense.Cell;
import towerdefense.Tower;

/** A class for tower cell */
public class TowerCell extends Cell {

    /** The tower to be placed in the cell */
    private Tower tower;
    
    /**
     * builds cells that can contain towers, inherits Cell
     * @see Cell
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     */
    public TowerCell(int x, int y) {
        super(x, y); 
        this.tower = null;
    }

    /**
     * returns the tower contained within the cell
     * @return the tower contained within the cell
     */
    public Tower getTower() {
        return tower;
    }

    /**
     * adds a tower to the cell
     * @param tower the tower to be added
     */
    public void addTower(Tower tower) {
        this.tower = tower;
    }

    /**
     * removes the tower from the cell 
     */
    public void removeTower(){
        this.tower = null;
    }

    /**
     * verifies if the cell contains a tower
     * @return true if the cell contains a tower, false otherwise
     */
    public boolean hasTower(){
        return this.tower != null;
    }

    /**
     * sells the tower contained within the cell, which is equivalent to removing the tower from the cell  
     */
    public boolean sellTower(){
        boolean res = this.tower != null;
        this.tower = null;
        return res;
    }

    /**
     * a string representation of a tower cell
     */
    public String toString(){
        if (this.tower == null){
            return " ";
        }
        else{
            return this.tower.toString();
        }
    }
    
}