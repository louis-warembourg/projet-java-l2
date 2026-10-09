package towerdefense.util;

import java.util.*;

/**
 * a class for choosing start and end 
 */
public class StartEndChooser {
    
    /**
     * verifies if a position is on the border of a grid
     * @param r the row index of the position
     * @param c the column index of the position
     * @param tab the grid to be verified
     * @return <code>true</code> if the position is on the border; <code>false</code> otherwise. 
     */
    public static boolean isValidBorder(int r, int c, Object[][] tab) {
        return r == 0 || r == tab.length-1 || c == 0 || c == tab[0].length - 1; 
    }
    
    /**
     * verifies if two positions are in the same border of a grid
     * @param p1 the coordinates of the first position
     * @param p2 the coordinates of the second position
     * @param tab the grid to be verified
     * @return <code>true</code> if the two positions are in the same border; <code>false</code> otherwise.
     */
    public static boolean sameBorder(int[] p1, int[] p2, Object[][] tab) {
        return (p1[0] == 0 && p2[0] == 0) //both are on the top border
               || (p1[0] == tab.length - 1 && p2[0] == tab.length - 1) //both are on the bottom border  
               || (p1[1] == 0 && p2[1] == 0) //both are on the left corner
               || (p1[1] == tab[0].length - 1 && p2[1] == tab[0].length - 1); //both are on the right corner
    }
    
    /**
     * chooses coordinates randomly to create a position
     * @param tab the grid from which the coordinates are generated
     * @return the array containing the generated coordinates
     */
    public static int[] generateCoordinates(Object[][] tab) {
        Random rand = new Random();
        int r, c;
        do {
            r = rand.nextInt(tab.length);     //choose a row index randomly
            c = rand.nextInt(tab[0].length);  //choose a column index randomly
        } 
        while (!isValidBorder(r, c, tab));  //re-choose if the coordinates are not on a valid border
        return new int[]{r, c};
    }

    public static int[] generateStartleft(Object[][] tab) {
        Random rand = new Random();
        int r;
        do {
            r = rand.nextInt(tab.length);  //choose a column index randomly
        } 
        while (!isValidBorder(r, 0, tab));  //re-choose if the coordinates are not on a valid border
        return new int[]{r, 0};
    }

    /**
     * choose a starting and an ending position on the grid
     * @param tab the grid on which the positions are chosen
     * @return the array containing the starting and ending positions
     */
    public static int[][] generateStartEnd(Object[][] tab) {
        int[] start = generateStartleft(tab); //generate a starting position
        int[] end;

        do {
            end = generateCoordinates(tab);  //generate an ending position
        } 
        while (sameBorder(start, end, tab)); //re-generate end if it is on same border as start

        int[] startInv = { start[1], start[0] };
        int[] endInv   = { end[1], end[0] };
        return new int[][]{ startInv, endInv };
    }

    /**
     * access to the row index of the starting position of the grid
     * @param tab the array containing the starting and ending position
     * @return the row index of the starting position
     */
    public static int getStartRow(int[][] tab){
        int[] start = tab[0]; //the starting position
        return start[1];  //the row index of the starting position  
    }

    /**
     * access to the column index of the starting position of the grid
     * @param tab the array containing the starting and ending position
     * @return the column index of the starting position
     */
    public static int getStartCol(int[][] tab){
        int[] start = tab[0]; //the starting position
        return start[0]; //the column index of the starting position 
    }

    /**
     * access to the row index of the ending position of the grid
     * @param tab the array containing the starting and ending position
     * @return the row index of the rnding position
     */
    public static int getEndRow(int[][] tab){
        int[] end = tab[1]; //the ending position
        return end[1]; //the row index of the ending position  
    }

    /**
     * access to the column index of the ending position of the grid
     * @param tab the array containing the starting and ending position
     * @return the column index of the ending position
     */
    public static int getEndCol(int[][] tab){
        int[] end = tab[1]; //the ending position
        return end[0]; //the column index of the ending position 
    }

    /**
     * determines the direction of that the balloon will take, based on the border in which it is located,
     * used for classic boards
     * @param x the x-coordinate of the balloon int he board
     * @param y the y-coordinate of the balloon int he board
     * @param tab the array containing the starting and ending position
     * @return the distance from the balloon to the end of the path
     */
    public static Direction getStartingBorderDirection(int x, int y, Object[][] tab) {
        if (y == 0) {
            return Direction.bas; //top border
        } else if (y == tab.length - 1) {
            return Direction.haut; //bottom border
        } else if (x == 0) {
            return Direction.droite; //left border
        } else {
            return Direction.gauche; //right border
        }
    }
}
