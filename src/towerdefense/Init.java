package towerdefense;

import java.util.*;

import towerdefense.util.*;
import towerdefense.tower.*;
import towerdefense.projectile.*;
import towerdefense.exception.*;

/** represent our Game mode */
public class Init {
    
    /**the mode of the game to be initialized */
    private int gameMode;
    private ArrayList<Integer> resistanceList;
    
    /**
     * initialises the assets of a new game
     * @param gameMode the mode of the game to be initialized
     */
    public Init(int gameMode){
        this.gameMode = gameMode;
        this.resistanceList = new ArrayList<Integer>();
        this.resistanceList.add(1);
        this.resistanceList.add(2);
        this.resistanceList.add(4);

    }

// ==================== METHOD BOARD ==================== 

    /**
     * Create a random board if the game mod equal 0 and a classic board if the game mod equal 1
     * @param lengthBoard the height of the board
     * @param widthBoard the width of the board
     * @return the board with a specific width and height
     */
    public Board generateBoard(int lengthBoard, int widthBoard){
        Board b;
        if(this.gameMode == 0){
            b = Board.createBoardRandom(lengthBoard, widthBoard);
            ArrayList<Cell> path = b.getPath();
            if (path == null || path.isEmpty()) {
                System.out.println("No path generated");
                return generateBoard(lengthBoard, widthBoard);
            }
        }
        else{
            b = Board.createBoardClassic(lengthBoard, widthBoard);
            
        }
        System.out.println("--- Board created ---");
        return b;
    }

// ==================== METHOD TOWER ==================== 

    /**
     * Create a tower depending on the tower type and place it on the board
     * 
     * @param b the board
     * @param type the typer of the tower
     * @param rand the module random
     * @param path the path
     * @param p the player who asked for creation of towers
     * @return the tower thas was placed
     * @throws TowerTypeException if the tower type doesn't exist
     */
    public Tower createTower(Board b, TowerType type, Random rand, ArrayList<Cell> path, Player p) throws TowerTypeException{

        int[] co = b.getRandomCoordinate(rand, path, this.gameMode);
        Tower t;
        switch(type){
            case BombMonkeyTower :
                t = new BombMonkeyTower(co[0], co[1], new Bomb());
                break;
            case DartMonkeyTower :
                t = new DartMonkeyTower(co[0], co[1], new Dart());
                break;
            case EliteMonkeyTower :
                t = new EliteMonkeyTower(co[0], co[1], new VeryPointyDart());
                break;
            case FreezeTower :
                t = new FreezeTower(co[0], co[1]);
                break;
            case GorillaTower :
                t = new GorillaTower(co[0], co[1], new PointyDart());
                break;
            case NeedleMonkeyTower :
                t = new NeedleMonkeyTower(co[0], co[1], new Needle());
                break;
            case SlowTower :
                t = new SlowTower(co[0], co[1]);
                break;
            default :
                throw new TowerTypeException("Tower type not defined");
        }
        p.spendResources(t.getCost());
        b.setTower(t, co[0], co[1]);
        return t;
    }

    /**
     * Place 2 tower of each type randomly in the board
     * 
     * @param b the board
     * @param rand the module random
     * @param path the path
     * @param p the player who buys and places the towers
     * @return the array list of tower
     * @exception TowerTypeException if a tower type isn't define
     */
    public ArrayList<Tower> placeAllTower(Board b, Random rand, ArrayList<Cell> path, Player p)throws TowerTypeException{
        ArrayList<Tower> towers = new ArrayList<>();
        for(int i = 0; i<2; i++){
            try{
                towers.add(createTower(b, TowerType.BombMonkeyTower, rand, path, p));
                towers.add(createTower(b, TowerType.DartMonkeyTower, rand, path, p));
                towers.add(createTower(b, TowerType.EliteMonkeyTower, rand, path, p));
                towers.add(createTower(b, TowerType.FreezeTower, rand, path, p));
                towers.add(createTower(b, TowerType.GorillaTower, rand, path, p));
                towers.add(createTower(b, TowerType.NeedleMonkeyTower, rand, path, p));
                towers.add(createTower(b, TowerType.SlowTower, rand, path, p));
            }
            catch(Exception e){
                throw new TowerTypeException("Tower type not defined for one of the towers");
            }
            
        }
        return towers;
    }

    /**
     * create a list of Manage Tower for each tower
     * 
     * @param towers the list of towers
     * @param b the board
     * @return the list of manageTower
    */
   public ArrayList<ManageTower> createManageTowers(ArrayList<Tower> towers, Board b){
        ArrayList<ManageTower> towerManagers = new ArrayList<>();
        for(Tower t : towers) {
            towerManagers.add(new ManageTower(t, b));
        }
        return towerManagers;
    }


// ==================== METHOD BALLOON ==================== 

    /**
     * Create all balloons depending on the game mod
     * 
     * @param nbBalloons the number of balloons
     * @param rand the module random
     * @param path the path
     * @param b the board
     * @param lengthBoard the length of the board
     * @param widthBoard the width of the board
     * @return the list of balloons
     */
    public ArrayList<Balloon> createBalloons(int nbBalloons, Random rand, ArrayList<Cell> path, Board b, int lengthBoard, int widthBoard){
        
        if(this.gameMode == 0){
            return this.createBalloonsRandom(nbBalloons, rand, path, b);
        }
        else{
            return this.createBalloonsClassic(nbBalloons, rand, path, b, lengthBoard, widthBoard);
        }
    }

    /**
     * Create all balloons on the mode Random
     * 
     * @param nbBalloons the number of balloons
     * @param rand the module random
     * @param path the path
     * @param b the board
     * @return the list of balloons
     */
    public ArrayList<Balloon> createBalloonsRandom(int nbBalloons, Random rand, ArrayList<Cell> path, Board b){
        
        ArrayList<Balloon> balloons = new ArrayList<>();
        Cell spawnCell = b.getPath().get(0);
        Cellable spawnCellable = (Cellable) spawnCell;
        for (int i = 0; i < nbBalloons; i++) {
            int speed = 40 + rand.nextInt(51);
            int resistance = this.resistanceList.get(rand.nextInt(this.resistanceList.size()));
            Balloon balloon = new Balloon(speed, resistance, path);
            spawnCellable.addBalloon(balloon);
            balloons.add(balloon);
        }
        return balloons;
    }

    /**
     * Create all balloons on the mode Classic
     * 
     * @param nbBalloons the number of balloons
     * @param rand the module random
     * @param path the path
     * @param b the board
     * @param lengthBoard the length of the board
     * @param widthBoard the width of the board
     * @return the list of balloons
     */
    public ArrayList<Balloon> createBalloonsClassic(int nbBalloons, Random rand, ArrayList<Cell> path, Board b, int lengthBoard, int widthBoard){
        ArrayList<Balloon> balloons = new ArrayList<>();
        Cell spawnCell;
        Cellable spawnCellable;

        for (int i = 0; i < nbBalloons; i++) {
            int randX = rand.nextInt(lengthBoard);
            int randY = rand.nextInt(widthBoard);
            Direction dir = StartEndChooser.getStartingBorderDirection(randX, randY, b.getBoard());
            boolean isABorder = StartEndChooser.isValidBorder(randY, randX, b.getBoard());
            while(!(isABorder)) {
                randX = rand.nextInt(lengthBoard);
                randY = rand.nextInt(widthBoard);
                dir = StartEndChooser.getStartingBorderDirection(randX, randY, b.getBoard());
                isABorder = StartEndChooser.isValidBorder(randY, randX, b.getBoard());
            }
            spawnCell = b.getCell(randX, randY);
            spawnCellable = (Cellable) spawnCell;
            int speed = 40 + rand.nextInt(50);
            int resistance = this.resistanceList.get(rand.nextInt(this.resistanceList.size()));
            Balloon balloon = new Balloon(speed, resistance, spawnCell.getX(), spawnCell.getY(), dir);
            spawnCellable.addBalloon(balloon);
            balloons.add(balloon);
        }
        return balloons;  
    }

    /**
     * create a list of manageTower
     * 
     * @param balloons the list of balloons
     * @param b the board
     * @return the list of manageBalloons
     */
    public ArrayList<ManageBalloon> createManageBalloons(ArrayList<Balloon> balloons, Board b){
        ArrayList<ManageBalloon> balloonManagers = new ArrayList<>();
        for(Balloon balloon : balloons){
            balloonManagers.add(new ManageBalloon(balloon, b));
        }
        return balloonManagers;
    }

}
