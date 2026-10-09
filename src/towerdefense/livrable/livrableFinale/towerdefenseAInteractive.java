package towerdefense.livrable.livrableFinale;

import towerdefense.Game;
import towerdefense.Player;
import towerdefense.util.*;

/**
 * Entry point for the interactive Tower Defense game.
 * 
 * This class launches the game in interactive mode,
 * allowing the player to configure and play through
 * the command-line interface.
 */
public class towerdefenseAInteractive {
     /**
     * Main method that starts the game.
     * 
     * It:
     * - creates a new game instance with a player
     * - initializes an interactive menu system
     * - sets the game mode (0 = predefined path mode)
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
        g.setGameMode(0);
        g.setLength(Integer.parseInt(args[0]));
        g.setWidth(Integer.parseInt(args[1]));
        g.start(ilc);

    }
}