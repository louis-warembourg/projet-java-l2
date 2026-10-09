package towerdefense.cell;
import towerdefense.Cell;

import java.util.*;
import towerdefense.Balloon;
/** A class for balloon cell
 */
public class BalloonCell extends Cell{
    
    /**the list of balloons in the cell */
    private ArrayList<Balloon> balloonList = new ArrayList<>();
    
    /**the order of the cell in the path */
    private int ordre;

    /**
     * builds cells that can contain balloons but no towers, inherits Cell
     * @see Cell
     * @param x the x coordinate of the cell
     * @param y the y coordinate of the cell
     * @param balloonList the list of balloons in the cell
     * @param ordre the order of the cell in the path
     */
    public BalloonCell(int x, int y, ArrayList<Balloon> balloonList, int ordre) {
        super(x, y);
        this.balloonList = balloonList;
        this.ordre = ordre;
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
     * a string representation of the Balloon Cell
     */
    public String toString(){
        return "X"+this.ordre;
    }
}