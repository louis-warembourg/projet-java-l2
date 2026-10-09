package towerdefense;
import towerdefense.cell.*;

import java.util.*;

import towerdefense.util.Direction;
import towerdefense.util.StartEndChooser;

/**
 * Represents the game board containing cells and the path for balloons.
 */
public class Board {

    /**the height of the board */
    private int x;
    /**the width of the board */
    private int y;
    /**the array of cells composing the board */
    private Cell[][] board;
    /**the list of cells where the balloons move */
    private ArrayList<Cell> path;
    /**the list of balloons which are in the board*/       
    private ArrayList<Balloon> balloons = new ArrayList<>();
    /**the list of towers which are in the board */
    private ArrayList<Tower> towers = new ArrayList<>();


    /**
     * Constructs a Board with the specified grid and path.
     * @param board board of cells representing the game board.
     * @param path  List of cells forming the balloon path.
     */
    public Board(Cell[][] board, ArrayList<Cell> path){
        this.board = board;
        this.path = path;
        if (board != null){
            this.y = board.length;
            this.x = board[0].length;
        }
    }

// ==================== METHOD GET ====================

    /**
     * Returns the width (number of columns) of the board.
     * 
     * @return the width of the board
     */
    public int getX(){
        return this.x;
    }
    
    /**
     * Returns the height (number of rows) of the board.
     * 
     * @return the height of the board
     */
    public int getY(){
        return this.y;
    }
    
    /**
     * Returns the Board as a matrix of cells
     * @return The matrix representing the board
     */
    public Cell[][] getBoard() {
        return this.board;
    }
    
    /**
     * Returns a random coordinate within the board that is not occupied by a tower,
     * used for random boards.
     * @param rand The random number generator to use.
     * @param path The list of cells forming the balloon path, to ensure the random coordinate is not on the path.
     * @param gameMode the current gamemode
     * @return An array containing the x and y coordinates.
     */
    public int[] getRandomCoordinate(Random rand, ArrayList<Cell> path, int gameMode){
        if(gameMode == 0){
            int[] res = {0, 0};
            int x = rand.nextInt(this.getX());
            int y = rand.nextInt(this.getY());
            while (path.contains(this.getCell(x, y)) || this.getCell(x, y).hasTower()){
                x = rand.nextInt(this.getX());
                y = rand.nextInt(this.getY());
            }
            res[0] = x;
            res[1] = y;
            return res;
        }
        else{
            int[] res = {0, 0};
            int x = rand.nextInt(this.getX());
            int y = rand.nextInt(this.getY());
            res[0] = x;
            res[1] = y;
            return res;
        }
    }

    /**
     * Retrieves the cell that contains the specified balloon.
     * @param b The balloon to find.
     * @return The cell containing the balloon, or null if not found.
     */
    public Cell getCellOfBalloon(Balloon b) {
        for (int y = 0; y < this.board.length; y++) {
            for (int x = 0; x < this.board[0].length; x++) {
                Cell cell = this.board[y][x];
                if (cell.getBalloonList().contains(b)) {
                  return cell;
                }
            }
        }
        return null;
    }
    
    
    /**
    * Retrieves the cell at the specified coordinates.
    *
    * @param x The x-coordinate of the cell.
    * @param y The y-coordinate of the cell.
    * @return The cell at the specified coordinates.
    */
    public Cell getCell(int x, int y){
        return this.board[y][x];
    }
    
    /**
     * Returns the balloon path of the board
     * 
     * @return ArrayList of cells forming the balloon path
     */
    public ArrayList<Cell> getPath(){
        return this.path;
    }
    
    /**
    * Returns the cell where a balloon will exit the board in classic mode.
    *
    * @param balloon The balloon to calculate the exit point for.
    * @return The cell at the board border.
    */
    public Cell getEndClassicPath(Balloon balloon){
        int x = balloon.getX();
        int y = balloon.getY();
        Direction direction = balloon.getDirection();
        switch (direction) {
            case haut:
                return this.board[0][x];
            case bas:
                return this.board[this.y - 1][x];
            case droite:
                return this.board[y][this.x - 1];
            case gauche:
                return this.board[y][0];
            default:
                return null;
        }
    }

    /**
     * Retrieves all balloons currently on the path (random mode).
     * 
     * @return list of all balloons present on the path
     */
    public ArrayList<Balloon> getAllBalloonsRandom(){
        ArrayList<Balloon> res = new ArrayList<>();
        for(Cell c : this.path){
            res.addAll(c.getBalloonList());
        }
        return res;
    }

    /**
     * Retrieves all balloons present on the entire board (classic mode).
     * 
     * @return list of all balloons on the board
     */
    public ArrayList<Balloon> getAllBalloonsClassic(){
        ArrayList<Balloon> res = new ArrayList<>();
        for(int i = 0; i < this.board.length; i++){
            for(int j = 0; j < this.board[0].length; j++){
                res.addAll(this.board[i][j].getBalloonList());
            }
        }
        return res;
    }

    /**
     * Retrieves all towers currently placed on the board.
     * 
     * @return list of all towers
     */
    public ArrayList<Tower> getAllTowers(){
        ArrayList<Tower> res = new ArrayList<>();
        for(int i = 0; i < this.board.length; i++){
            for(int j = 0; j < this.board[0].length; j++){
                Tower t = this.board[i][j].getTower();
                if (t != null){
                    res.add(t);
                }
            }
        }
        return res;
    }

    /**
     * Creates managers for all balloons in classic mode.
     * 
     * @return list of ManageBalloon objects
     */
    public ArrayList<ManageBalloon> getAllManageBalloonsClassic(){
        ArrayList<ManageBalloon> res = new ArrayList<>();
        for(Balloon b : this.getAllBalloonsClassic()){
            res.add(new ManageBalloon(b, this));
        }
        return res;
    }

    /**
     * Creates managers for all balloons in random mode.
     * 
     * @return list of ManageBalloon objects
     */
    public ArrayList<ManageBalloon> getAllManageBalloonsRandom(){
        ArrayList<ManageBalloon> res = new ArrayList<>();
        for(Balloon b : this.getAllBalloonsRandom()){
            res.add(new ManageBalloon(b, this));
        }
        return res;
    }

    /**
     * Creates managers for all towers on the board.
     * 
     * @return list of ManageTower objects
     */
    public ArrayList<ManageTower> getAllManageTowers(){
        ArrayList<ManageTower> res = new ArrayList<>();
        for(Tower t : this.getAllTowers()){
            res.add(new ManageTower(t, this));
        }
        return res;
    }
    
    /**
     * Counts the number of cells without a tower.
     * 
     * @return number of unoccupied cells
     */
    public int getNbUnoccupiedCells(){
        int count = 0;
        for(int i = 0; i < this.board.length; i++){
            for(int j = 0; j < this.board[0].length; j++){
                if (!this.board[i][j].hasTower()){
                    count++;
                }
            }
        }
        return count;
    }

// ==================== METHOD SET ==================== 
                
    /**
    * Places a balloon at the specified coordinates on the board.
    * 
    * @param balloon The balloon to place on the board.
    * @param x The x-coordinate where to place the balloon.
    * @param y The y-coordinate where to place the balloon.
    */
    public void setBalloon(Balloon balloon, int x, int y){
        this.getCell(x, y).addBalloon(balloon);
        this.balloons.add(balloon);
    }
                
    /**
    * places a tower at a certain cell
    * @param tower the tower we need to place
    * @param x The x-coordinate where to place the tower.
    * @param y The y-coordinate where to place the tower.
    * @return true if the coordinates are within the board, false else.
    */
    public boolean setTower(Tower tower, int x, int y){
        if((x < 0 || y < 0) && (x>this.getX() || y>this.getY())){
            return false;
        }
        this.getCell(x, y).addTower(tower);
        this.towers.add(tower);
        return true;
    }

    /**
     * @param x The x-coordinate where to place the tower.
     * @param y The y-coordinate where to place the tower.
     * @return true if the coordinates are within the board and if it could have had a tower, false else.
     */
    public boolean sellTower(int x, int y){
        if((x < 0 || y < 0) && (x>this.getX() || y>this.getY())){
            return false;
        }
        return this.getCell(x, y).sellTower();
    }

    
// ==================== METHOD BOARD ====================     
    
    /**
     * Initializes the board with TowerCell instances.
     * @param x The width of the board (number of columns).
     * @param y The height of the board (number of rows).
     * @return A board of TowerCell objects representing the initialized board.
    */
    public static Cell[][] initBoard(int x, int y) {
        Cell[][] board = new Cell[y][x];
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                board[i][j] = new TowerCell(j, i);
            }
        }
        return board;
    }

    /**
     * Creates a classic board with MixedCell instances.
     *
     * @param x The width of the board (number of columns).
     * @param y The height of the board (number of rows).
     * @return A Board instance with MixedCell grid and no predefined path.
     */
    public static Board createBoardClassic(int x, int y){
        Cell[][] board = new Cell[y][x];
        for (int i = 0; i< y; i++){
            for (int j =0; j< x; j++){
                board[i][j] = new MixedCell(j, i, new ArrayList<>());
            }
        }
        return new Board(board, null);
    }

    /**
    * Creates a Board instance with a random path from a start to an end point.
    *
    * @param x The width of the board (number of columns).
    * @param y The height of the board (number of rows).
    * @return A Board instance with a grid and a random path.
    */
    public static Board createBoardRandom(int x, int y){

        Cell[][] board = initBoard(x, y);
        int[][] startEnd = StartEndChooser.generateStartEnd(board);
        Board b = new Board(board, new ArrayList<>());
        ArrayList<Cell> path = b.pathRandom(startEnd[0], startEnd[1]);

        while (path.isEmpty() || path.size() < x || path.size() < y){
            board = initBoard(x,y);
            b = new Board(board, new ArrayList<>());
            startEnd = StartEndChooser.generateStartEnd(board);
            path = b.pathRandom(startEnd[0], startEnd[1]);
        } 
        int ordre = 0;
        ArrayList<Cell> newPath = new ArrayList<>();
        for (Cell cell : path) {   
            BalloonCell balloonCell = new BalloonCell(cell.getX(), cell.getY(), new ArrayList<>(), ordre);
            board[cell.getY()][cell.getX()] = balloonCell;
            newPath.add(balloonCell);
            ordre += 1;
        }
        return new Board(board, newPath);
    }

// ==================== METHOD PATH ==================== 

    /**
    * Generates a random path from start to arrival coordinates
    * The algorithm has a 20% chance to move towards the destination and 80% chance for random move (changeable values)
    *
    * @param start The starting coordinates as an array: [x, y].
    * @param arrival The arrival coordinates as an array: [x, y].
    * @return An ArrayList of Cell objects representing the path from start to arrival. Return an empty list if not valid path is found.
    */
    public ArrayList<Cell> pathRandom(int[] start, int[] arrival){
    ArrayList<Cell> path = new ArrayList<Cell>();
    Cell actualCell = this.board[start[1]][start[0]];
    path.add(actualCell);
    
    Random rand = new Random();
    int maxSteps = this.x * this.y * 3;
    
    for (int step = 0; step < maxSteps; step++) {
        // We check to see if we have arrived
        if (actualCell.getX() == arrival[0] && actualCell.getY() == arrival[1]) {
            return path;
        }
        
        // We calculate the distance to the finish.
        int dx = arrival[0] - actualCell.getX();
        int dy = arrival[1] - actualCell.getY();
        
        // We generate a direction (20% toward the destination, 80% random)
        Direction direction;
        if (rand.nextDouble() < 0.2 && (dx != 0 || dy != 0)) {
            // Moving towards the destination
            if (Math.abs(dx) > Math.abs(dy)) {
                direction = (dx > 0) ? Direction.droite : Direction.gauche;
            } else {
                direction = (dy > 0) ? Direction.bas : Direction.haut;
            }
        } else {
            // Random direction
            direction = Direction.values()[rand.nextInt(Direction.values().length)];
        }
        
        Cell next = nextCell(actualCell, direction);
        
        if (next == null) {
            if (path.size() > 1) {
                path.remove(path.size() - 1);
                actualCell = path.get(path.size() - 1);
            } 
            else {
                break;
            }
            continue;
        }
        // We check whether the next cell has already been visited 
        boolean alreadyVisited = false;
        Iterator<Cell> iterator = path.iterator();
        while (iterator.hasNext() && !alreadyVisited) {
            Cell cell = iterator.next();
            if (cell.getX() == next.getX() && cell.getY() == next.getY()) {
                alreadyVisited = true;
            }
        }
        if (!alreadyVisited) {
            path.add(next);
            actualCell = next;
        } else if (path.size() > 1) {
            path.remove(path.size() - 1);
            actualCell = path.get(path.size() - 1);
        } else {
            // Stuck from the start
            break;
        }
    }
    
    // We check to see if our destination has been reached
    if (actualCell.getX() == arrival[0] && actualCell.getY() == arrival[1]) {
        return path;
    }
    
    return new ArrayList<Cell>();
}

// ==================== METHOD CELL ==================== 

    /**
    * Changes the type of the cell at the specified coordinates to a BalloonCell.
    * 
    * @param x The x-coordinate of the cell to change.
    * @param y The y-coordinate of the cell to change.
    * @param ordre The position order of the cell in the balloon path
    */
    public void changeCell(int x, int y, int ordre){
        this.board[y][x] = new BalloonCell(x,y, new ArrayList<>(), ordre);
    }

    
    /**
     * Returns the next cell in the specified direction from the current cell.
     * 
     * @param actualCell The current cell.
     * @param direction The direction in which to move.
     * @return The next cell in the specified direction or null if out of bounds.
    */
    public Cell nextCell(Cell actualCell, Direction direction) {
        int x = actualCell.getX();
        int y = actualCell.getY();
       
        switch (direction) {
           case haut:
               if (y > 0){
                   return this.board[y-1][x];
                }
                break;
            case bas:
                if (y < this.y - 1){
                    return this.board[y+1][x];
                }
                break;
            case droite:
                if (x < this.x - 1){
                    return this.board[y][x+1];
                }
                break;
            case gauche:
                if (x > 0){
                    return this.board[y][x - 1];
                }
                break;
        }
        return null;
    }


// ==================== METHOD DIRECTION ==================== 
            
            
    /**
    * Generates a random direction for path creation.
    *
    * @param x The x-coordinate of the current cell.
    * @param y The y-coordinate of the current cell.
    * @return A random Direction enum value.
    */
    public Direction directionRandom(int x, int y){
        Random rand = new Random();
        int i = rand.nextInt(Direction.values().length);
        return Direction.values()[i];
    }


// ==================== DISPLAY METHOD ==================== 
            
    /**
    * Prints the board in a grid format with borders.
    */
    public void printBoard(){
        String separation = "+------+";
        for(int k=0; k<this.board[0].length-1; k++){
            separation = separation+"------+";
        }
        for(int i=0; i<this.board.length; i++){
            String ligne = " |";
            for(int j=0; j<this.board[0].length; j++){
                int padding = 6 - this.getCell(j, i).toString().length();
                String spacePadding = "";
                for(int k = 0; k < padding - 1; k++) {
                    spacePadding = spacePadding + " ";
                }
                 ligne = ligne + " " + this.getCell(j, i).toString()+ spacePadding + "|";
            }
            System.out.println(separation);
            System.out.println(ligne);
        }
        System.out.println(separation);
    }

    

}

