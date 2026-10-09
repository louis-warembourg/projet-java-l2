package towerdefense.livrable.livrable5;

import towerdefense.Board;
import towerdefense.Game;
import towerdefense.Init;
import towerdefense.Player;
import towerdefense.util.Ask;
import towerdefense.util.InteractiveListChooser;

import java.util.*;

/**
 * Entry point for the final version of the Tower Defense game.
 * 
 * This version:
 * - runs the game in interactive mode by default
 * - uses the default game mode (set inside Game if not specified)
 */
public class Livrable5 {
    
    /**
     * Main method that starts the final version of the game.
     * 
     * It:
     * - creates a new game instance
     * - uses an interactive list chooser
     * - launches the game loop
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args){
        Game g = new Game(new Player());
        InteractiveListChooser<String> ilc = new InteractiveListChooser<>();
        g.start(ilc);

    }
}
