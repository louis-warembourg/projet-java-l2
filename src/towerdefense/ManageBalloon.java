package towerdefense;

import java.util.*;

import towerdefense.cell.BalloonCell;
import towerdefense.cell.MixedCell;

/**a class for managment of  balloons

*/
public class ManageBalloon {

    /**the balloon which is managed  */
    private Balloon balloon;

    /**the board in which the balloon manager operates */
    private Board b;

    /**
     * builds a new balloon manager
     * @param balloon the balloon to be managed
     * @param b the board in which the balloon manager operates
     */
    public ManageBalloon(Balloon balloon, Board b){
        this.balloon = balloon;
        this.b = b;
    }

// ==================== METHOD GET ====================

    /**
     * get the balloon which is managed
     * @return the balloon which is managed
     */
    public Balloon getBalloon(){
        return this.balloon;
    }

    /**
     * get the board of the balloon manager
     * @return this balloon manager's board
     */
    public Board getBoard(){
        return this.b;
    }

// ==================== PREDICATE ====================
    
    /**
     * Checks if the balloon is at the end of the path or the board and can no longer move forward.
     * @param gameMode the mode of the game (wether random or classic).
     * @return true if the balloon is blocked at the end of the path or at the end of the board, false otherwise.
     */
    public boolean isBalloonAtEnd(int gameMode){
        if(gameMode == 0){
            return isRandomBalloonAtEnd();
        }
        else{
            return isClassicBalloonAtEnd();
        }
    }

    /**
     * Checks if the balloon is at the end of the path and can no longer move forward.
     * @return true if the balloon is blocked at the end of the path, false otherwise.
     */
    public boolean isRandomBalloonAtEnd() {
        if (this.b.getPath() == null || this.b.getPath().isEmpty()) {
            return false;
        }

        Cell lastCell = this.b.getPath().get(this.b.getPath().size() - 1);

        if (balloon.getX() == lastCell.getX() && this.balloon.getY() == lastCell.getY()) {
            if (balloon.getProgress() >= 100) {
                return true;
            }
        }

        boolean isOnPath = false;
        for (Cell cell : this.b.getPath()) {
            if (balloon.getX() == cell.getX() && balloon.getY() == cell.getY()) {
                isOnPath = true;
                break;
            }
        }

        if (!isOnPath) {
            return true;
        }
        
        return false;
    }

    /**
     * Checks if the balloon is at the end of the path and can no longer move forward.
     * @return true if the balloon is blocked at the end of the path, false otherwise.
     */
    public boolean isClassicBalloonAtEnd() {

        boolean isAtBorder = false;
        switch (this.balloon.getDirection()) {
            case haut:
                isAtBorder = this.balloon.getY() == 0;
                break;
            case bas:
                isAtBorder = this.balloon.getY() == this.b.getY() - 1;
                break;
            case gauche:
                isAtBorder = this.balloon.getX() == 0;
                break;
            case droite: 
                isAtBorder = this.balloon.getX() == this.b.getX() - 1;
                break;
        }
        return isAtBorder && this.balloon.getProgress() >= 100;

    }


// ==================== METHOD BALLOON ====================


    /**
     * Moves a balloon from one cell to the next along its path.
     * @param c The current BalloonCell containing the balloon.
     * @param gameMode the mode of the game (wether random or classic).
     */
    public void moveBalloon(Cell c, int gameMode){
        if(gameMode == 0){
            c.removeBalloon(this.balloon);
            this.balloon.moveRandomBalloon();
            Cell newCell = this.b.getCell(this.balloon.getX(), this.balloon.getY());
            if(newCell != c){
                this.balloon.resetProgress();
            }
            this.b.setBalloon(this.balloon, this.balloon.getX(), this.balloon.getY());
        }
        else{
             boolean isAtBorder = false;
        switch (this.balloon.getDirection()) {
            case haut:
                isAtBorder = this.balloon.getY() == 0;
                break;
            case bas:
                isAtBorder = this.balloon.getY() == this.b.getY() - 1;
                break;
            case gauche:
                isAtBorder = this.balloon.getX() == 0;
                break;
            case droite: 
                isAtBorder = this.balloon.getX() == this.b.getX() - 1;
                break;
        }
        
            if (!isAtBorder) {
                c.removeBalloon(this.balloon);
                this.balloon.moveClassicBalloon();
                this.balloon.resetProgress();
                this.b.setBalloon(this.balloon, this.balloon.getX(), this.balloon.getY());
            }
            else {
                if (this.balloon.getProgress() >= 100) {
                    this.balloon.setProgress(100);
                }
            }
        }
    }
}
