package towerdefense;

import java.util.*;
import towerdefense.util.*;
import towerdefense.exception.TowerTypeException;
import towerdefense.projectile.*;
import towerdefense.tower.*;
/**
 * Represent the Core of our Projects
 */
public class Game{
    
    /**the tick of the game */
    private int currentTick;
    /**the player of the game */
    private Player player;
    /**the mode of the game */
    private int gameMode;
    /**the default tick duration of the game */
    private static final int TICK_DURATION_MS = 100;
    /**the height of the board of the game */
    private int lengthBoard;
    /**the width of the board of the game */
    private int widthBoard;
    /**the number of balloons in the game */
    private int nbBalloons;
    /**the towers used in the game */
    private ArrayList<Tower> towers;
    /**a prompt to get requested action */
    private Ask k;
    /**the tower managers of the game */
    private ArrayList<ManageTower> towerManagers;

    /**
     * builds a new game
     * @param p the player of the new game
     */
    public Game(Player p){
        this.currentTick = 0;
        this.player = p;
        this.gameMode = 0;
        this.lengthBoard = 10;
        this.widthBoard = 10;
        this.nbBalloons = 5;
        this.towers = new ArrayList<Tower>();
        this.k = new Ask();
        this.towerManagers = new ArrayList<ManageTower>();
    }

// ==================== METHOD GET ==================== 

    /**
     * get the number of balloons
     * @return the number of balloons
     */
    public int getNbBalloons(){
        return this.nbBalloons;
    }
    
    /**
     * get the length of the board
     * @return the length of the board
     */
    public int getLength(){
        return this.lengthBoard;
    }

    /**
     * get the width of the board
     * @return the width of the board
     */
    public int getWidth(){
        return this.widthBoard;
    }

    /**
     * get the game mode
     * @return the game mode
     */
    public int getGameMode(){
        return this.gameMode;
    }

    /**
     * get the player
     * @return the player
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * get the current tick of the round
     * @return the current tick
     */
    public int getCurrentTick(){
        return this.currentTick;
    }

    /**
     * get the manage balloon for a specifical balloon
     * @param balloonManagers the list of manageBalloons
     * @param balloon the balloon associated with the ManageBalloon we want
     * @return the manager of the specified
     */
    public ManageBalloon getManageBalloon(ArrayList<ManageBalloon> balloonManagers, Balloon balloon){
        for (ManageBalloon mb : balloonManagers) {
            if (mb.getBalloon().equals(balloon)) {
                return mb;
            }
        }
        return null;
    }
    
    /**
     * provides the list of all towers in the game
     * @return the towers of the game
     */
    public ArrayList<Tower> getTowers(){
        return this.towers;
    }

    /**
     * provides the list of towers that have been upgraded
     * @return the towers which have been upgraded
     */
    public ArrayList<Tower> getUpgradedTowers(){
        ArrayList<Tower> upgradedTowers = new ArrayList<>();
        for(Tower t : this.towers){
            if(t.getCurrentUpgrades() != null && !t.getCurrentUpgrades().isEmpty()){
                upgradedTowers.add(t);
            }
        }
        return upgradedTowers;
    }


// ==================== METHOD SET ==================== 

    /**
     * resets the tick
     */
    public void resetTick(){
        this.currentTick = 0;
    }

    /**
     * set the number of balloons
     * @param nbBalloons the number of balloons
     */
    public void setNbBalloons(int nbBalloons){
        this.nbBalloons = nbBalloons;
    }

    /**
     * set the length of the board
     * @param length the length of the board
     */
    public void setLength(int length){
        this.lengthBoard = length;
    }

    /**
     * set the width of the board
     * @param width the width of the board
     */
    public void setWidth(int width){
        this.widthBoard = width;
    }

    /**
     * set the game mode
     * @param mod the game mode
     */
    public void setGameMode(int mod){
        this.gameMode = mod;
    }

    /**
     * set the player
     * @param p the player to be set
     */
    public void setPlayer(Player p){
        this.player = p;
    }

    /**
     * adds a tower in the board
     * @param b the board of the game
     * @param t the tower to be added
     * @param x the x-coordinate of the position where to place the tower
     * @param y the y-coordinate of the position where to place the tower
     * @param p the player who adds the tower
     * @return the tower which has been added
     */
    public Tower setTower(Board b, Tower t, int x, int y, Player p) throws IllegalArgumentException{
        try{
            p.spendResources(t.getCost());
        }
        catch(Exception e){
            throw new IllegalArgumentException("Insufficient resources for a Tower !"); 
        }
        b.setTower(t, x, y);
        this.towers.add(t);
        return t;
    }


// ==================== METHOD TOWER ==================== 
    
    /**
     * removes a tower from the board
     * @param b the board of the game
     * @param t the tower to be removed
     * @param x the x-coordinate of the tower
     * @param y the y-coordinate of the tower
     * @param p the player who removes the tower
     */
    public void removeTower(Board b, Tower t, int x, int y, Player p){
        b.sellTower(x, y);
        p.addResources(t.getCost());
        this.towers.remove(t);
    }

    /**
     * had fired all towers 
     * @param towerManagers the list of manageTower
     * @param deadBalloons the list of balloons who were destroyed
     * @param exitedBalloons the list of exited balloons
     * @param b the board of the game
     * @return the list of destroyed balloons
     */
    public ArrayList<Balloon> towersShoot(ArrayList<ManageTower> towerManagers, ArrayList<Balloon> deadBalloons, ArrayList<Balloon> exitedBalloons, Board b){
        for(ManageTower mt : towerManagers) {
            mt.identifyTarget(this.gameMode == 0);
            if(mt.canShoot()){
                System.out.println("Tower : " + mt.getTower().print());
                Balloon target = mt.getTarget();
                if (target != null && !deadBalloons.contains(target) && !exitedBalloons.contains(target)) {
                    if(target.isFrozen()){
                        System.out.println(target + " : " + "is already freeze " + target.getFrozenTime() + " tick, remains : " + target.getFreezeRemaining() + " ticks ");
                    }
                    if(target.isSlowed()){
                        System.out.println(target + " : " + "is already slowed " + target.getSlowedTime() + "tick, remains : " + target.getSlowRemaining() + " ticks ");
                    }
                    if (mt.getTower().isFrozenTower() || mt.getTower().isSlowTower()) {
                        System.out.println("the Tower : " + mt.getTower().print() + " freeze or slowed the balloon " + target.toString());
                        mt.shoot();
                    } 
                    else if(mt.getTower().isBombTower()){
                        for(Balloon hurtBalloon : mt.getManageProjectile().getBalloonsInImpact(mt.getManageProjectile().getCellsInImpact(b.getCell(target.getX(),target.getY())))){
                            System.out.println("the Tower : " + mt.getTower().print() + " shot the ballon " + hurtBalloon.toString());
                        }
                        mt.shoot();
                        for(Balloon hurtBalloon : mt.getManageProjectile().getBalloonsInImpact(mt.getManageProjectile().getCellsInImpact(b.getCell(target.getX(),target.getY())))){
                            System.out.println(hurtBalloon.toString() + "lost PV : " + mt.getTower().getStrength() + " PV remains : " + hurtBalloon.getResistance());
                            if(hurtBalloon.isDead() && !deadBalloons.contains(hurtBalloon)){
                                System.out.println(hurtBalloon.toString() + " est mort");
                                deadBalloons.add(hurtBalloon);
                                Cell hurtBalloonCell = b.getCell(hurtBalloon.getX(), hurtBalloon.getY());
                                if (hurtBalloonCell instanceof Cellable) {
                                    ((Cellable) hurtBalloonCell).removeBalloon(hurtBalloon);
                                }
                            }
                        }
                    }
                    else {
                        System.out.println("the Tower : " + mt.getTower().print() + " shot the ballon " + target.toString());
                        mt.shoot();
                        System.out.println("lost PV : " + mt.getTower().getStrength() + " PV remains : " + target.getResistance());
                    }

                    if(target.isDead() && !deadBalloons.contains(target)){
                        System.out.println(target.toString() + " est mort");
                        deadBalloons.add(target);
                        Cell targetCell = b.getCell(target.getX(), target.getY());
                        if (targetCell instanceof Cellable) {
                            ((Cellable) targetCell).removeBalloon(target);
                        }
                    }   
                }            
            }
            mt.increaseShootingClock(); 
        }
        return deadBalloons;
    }

// ==================== METHOD BALLOON ==================== 

    /**
     * Remove a balloon if it is at the end of his path
     * @param b the board of the game
     * @param balloon the balloon which is at the end of his path
     * @param exitedBalloons the list of exited balloons
     * @param exitTimes the map of exited balloons, and their ExitTimes
     */
    public void removeBalloonAtEnd(Board b, Balloon balloon, ArrayList<Balloon> exitedBalloons, Map<Balloon, Integer> exitTimes){
        exitTimes.put(balloon, this.currentTick);
        System.out.println(balloon + "tick output " + this.currentTick + " at the position (" + balloon.getX() + "," + balloon.getY() + ")");
        Cell currentCell = b.getCell(balloon.getX(), balloon.getY());
        currentCell.removeBalloon(balloon);

        exitedBalloons.add(balloon); 
    }


    /**
     * advances the balloon in is cell and move it on the next cell if the balloon is at the end of his actual cell
     * @param exitedBalloons the list of exited balloons
     * @param deadBalloons the list of balloons which were destroyed
     * @param balloon the specifical balloon
     * @param b the board
     * @param mb the manageBalloon of the specifical balloon
     */
    public void balloonProgress(ArrayList<Balloon> exitedBalloons, ArrayList<Balloon> deadBalloons, Balloon balloon, Board b, ManageBalloon mb){
    
        if (deadBalloons.contains(balloon) || exitedBalloons.contains(balloon)) {
            return;
        }

        if(balloon.isFrozen()) {
            if (balloon.getFreezeRemaining() > 0) {
                balloon.increaseFreezeOrSlowTime();
                return;
            } else {
                balloon.unfreeze();
            }
        }
            
        if(balloon.isSlowed()) {
            if(balloon.getSlowRemaining() > 0){
                balloon.increaseFreezeOrSlowTime();
                System.out.println(" Slow time : " + balloon.getSlowedTime());
            }
            else{
                balloon.unslowed();
            }
        }

        int oldProgress = balloon.getProgress();
        Cell oldPosition = b.getCell(balloon.getX(), balloon.getY());

        balloon.progress();

        if (balloon.isAtEndProgress()) {
            if (this.gameMode == 1) {
                boolean isAtBorder = false;
                switch (balloon.getDirection()) {
                    case haut: isAtBorder = balloon.getY() == 0; break;
                    case bas: isAtBorder = balloon.getY() == b.getY() - 1; break;
                    case gauche: isAtBorder = balloon.getX() == 0; break;
                    case droite: isAtBorder = balloon.getX() == b.getX() - 1; break;
                }
                if (isAtBorder) {
                    System.out.println(balloon + " is at border with progress " + balloon.getProgress() + " and will exit!");
                    return;
                }
            }
            if(!mb.isBalloonAtEnd(this.gameMode)){
                Cell currentCell = b.getCell(balloon.getX(), balloon.getY());
                mb.moveBalloon(currentCell, this.gameMode);
                if (mb.isBalloonAtEnd(this.gameMode)) {
                    return;
                }
                System.out.println(balloon + " from (" + oldPosition.getX() + "," + oldPosition.getY() +") move at (" + balloon.getX()+ "," + balloon.getY()+")");
            }
                
        }
        else{
            System.out.println(balloon + " progress from ( "+ oldProgress + " ) at ( " + balloon.getProgress() + " )");
        }
    } 

// ==================== METHOD PLAY ==================== 


    /**
     * play a tick of the round, moves all balloons and had shoot all towers
     * @param deadBalloons the list of balloons who were destroyed
     * @param exitedBalloons the list of exited balloons
     * @param it the iterator for the list of balloons 
     * @param balloonManagers the list of manageBalloons 
     * @param b the board of the game
     * @param exitTimes the map of exited balloons, and their exitTimes
     */
    public void playStep(ArrayList<Balloon> deadBalloons, ArrayList<Balloon> exitedBalloons, ArrayList<ManageBalloon> balloonManagers, Iterator<Balloon> it, Board b, Map<Balloon, Integer> exitTimes){
        
        System.out.println("\n----- Tower Shots -----");
        towersShoot(this.towerManagers, deadBalloons, exitedBalloons, b);

        System.out.println("\n----- Balloon move -----");
        while (it.hasNext()) {
                Balloon balloon = it.next();

                if (deadBalloons.contains(balloon) || exitedBalloons.contains(balloon)) {
                    continue;
                }
                ManageBalloon mb = getManageBalloon(balloonManagers, balloon);
                
                boolean balloonAtEnd = mb.isBalloonAtEnd(this.gameMode);
                
                if (balloonAtEnd){
                    removeBalloonAtEnd(b, balloon, exitedBalloons, exitTimes);
                    continue;
                }
                
                balloonProgress(exitedBalloons, deadBalloons, balloon, b, mb);

                balloonAtEnd = mb.isBalloonAtEnd(this.gameMode);

                if (balloonAtEnd){
                    removeBalloonAtEnd(b, balloon, exitedBalloons, exitTimes);
                    continue;
                }
        }
        
    }

    /**
     * play a random and continue as long as a balloon isn't exited or isn't destroyed
     * @param balloons the list of balloons
     * @param balloonManagers the list of balloons managers
     * @param b the board of the game
     * @param exitTimes the map of exited balloons, and their exitTimes
     * @return the list of dead balloons
     */
    public ArrayList<ArrayList<Balloon>> startRound(Board b, ArrayList<Balloon> balloons, ArrayList<ManageBalloon> balloonManagers, Map<Balloon, Integer> exitTimes){
         
        ArrayList<Balloon> exitedBalloons = new ArrayList<>();
        ArrayList<Balloon> deadBalloons = new ArrayList<>();
        while (!balloons.isEmpty()) {
            if (this.currentTick > 10000) { 
                System.out.println("ERROR: Infinite loop detected after " + this.currentTick + " ticks");
                break;
            }
            Iterator<Balloon> it = balloons.iterator();
            System.out.println("\n----- Tick : " + this.currentTick + " -----\n");
            playStep(deadBalloons, exitedBalloons, balloonManagers, it, b, exitTimes);
            
            balloons.removeAll(deadBalloons);
            balloons.removeAll(exitedBalloons);
            this.currentTick+= 100;
        }
        ArrayList<ArrayList<Balloon>> result = new ArrayList<>();
        result.add(deadBalloons);
        result.add(exitedBalloons);
        return result;
    }

// ==================== DISPLAY METHOD ==================== 

    /**
     * print the result for a round
     * 
     * @param remainingBalloons the list of remaining balloons in the board
     * @param b the board of the game
     * @param deadBalloons the list of balloons who were destroyed
     * @param exitedBalloons the list of balloons who went out of the board
     * @param exitTimes the map of exited balloons, and their exitTimes
     */
    public void printResult(ArrayList<Balloon> remainingBalloons, Board b, ArrayList<Balloon> deadBalloons, ArrayList<Balloon> exitedBalloons, Map<Balloon, Integer> exitTimes){
        if (!remainingBalloons.isEmpty()) {
            System.out.println("WARNING: " + remainingBalloons.size() + " remaining balloons not released");
        }
        b.printBoard();
        System.out.println("=== Results ===");
        System.out.println("Remaining life : " + this.player.getLives());
        System.out.println("Board size : " + this.lengthBoard + "x" + this.widthBoard);
        System.out.println("number of balloons : " + this.nbBalloons);
        if(this.gameMode == 0){
            System.out.println("Path length: " + b.getPath().size());
        }
        System.out.println("\nExited balloons :");
        for (Balloon balloon : exitedBalloons) {
            System.out.println(balloon + " at tick " + exitTimes.get(balloon));
        }

        System.out.println("\nDestroyed balloons :");
        for(Balloon balloon : deadBalloons){
            System.out.println(balloon.toString());

        }
    }
    
    /**
     * Initializes the game settings by asking the user
     * to define the board dimensions.
     * 
     * @return the Ask object used for user input
     */
    public Ask setStart(){
        Ask k = new Ask();
        this.setLength(k.askInteger("enter the length of the board :", 4, 100));
        this.setWidth(k.askInteger("enter the width of the board :", 4, 100));
        return k;                                 
    }
    /**
     * Adds a random upgrade to towers that have available upgrades.
     * 
     * This method iterates through all tower managers and:
     * - checks if upgrades are available
     * - randomly selects one upgrade
     * - applies it to the tower
     * 
     * @param towersManagers list of all tower managers
     * @param rand random generator
     * @param towerWithUpgrades list of towers already upgraded
     * @param p the player (used to pay for upgrades)
     */

    public void addUpgrade(ArrayList<ManageTower> towersManagers, Random rand, ArrayList<ManageTower> towerWithUpgrades, Player p){

        boolean find = false;
        Upgrade up;
        for(ManageTower mt : towersManagers){
            if(mt.getTower().getAvailableUpgrades() != null && !mt.getTower().getAvailableUpgrades().isEmpty()){
                if(towerWithUpgrades.contains(mt)){
                    up = mt.getTower().getAvailableUpgrades().get(rand.nextInt(mt.getTower().getAvailableUpgrades().size()));
                    mt.getTower().upgrade(up, p);
                }
                else{
                    towerWithUpgrades.add(mt);
                    up = mt.getTower().getAvailableUpgrades().get(rand.nextInt(mt.getTower().getAvailableUpgrades().size()));
                    mt.getTower().upgrade(up, p);
                }
                System.out.println(mt.getTower().toString() + " a eu l'upgrade : " + up.toString());
                find = true;
            }
        }
        if(!find){
            System.out.println("Aucune upgrade possible !");
        }
        /* 
        ManageTower mt = towersManagers.get(rand.nextInt(towersManagers.size()));
        while(mt.getTower().getAvailableUpgrades() == null || mt.getTower().getAvailableUpgrades().isEmpty()){
            int indice = rand.nextInt(towersManagers.size());
            mt = towersManagers.get(indice);
        }
        towerWithUpgrades.add(mt);
        Upgrade up = mt.getTower().getAvailableUpgrades().get(rand.nextInt(mt.getTower().getAvailableUpgrades().size()));
        mt.getTower().upgrade(up, p);
        System.out.println(mt.getTower().toString() + " a eu l'upgrade : " + up.toString());
        */
    }
    /**
     * Removes a random upgrade from towers that currently have upgrades.
     * 
     * This method:
     * - checks towers with active upgrades
     * - randomly selects an upgrade
     * - removes it and refunds the player
     * 
     * @param towersManagers list of all tower managers
     * @param rand random generator
     * @param towerWithUpgrades list of towers that have upgrades
     * @param p the player (used to refund resources)
     */

    public void removeUpgrade(ArrayList<ManageTower> towersManagers, Random rand, ArrayList<ManageTower> towerWithUpgrades, Player p){
        boolean find = false;
        Upgrade up;
        for(ManageTower mt : towersManagers){
            if(mt.getTower().getCurrentUpgrades().isEmpty()){
                towerWithUpgrades.remove(mt);
            }
            else{
                up = mt.getTower().getCurrentUpgrades().get(rand.nextInt(mt.getTower().getCurrentUpgrades().size()));
                mt.getTower().sellUpgrade(up, p);
                
                System.out.println(mt.getTower().toString() + " a enlevé l'upgrade : " + up.toString());
                find = true;
            }
        }
        if(!find){
            System.out.println("Aucune upgrade à enlever !");
        }
       /* 
        ManageTower mt = towerWithUpgrades.get(rand.nextInt(towerWithUpgrades.size()));
        while(mt.getTower().getCurrentUpgrades().isEmpty()){
            towerWithUpgrades.remove(mt);
            mt = towerWithUpgrades.get(rand.nextInt(towerWithUpgrades.size()));
        }
        Upgrade up = mt.getTower().getCurrentUpgrades().get(rand.nextInt(mt.getTower().getCurrentUpgrades().size()));
        mt.getTower().sellUpgrade(up, p);
        System.out.println(mt.getTower().toString() + " a enlevé l'upgrade : " + up.toString());
        */
    }
    /**
     * Starts the game and manages the main game loop.
     * 
     * This method:
     * - initializes the board and game parameters
     * - creates balloons and towers
     * - runs rounds until the player loses all lives
     * - increases difficulty at each round
     * - updates player resources and lives
     * 
     * @param lc the list chooser used for menu interactions
     */

    public void start(ListChooser<String> lc){

        Random rand = new Random();

        Ask k = new Ask();
        
        Init init = new Init(this.getGameMode());

        int nbBalloons = k.askInteger("enter the number of balloons :", 15, 1000);

        Board b = init.generateBoard(this.getLength(), this.getWidth());

        ArrayList<ManageTower> towersManagers = init.createManageTowers(towers, b);
        
        ArrayList<ManageTower> towerWithUpgrades = new ArrayList<>();
        
        Menu m = new Menu(k, this.player);

        int totalDestroyed = 0;
        int totalExited = 0;

        int nbRound = 1;

        while(this.player.isAlive()){
            
            this.setNbBalloons(nbBalloons + (nbRound-1) * 2);
            ArrayList<Balloon> balloons = init.createBalloons(this.getNbBalloons(), rand, b.getPath(), b, this.getLength(), this.getWidth());
            ArrayList<ManageBalloon> balloonsManagers = init.createManageBalloons(balloons, b);

            System.out.println("--- Balloon Créés et placés ---");
            
            Map<Balloon, Integer> exitTimes = new HashMap<>();
        
            System.out.println("=== Start Round : " + nbRound + " ===");

            m.mainMenu(this, b, lc);

            ArrayList<ArrayList<Balloon>> result = this.startRound(b, balloons, balloonsManagers, exitTimes);

            ArrayList<Balloon> deadballoons = result.get(0);
            ArrayList<Balloon> exitedBalloons = result.get(1);

            totalDestroyed += deadballoons.size();
            totalExited += exitedBalloons.size();

            this.player.loseLives(totalExited);
            this.player.addResources(totalDestroyed * 10);

            System.out.println("=== Result Round : " + nbRound + " ===");
            this.printResult(balloons, b, deadballoons,  exitedBalloons ,exitTimes);
            this.resetTick();
            nbRound++;
        }
        System.out.println("===============================================\n");
        System.out.println("Thanks for playing you've made it to Round " + Math.max(0, nbRound - 2) + " !\n");
        System.out.println("===============================================\n");
    }
    /**
     * Adds a tower manager to the game.
     * 
     * @param mt the tower manager to add
     */
    public void addManageTower(ManageTower mt){
        this.towerManagers.add(mt);
    }
    
    /**
     * Removes the tower manager associated with a given tower.
     * 
     * @param t the tower whose manager must be removed
     */
    public void removeManageTower(Tower t){
        for(ManageTower mt : this.towerManagers){
            if(mt.getTower().equals(t)){
                this.towerManagers.remove(mt);
                break;
            }
        }
    }

    
}    

