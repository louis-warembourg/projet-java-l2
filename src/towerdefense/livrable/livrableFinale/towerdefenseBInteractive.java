package towerdefense.livrable.livrableFinale;

import towerdefense.Game;
import towerdefense.Player;
import towerdefense.util.*;

/**
 * Entry point for the Tower Defense game in interactive mode (mode B).
 * 
 * In this version:
 * - the game uses dynamic movement (gameMode = 1)
 * - the player interacts through a menu
 */
public class towerdefenseBInteractive {
    
    /**
     * Main method that starts the game in interactive mode.
     * 
     * It:
     * - creates a new game instance
     * - uses an interactive list chooser
     * - sets the game mode to 1 (dynamic/free mode)
     * - launches the game loop
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args){
        if(args.length != 2 || (!Ask.isNumber(args[0])) || (!Ask.isNumber(args[1])) || (Integer.parseInt(args[0]) <= 5) || (Integer.parseInt(args[1]) <= 5)){
            System.out.println("Warning: arguments error - expected 2 arguments but got " + args.length);
            throw new IllegalArgumentException("Expected 2 arguments: <length> <width>");
        }
        Game g = new Game(new Player());
        InteractiveListChooser<String> ilc = new InteractiveListChooser<>();
        g.setGameMode(1);
        g.setLength(Integer.parseInt(args[0]));
        g.setWidth(Integer.parseInt(args[1]));
        g.start(ilc);

    }
}