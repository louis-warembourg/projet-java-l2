package towerdefense;

import java.util.*;

import towerdefense.tower.EliteMonkeyTower;
import towerdefense.util.Direction;

/** a class for managing towers*/
public class ManageTower {
    
    /**the tower to be managed  */
    private Tower t;
    
    /**the board in which the tower manager operates */
    private Board b;

    /**the balloon shot by the tower */
    private Balloon target;

    /**the shooting clock of a tower */
    private int shootingClock;

    /** the list of manage projectile for bombTower */
    private ManageProjectile mp;

    /**
     * builds a new tower manager 
     * @param t the tower to be managed
     * @param b the board in which the tower manager operates 
     */
    public ManageTower(Tower t, Board b){
        this.t = t;
        this.b = b;
        this.target = null;
        this.shootingClock = t.getShootingSpeed()/100;
        this.mp = new ManageProjectile(t.getProjectile(), t, b);
    }

// ==================== METHOD GET ====================    

    /**
     * get the board of the tower manager 
     * @return this tower manager's board
     */
    public Board getBoard(){
        return this.b;
    }

    /**
     * get the tower to be managed
     * @return the tower which is managed
     */
    public Tower getTower(){
        return this.t;
    }

    /**
     * get the ballon of the tower manager 
     * @return this tower manager's ballon
     */
    public Balloon getTarget(){
        return this.target;
    }

    /**
     * get the shooting time of the tower manager 
     * @return this tower manager's shooting time
     */
    public int getShootingTime(){
        return this.shootingClock;
    }

    public ManageProjectile getManageProjectile(){
        return this.mp;
    }

// ==================== METHOD GET (DISTANCE) ====================

    /**
     * calculates the distance from a balloon to the end of the path, based on the position of the balloon and the position of the tower, for both random and classic boards
     * @param cellInRange the cells in the range of the tower, used to determine if the balloon is in range of the tower
     * @param cell the cell where the balloon is located
     * @param previousCell the cell before the balloon on the path, used to determine the position of the balloon on the path
     * @param nextCell the cell after the balloon on the path, used to determine the position of the balloon on the path
     * @param useRandomPath a boolean indicating whether the board is random or not, used to determine the method of calculating the distance to the end of the path
     * @param balloon the balloon for which the distance is calculated
     * @return the distance from the balloon to the end of the path
     */
    public int getMinDistance(Map<Cell, Integer> cellInRange, Cell cell, Cell previousCell, Cell nextCell, boolean useRandomPath, Balloon balloon) {
    
        if (!cellInRange.containsKey(cell)) {
            return Integer.MAX_VALUE;
        }
        
        int coverage = cellInRange.get(cell);
        int progress = balloon.getProgress();

        
        if (coverage == 100) {
            return useRandomPath ? getDistanceToEndRandom(balloon) : getDistanceToEndClassic(balloon);
        }
        
        boolean movingTowardTower = false;
        
        if (useRandomPath) {
            movingTowardTower = getMinDistanceRandom(balloon, cell);
        }
        else {
            movingTowardTower = getMinDistanceClassic(balloon, cell);
        }

        
        boolean inRange;
        if (movingTowardTower) {
            inRange = progress > (100 - coverage);
        } 
        else {
            inRange = progress <= coverage;
        }
        if (inRange) {
            return useRandomPath ? getDistanceToEndRandom(balloon) : getDistanceToEndClassic(balloon);
        }
        return Integer.MAX_VALUE;
    }

    /**
     * verifies if the balloon is approaching from a tower, in a classic board
     * @param balloon the balloon we are following
     * @param cell the cell in whiwh the balloon is located
     * @return if the balloon is approaching or not
     */
    private boolean getMinDistanceClassic(Balloon balloon, Cell cell){
            
        boolean movingTowardTower = false;
        Direction dir = balloon.getDirection();
        int towerX = this.t.getCurrentCell()[0];
        int towerY = this.t.getCurrentCell()[1];
        int cellX = cell.getX();
        int cellY = cell.getY();
            
        switch(dir) {
            case haut:
                movingTowardTower = cellY > towerY;
                break;
            case bas:
                movingTowardTower = cellY < towerY;
                break;
            case gauche:
                movingTowardTower = cellX > towerX;
                break;
            case droite:
                movingTowardTower = cellX < towerX;
                break;
        }
        return movingTowardTower;
    }

    /**
     * verifies if the balloon is approaching from a tower, in a random board
     * @param balloon the balloon we are following
     * @param cell the cell in whiwh the balloon is located
     * @return if the balloon is approaching or not
     */
    private boolean getMinDistanceRandom(Balloon balloon, Cell cell){
        boolean movingTowardTower = false;
        Cell currentCell = this.b.getCellOfBalloon(balloon);
            int cellIndex = -1;
            ArrayList<Cell> path = this.b.getPath();
            for (int i = 0; i < path.size(); i++) {
                if (path.get(i).getX() == currentCell.getX() && path.get(i).getY() == currentCell.getY()) {
                    cellIndex = i;
                    break;
                }
            }
            
            int towerX = this.t.getCurrentCell()[0];
            int towerY = this.t.getCurrentCell()[1];
            int cellX = cell.getX();
            int cellY = cell.getY();
            
            double distToTowerNow = Math.sqrt(Math.pow(cellX - towerX, 2) + Math.pow(cellY - towerY, 2));
            
            if (cellIndex > 0) {
                Cell prev = path.get(cellIndex - 1);
                double distToTowerPrev = Math.sqrt(Math.pow(prev.getX() - towerX, 2) + Math.pow(prev.getY() - towerY, 2));
                movingTowardTower = distToTowerNow < distToTowerPrev;
            }
        return movingTowardTower;
    }
    
    /**
     * calculates the distance from a balloon to the end of the path for classic boards 
     * @param balloon the balloon for which the distance is calculated
     * @return the distance from the balloon to the end of the path
     */
    private int getDistanceToEndClassic(Balloon balloon){
        Cell end = this.b.getEndClassicPath(balloon);
        
        int distance;
        if(balloon.getX() == end.getX()){
            distance = (int) Math.abs(end.getY() - balloon.getY());
        }
        else{
            distance = (int) Math.abs(end.getX() - balloon.getX());
        }
        return distance;
    }

    /**
     * calculates the distance from a balloon to the end of the path for random boards 
     * @param balloon the balloon for which the distance is calculated
     * @return the distance from the balloon to the end of the path
     */
    private int getDistanceToEndRandom(Balloon balloon){
        Cell currentCell = this.b.getCellOfBalloon(balloon);
        if (currentCell == null) return Integer.MAX_VALUE;
        
        for (int i = 0; i < this.b.getPath().size(); i++) {
            Cell cell = this.b.getPath().get(i);   
            if (cell.getX() == currentCell.getX() && cell.getY() == currentCell.getY()) {
                return this.b.getPath().size() - i;
            }
        }
        return Integer.MAX_VALUE;
    }

// ==================== METHOD GET CELL ====================

    /**
     * provides the range of cells where a tower can shoot
     * @return the list of cells in the range
     */
    public Map<Cell, Integer> getCellsInRange(){
        Map<Cell, Integer> res = new HashMap<>();
        int cx = this.t.getCurrentCell()[0];
        int cy = this.t.getCurrentCell()[1];
        int r = this.t.getRange();
        if (r == 0) { 
            for (int y = 0; y < this.b.getY(); y++) {
                for (int x = 0; x < this.b.getX(); x++) {
                    res.put(this.b.getCell(x, y), 100);
                }
            }
            return res;
        }

        int dft = (r - (r % 100)) / 100;
        if(r % 100 > 0) dft += 1;
        
        for(int dy = -dft; dy <= dft; dy++){
            for(int dx = -dft; dx <= dft; dx++){
                int nx = cx + dx;
                int ny = cy + dy;
                if(nx >= 0 && nx < this.b.getBoard()[0].length && ny >= 0 && ny < this.b.getBoard().length){
                    if((dx == -dft || dx == dft || dy == -dft || dy == dft) && r % 100 > 0){
                        res.put(this.b.getCell(nx, ny), r % 100);
                    } else { 
                        res.put(this.b.getCell(nx, ny), 100);
                    }
                }
            }
        }
        return res;
    }

    /**
     * returns the corners of the range of a tower
     * @return the corners of the range of the tower
     */
    public ArrayList<Cell> getCornersOfRange(){
        ArrayList<Cell> res = new ArrayList<>();
        int cx = this.t.getCurrentCell()[0];
        int cy = this.t.getCurrentCell()[1];
        int r = this.t.getRange();
        int dft = (r - (r % 100)) / 100;
        if(r % 100 > 0){
            dft += 1;
        }
        if (cx - dft >= 0 && cy - dft >= 0) {
            res.add(this.b.getCell(cx - dft, cy - dft));
        }
        if (cx - dft >= 0 && cy + dft < this.b.getBoard().length) {
            res.add(this.b.getCell(cx - dft, cy + dft));
        }
        if (cx + dft < this.b.getBoard()[0].length && cy - dft >= 0) {
            res.add(this.b.getCell(cx + dft, cy - dft));
        }
        if (cx + dft < this.b.getBoard()[0].length && cy + dft < this.b.getBoard().length) {
            res.add(this.b.getCell(cx + dft, cy + dft));
        }
        return res;
    }

// ==================== PREDICATE ====================

    /**
     * verifies if a shoot can be made
     * @return true if a shoot can be made and false if not
     */
    public boolean canShoot(){
        return this.target != null && !this.target.isDead() && this.shootingClock >= t.getShootingSpeed()/100;
    }

    /**
     * verifies if the shooting speed was reset 
     * @return true if the shooting speed was reset and false if not
     */
    public boolean shootingSpeedIsReset(){
        return this.shootingClock == 0 || (((Integer) this.t.getShootingSpeed()/100) - this.getShootingTime()) == 0;
    }

// ==================== METHOD SHOOTING CLOCK ====================


    /**
     * increases the shooting clock by 1 unit
     */
    public void increaseShootingClock(){
        this.shootingClock += 1;
    }

    /**
     * sets the shooting clock to zero
     */
    public void resetShootClock(){
        this.shootingClock = 0;
    }


// ==================== METHOD SHOOT ====================


    /**
     * helps us to identify the target of a tower, depending onthe minimum distance between the balloon and the end of the path,
     * and its presence within the range of the tower
     * @param useRandomPath a boolean indicating whether the board is random or not, used to determine the method of calculating the distance to the end of the path
     * @return the target which has been identified
     */
    public Balloon identifyTarget(boolean useRandomPath) {
        Balloon target = null;
        int minDistance = Integer.MAX_VALUE - 1;
        Map<Cell, Integer> cellInRange = this.getCellsInRange();

        for(Cell cell : cellInRange.keySet()) {
            for(Balloon balloon : cell.getBalloonList()) {
                int distance;
                if(useRandomPath){
                    Cell previousCell;
                    Cell nextCell;
                    ArrayList<Cell> path = this.b.getPath();

                    int indiceCell = -1;
                    for (int i = 0; i < path.size(); i++) {
                        Cell c = path.get(i);
                        if (c.getX() == cell.getX() && c.getY() == cell.getY()) {
                            indiceCell = i;
                            break;
                        }
                    }
                    if(indiceCell == -1){
                        continue;
                    }
                    if(indiceCell >=1){
                        previousCell = path.get(indiceCell-1);
                    }
                    else{
                        previousCell = null;
                    }
                    if (indiceCell<= path.size()-2){
                        nextCell = path.get(indiceCell+1);
                    }
                    else{
                        nextCell = null;
                    }
                    distance = this.getMinDistance(cellInRange, cell, previousCell, nextCell, useRandomPath, balloon);
                }
                else{
                    Direction d = balloon.getOppositeDirection();
                    Cell previousCell = this.b.nextCell(cell, d);
                    Cell nextCell = this.b.nextCell(cell, balloon.getDirection());
                    distance = this.getMinDistance(cellInRange, cell, previousCell, nextCell, useRandomPath, balloon);
                }
                if(distance < minDistance){
                    target = balloon;
                    minDistance = distance;
                }
            }
        } 
    this.target = target;

    return target;
    }


    /**
     * manages the shooting of a tower, after identifying a proper target
     * used to determine the method of calculating the distance to the end of the path
     */
    public void shoot(){
        if(this.getTarget() != null){
            if(this.canShoot()){
                if(this.t.isFrozenTower() && (this.target.getFreezeRemaining()>0 && this.target.isFrozen())){
                    System.out.println("Balloon already freeze !");
                }
                else if(this.t.isSlowTower() && (this.target.getSlowRemaining()>0 && this.target.isSlowed())){
                    System.out.println("Balloon already slowed !");
                }
                else{
                    this.getManageProjectile().hurtBalloon(target);
                    this.resetShootClock();
                }

            }
        }
    }
}
