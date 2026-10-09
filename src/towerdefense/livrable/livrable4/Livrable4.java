package towerdefense.livrable.livrable4;

import java.util.*;

import towerdefense.*;
import towerdefense.util.Ask;
import towerdefense.util.Menu;

/**
 * Utility class used to simulate multiple rounds of the Tower Defense game.
 * 
 * This class provides static methods to:
 * - initialize the game
 * - manage upgrades
 * - simulate a sequence of rounds automatically
 */
public class Livrable4{
    
    /**
     * Initializes the game settings by asking the user for parameters.
     * 
     * This method:
     * - sets the game mode
     * - asks the user for board dimensions
     * 
     * @param g the game instance to configure
     * @param gameMode the game mode (0 or 1)
     * @return the Ask object used for user input
     */
    public static Ask setStart(Game g, int gameMode){
        Ask k = new Ask();
        g.setGameMode(gameMode);
        g.setLength(k.askInteger("enter the length of the board :", 4, 100));
        g.setWidth(k.askInteger("enter the width of the board :", 4, 100));
        return k;                                 
    }
    
    /**
     * Adds a random upgrade to towers that have available upgrades.
     * 
     * This method iterates through all tower managers and:
     * - checks if upgrades are available
     * - randomly selects an upgrade
     * - applies it to the tower
     * 
     * @param towersManagers list of all tower managers
     * @param rand random generator
     * @param towerWithUpgrades list of towers that already received upgrades
     * @param p the player (used to pay for upgrades)
     */
    public static void addUpgrade(ArrayList<ManageTower> towersManagers, Random rand, ArrayList<ManageTower> towerWithUpgrades, Player p){

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
     * - randomly selects one upgrade
     * - removes it and refunds the player
     * 
     * @param towersManagers list of all tower managers
     * @param rand random generator
     * @param towerWithUpgrades list of towers that currently have upgrades
     * @param p the player (used to refund resources)
     */
    public static void removeUpgrade(ArrayList<ManageTower> towersManagers, Random rand, ArrayList<ManageTower> towerWithUpgrades, Player p){
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
     * Simulates 10 rounds of the game automatically.
     * 
     * This method:
     * - initializes the game, board, and towers
     * - runs 10 rounds
     * - asks the user for the number of balloons at each round
     * - applies upgrades during the first rounds
     * - removes upgrades during later rounds
     * - displays results after each round
     * 
     * @param gameMode the game mode (0 or 1)
     */
    public static void play10Round(int gameMode){

        Player p = new Player();

        Game g = new Game(p);

        Ask k = setStart(g, gameMode);

        Random rand = new Random();

        Init init = new Init(gameMode);

        Board b = init.generateBoard(g.getLength(), g.getWidth());

        ArrayList<Tower> towers = new ArrayList<>();
        try{
            towers = init.placeAllTower(b, rand, b.getPath(), p);
        }
        catch(Exception e){
            System.out.println("Type de tour non définis !");
        }

        ArrayList<ManageTower> towersManagers = init.createManageTowers(towers, b);
        
        ArrayList<ManageTower> towerWithUpgrades = new ArrayList<>();

        for(int i =1; i<11; i++){
            p.addResources(5000);
            
            g.setNbBalloons(k.askInteger("Enter the numer of balloons :", 0, 1000));
            ArrayList<Balloon> balloons = init.createBalloons(g.getNbBalloons(), rand, b.getPath(), b, g.getLength(), g.getWidth());
            ArrayList<ManageBalloon> balloonsManagers = init.createManageBalloons(balloons, b);

            System.out.println("--- Balloon Créés et placés ---");
        
            Map<Balloon, Integer> exitTimes = new HashMap<>();
            System.out.println("=== Start Round : " + i + " ===");
            if(i<6){
                addUpgrade(towersManagers, rand, towerWithUpgrades, p);
            }
            else{
                removeUpgrade(towersManagers, rand, towerWithUpgrades, p);
            }

            ArrayList<ArrayList<Balloon>> result = g.startRound(b, balloons, balloonsManagers, exitTimes);

            ArrayList<Balloon> deadballoons = result.get(0);
            ArrayList<Balloon> exitedBalloons = result.get(1);

            System.out.println("=== Result Round : " + i + " ===");
            g.printResult(balloons, b, deadballoons, exitedBalloons, exitTimes);
            g.resetTick();
        }
    }
}
