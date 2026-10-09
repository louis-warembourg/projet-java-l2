package towerdefense.livrable.livrable3;

import java.util.*;

import towerdefense.*;
import towerdefense.util.Ask;

/**A class for third deliverable */
public class Livrable3a {
    /**
     * Main method that executes the balloon simulation.
     * 
     * @param args no argument necessary
     */
    public static void main(String[] args){

        Player p = new Player();
        Game g = new Game(p);

        Ask k = new Ask();
        g.setGameMode(k.askGameMode());
        g.setLength(k.askLengthBoard());
        g.setWidth(k.askWidthBoard());
        g.setNbBalloons(k.askNumberOfBalloons());

        Random rand = new Random();

        Init init = new Init(g.getGameMode());

        Board b = init.generateBoard(g.getLength(), g.getWidth());
        
        System.out.println("--- Création des Tours ---");

        ArrayList<Tower> towers = new ArrayList<>();
        try{
            towers = init.placeAllTower(b, rand, b.getPath(), p);
        }
        catch(Exception e){
            System.out.println("Type de tour non définis !");
        }

        ArrayList<ManageTower> towersManagers = init.createManageTowers(towers, b);

        ArrayList<Balloon> balloons = init.createBalloons(g.getNbBalloons(), rand, b.getPath(), b, g.getLength(), g.getWidth());
        ArrayList<ManageBalloon> balloonsManagers = init.createManageBalloons(balloons, b);

        System.out.println("--- Balloon Créés et placés ---");
        
        Map<Balloon, Integer> exitTimes = new HashMap<>();
        System.out.println("Début simulation...");

        ArrayList<ArrayList<Balloon>> result = g.startRound(b, balloons, balloonsManagers, exitTimes);

        ArrayList<Balloon> deadballoons = result.get(0);
        ArrayList<Balloon> exitedBalloons = result.get(1);

        g.printResult(balloons, b, deadballoons, exitedBalloons, exitTimes);
    }
}


