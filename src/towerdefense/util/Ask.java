package towerdefense.util;

import java.util.*;
import java.util.concurrent.Callable;

import towerdefense.Tower;
import towerdefense.util.Input;

/**a class for managing user's prompts */
public class Ask {

    /**the scanning instance of the standard input */
    private Scanner scan = new Scanner(System.in);
    
    
    /**
     * check if a string is a number or not
     * @param input the string
     * @return true if the string is a number
     */
    public static boolean isNumber(String input){
        if (input == ""){
            return false;
        }
        for(int i = 0; i<input.length(); i++){
            if(!Character.isDigit(input.charAt(i))){
                return false;
            }
        }
        return true;
    }

    /**
     * ask to the player a valide gameMod
     * @return the game mode between 0 for random mod and 1 for the classic mode
     */
    public int askGameMode(){
        System.out.println("Enter the Game Mod : 0 for Random Mod, else 1");
        String mod = scan.nextLine();
        while(!this.isNumber(mod) ||( Integer.parseInt(mod) != 1 && Integer.parseInt(mod) != 0)){
            System.out.println("Incorrect game mode ! try again.");
            mod = scan.nextLine();
        }
        return Integer.parseInt(mod);
    }

    /**
     * allows to make a choice for the game mode
     * @return the choice made for the game mode
     */
    public int askGameModeInteractive(){
        InteractiveListChooser<Integer> lc = new InteractiveListChooser<>();
        List<Integer> gameModes = List.of(0,1);
        int choice = lc.choose("Enter the Game Mod : 0 for Random Mod, else 1", gameModes);
        while(!(gameModes.contains(choice))){
            System.out.println("Incorrect Input ! try again.");
            choice = lc.choose("Enter the GameMode : 0 for RandomMode, 1 for ClassicMode", gameModes);
        }
        return choice;
    }

    /**
     * allows to choose between classic and random mode
     * @return the choice made for the game mode
     */
    public int askGameModeRandom(){
        RandomListChooser<Integer> lc = new RandomListChooser<>();
        List<Integer> gameModes = List.of(0,1);
        int choice = lc.choose("Enter the GameMode : 0 for RandomMode, 1 for ClassicMode", gameModes);
        return choice;
    }

    /**
     * allows to enter an integer within a given range
     * @param msg the message preceeding the prompt
     * @param min the minimum of the range
     * @param max the maximum of the range
     * @return the entered integer
     */
    public int askInteger(String msg, int min, int max){
        System.out.println(msg);
        System.out.println("Enter an integer between " + min + " and " + max);
        String mod = scan.nextLine();
        while(!this.isNumber(mod) || Integer.parseInt(mod) < min || Integer.parseInt(mod) > max){
            System.out.println("Incorrect input ! try again.");
            mod = scan.nextLine();
        }
        return Integer.parseInt(mod);
    }

    /**
     * ask to the player a valid length for the board
     * 
     * @return the length of the board
     */
    public int askLengthBoard(){
        System.out.println("Enter the length of the Board : Greater than 4");
        String length = scan.nextLine();
        while(!this.isNumber(length) || ( Integer.parseInt(length)<= 4)){
            System.out.println("Incorrect length ! try again.");
            length = scan.nextLine();
        }
        return Integer.parseInt(length);
    }

    /**
     * ask to the player a valid width for the board
     * 
     * @return the width of the board
     */
    public int askWidthBoard(){
        System.out.println("Enter the width of the Board : Greater than 4");
        String width = scan.nextLine();
        while(!this.isNumber(width) || (Integer.parseInt(width)<= 4)){
            System.out.println("Incorrect width ! try again.");
            width = scan.nextLine();
        }
        return Integer.parseInt(width);
    }

    /**
     * ask to the player a valid number of balloons
     * 
     * @return the number of balloons
     */
    public int askNumberOfBalloons(){
        System.out.println("Enter the number of balloons : Greater than 0");
        String nbBalloons = scan.nextLine();
        while(!this.isNumber(nbBalloons) || ( Integer.parseInt(nbBalloons)<= 0)){
            System.out.println("Incorrect number of balloons ! try again.");
            nbBalloons = scan.nextLine();
        }
        return Integer.parseInt(nbBalloons);
    }
}