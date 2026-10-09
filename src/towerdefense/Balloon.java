package towerdefense;

import java.util.*;

//import towerdefense.cell.*;
import towerdefense.util.Direction;

/**
 * A class representing a balloon in the game.
 */
public class Balloon {

    /** The path that the balloon follows */
    private ArrayList<Cell> path;

    /** The x-coordinate of the current position of the balloon */
    private int x;

    /** The y-coordinate of the current position of the balloon */
    private int y;

    /** Progress within the current cell (from 0.0 to just under 1.0) */
    private int progress;

    /** Speed of the balloon (fraction of a cell per tick) */
    private int speed;

    /** Resistance of the balloon */
    private int resistance;

    /** Indicates if the balloon is frozen */
    private boolean frozen;

    /** The direction in which the balloon moves */
    private Direction direction;

    /** Indicates if the balloon is slowed */
    private boolean slowed;

    private int frozenTime;

    private int slowedTime;

    private static final int FREEZE_TIME = 4;

    

    /**
     * Constructs a balloon with the specified speed, resistance, and path.
     * @param speed The speed of the balloon (fraction of a cell per tick).
     * @param resistance The resistance of the balloon.
     * @param path The path taken by the balloon.
     */
    public Balloon(int speed, int resistance, ArrayList<Cell> path) {
        this.speed = speed;
        this.resistance = resistance;
        this.path = path;
        this.x = path.get(0).getX();
        this.y = path.get(0).getY();
        this.progress = 0;
        this.frozen = false;
        this.direction = null;
        this.frozenTime = 0;
        this.slowedTime = 0;
        this.slowed = false;
    }

    /**
     * builds a new balloon adapted to the classic version of the game
     * @param speed the speed of the balloon 
     * @param resistance the resistance of the balloon
     * @param xStart the x-coordinate of the starting position of the balloon
     * @param yStart the y-coordinate of the starting position of the balloon
     * @param direction the direction in which the balloon moves
     */
    public Balloon(int speed, int resistance, int xStart, int yStart, Direction direction){
        this.speed = speed;
        this.resistance = resistance; 
        this.x = xStart;
        this.y = yStart;
        this.direction = direction;
        this.progress = 0;
        this.frozen = false;
        this.slowed = false;
        this.path= null;
        this.frozenTime = 0;
    }

// ==================== METHOD GET ====================
    
    /**
     * provides the x-coordinate of the cell where the balloon is
     * @return this balloon's x-coordinate
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * provides the y-coordinate of the cell where the balloon is
     * @return this balloon's y-coordinate
     */
    public int getY() {
        return this.y;
    }

    
    /**
     * provides the direction in which the ballon is moving
     * @return this balloon's direction
     */
    public Direction getDirection(){
        return this.direction;
    }

    public Direction getOppositeDirection(){
        if(this.direction == Direction.gauche){return Direction.droite;}
        if(this.direction == Direction.droite){return Direction.gauche;}
        if(this.direction == Direction.haut){return Direction.bas;}
        if(this.direction == Direction.bas){return Direction.haut;}
        return null;
    }
    
    /**
     * provides the speed at which the ballloon is moving
     * @return this balloon's speed
     */

    public int getSpeed() {
        return this.speed;
    }
    
    /**
     * provides the resistance of this balloon  
     * @return this balloon's resistance
     */
    public int getResistance() {
        return this.resistance;
    }
    
    /**
     * provides the progress of this balloon in the actual cell
     * @return this balloon's progress
    */
   public int getProgress(){
       return this.progress;
    }

    /**
     * get the frozen time of the balloon
     * @return this balloon's frozen time
     */
    public int getFrozenTime(){
        return this.frozenTime;
    }
 
    /**
     * get the slowed time of the balloon
     * @return this balloon's slowed time
     */
    public int getSlowedTime(){
        return this.slowedTime;
    }
 
    /**
     * calculates the remaining time to stay frozen 
     * @return the remaining time to stay frozen 
     */
    public int getFreezeRemaining(){
        return Math.max(0, FREEZE_TIME - this.frozenTime);
    }
 
    /**
     * calculates the remaining time to stay slowed
     * @return the remaining time to stay slowed
     */
    public int getSlowRemaining(){
            return Math.max(0, FREEZE_TIME - this.slowedTime);
    }
    

// ==================== PREDICATE ====================

    /**
     * verifies if the balloon is frozen
     * @return <code>true</code> if the balloon is frozen and <code>false</code> if not
    */
   public boolean isFrozen() {
       return this.frozen;
    }
    
    /**
     * verifies if a balloon is slowed
     * @return true if a balloon is slowed and false if not
    */
   public boolean isSlowed(){
       return this.slowed;
    }
    
    /**
     * verifies if a balloon is dead
     * @return true if a balloon is dead and false if not
    */
   public boolean isDead(){
       return this.resistance <= 0;
    }
    
    /**
     * verifies if the balloon is at the end of its progress
     * @return <code>true</code> if the balloon is at the end and <code>false</code> if not
     */
    public boolean isAtEndProgress(){
        return this.progress>=100;
    }

// ==================== METHOD FREEZE AND SLOW ====================

    /**
     * freezes the balloon
    */
   public void freeze() {
       this.frozen = true;
    }
    
    /**
     * unfreezes the balloon
     */
    public void unfreeze() {
        this.frozen = false;
        this.frozenTime = 0;
        
    }

    /**
     * slow the balloon
     */
    public void slowed(){
        this.slowed = true;
    }

    /**
     * unslow the balloon
     */
    public void unslowed(){
        this.slowed = false;
        this.slowedTime = 0;
        System.out.println("Balloon unslowed !");
    }



    /**
     * increase the freeze or slow time
     */
    public void increaseFreezeOrSlowTime(){
    if(this.isFrozen()){
        this.frozenTime += 1;
        if (this.frozenTime >= FREEZE_TIME) {
            this.unfreeze();
        }
    }
    if(this.isSlowed()){
        this.slowedTime += 1;
        if (this.slowedTime >= FREEZE_TIME) {
            this.unslowed();
        }
    }
}

// ==================== MOVE METHOD ====================

    public void resetProgress() {
        this.progress = this.progress % 100;
    }

    /**
     * moves the balloon to the next cell if exists
     */
    public void moveRandomBalloon(){
        if (this.path == null || this.path.isEmpty()) {
        return;
        }

        Cell lastCell = this.path.get(this.path.size() - 1);
        if (this.x == lastCell.getX() && this.y == lastCell.getY()) {
            return;
    }

        for(int i=0;i<this.path.size()-1;i++){
            if(this.x==this.path.get(i).getX() && this.y==this.path.get(i).getY()){
                this.x = this.path.get(i+1).getX();
                this.y = this.path.get(i+1).getY();
                return;
            }
        }
    }

    /**
     * Moves the balloon along its path based on its speed and current progress.
     */
    public void moveClassicBalloon(){
        if (direction == null) {
            return;
         }

        switch(this.direction){
            case haut:
                this.y -= 1;
                break;
            case bas:
                this.y += 1;
                break;
            case droite:
                this.x += 1;
                break;
            case gauche:
                this.x -= 1;
                break;
            }
        }


    /**
     * sets the progress of a balloon
     * @param n the new progress of the balloon
     */
    public void setProgress(int n){
        if (n>= 0 && n<=100){
            this.progress = n;
        }
        else{
            this.progress = 0;
        }
    }

    /**
     * updates the progress and provides it
     * @return this balloon's progress
     */
    public int progress(){
        if(isFrozen()) {
            return this.progress;
        }
        
        int effectiveSpeed = this.speed;
        if(isSlowed()) {
            effectiveSpeed = (int) (this.speed * 0.5);
        }
        
        this.progress += effectiveSpeed;
        return this.progress;
    }

// ==================== DAMAGE METHOD ====================

    /**
     * modify the resistance of a balloon 
     * @param h the new rrsistance of the balloon
     */
    public void hurt(int h){
        this.resistance = Math.max(0, h);
    } 

    

// ==================== DISPLAY METHOD ====================

    /**
     * a string representation of a balloon
     */
    public String toString(){
        return "Ballon d'attribut : " +this.getResistance()  +" resistance " + this.getSpeed() + " speed ";
    }
      
}
