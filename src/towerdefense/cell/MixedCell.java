package towerdefense.cell;

import towerdefense.Cell;
import towerdefense.Balloon;
import towerdefense.Tower;
import java.util.*;
/** A class for Mixed cell that is both ballon and Tower Cells
 */
public class MixedCell extends Cell{
    
    /**the list of balloons in the cell */
    private ArrayList<Balloon> balloonList = new ArrayList<>();
    
    /**the tower placed in the cell */
    private Tower tower;

    /**
     * builds cells that can contain balloons and towers, inherits Cell
     * @see Cell
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param balloonList the list of balloons in the cell
     */
    public MixedCell(int x, int y, ArrayList<Balloon> balloonList) {
        super(x, y);
        this.tower = null;
        this.balloonList = balloonList;
    }

    /**
     * returns the list of balloons in the cell
     * @return the list of balloons in the cell
     */
    public ArrayList<Balloon> getBalloonList() {
        return balloonList;
    }

    /**
     * adds a new balloon to the cell
     * @param balloon the balloon to be added
     */
    public void addBalloon(Balloon balloon) {
        balloonList.add(balloon);
    }

    /**
     * removes a balloon from the cell
     * @param balloon the balloon to be removed
     */
    public void removeBalloon(Balloon balloon){
        balloonList.remove(balloon);
    }

    /**
     * verifies if tha cell contains a balloon
     * @return true if the cell is empty, false otherwise
     */
    public boolean hasNoBalloon(){
        return balloonList.isEmpty();    
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
        if(this.tower == null){
        this.tower = tower;
        }
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
     * a string representation of a mixed cell
     */
    public String toString(){
        if(this.balloonList.isEmpty() && this.getTower() == null){
            return " , ";
        }
        else if (this.getTower() == null){
            return "B,";  
        }
        else if(this.balloonList.isEmpty()){
            return "," +this.tower.toString() + " ";
        }
        else{
            return "B," + this.tower.toString() + " ";
        }
    }
}