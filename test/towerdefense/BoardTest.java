package towerdefense;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import towerdefense.cell.*;
import towerdefense.util.Direction;
import towerdefense.tower.*;
import java.util.*;

public class BoardTest{

    @Test
    public void testInitBoard() {
        Cell[][] board = Board.initBoard(5, 5);
        assertNotNull(board, "The board must not be empty.");
        assertEquals(5, board.length, "The board must have 5 rows.");
        assertEquals(5, board[0].length, "The board must have 5 columns.");
        assertTrue(board[0][0] instanceof TowerCell, "The cell must be a TowerCell.");
    }

    @Test
    public void testCreateBoardClassic() {
        Board classicBoard = Board.createBoardClassic(5, 5);
        assertNotNull(classicBoard, "The classicBoard must not be null.");
        Cell cell = classicBoard.getCell(0, 0);
        assertNotNull(cell, "The cell at (0,0) must not be null.");
        assertEquals(0, cell.getX(), "The X coordinate must be correct.");
        assertEquals(0, cell.getY(), "The Y coordinate must be correct.");
        assertTrue(cell instanceof MixedCell, "The cell must be a MixedCell.");
    }

    @Test
    public void testPathRandom() {
        Cell[][] board = Board.initBoard(3, 3);
        Board b = new Board(board, new ArrayList<>());
        int[] start = {0, 0};
        int[] end = {2, 2};
        ArrayList<Cell> path = b.pathRandom(start, end);
        assertNotNull(path, "The path must not be null.");
    
        if (!path.isEmpty()) {
            Cell firstCell = path.get(0);
            assertEquals(0, firstCell.getX(), "The path must start at (0,0) on the X-axis.");
            assertEquals(0, firstCell.getY(), "The path must start at (0,0) on the Y-axis.");
        }
    }

    @Test
    public void testNextCell() {
        Cell[][] board = Board.initBoard(5, 5);
        Board b = new Board(board, new ArrayList<>());
        
        Cell cell = b.getCell(2, 2);
        assertNotNull(cell, "The cell must not be empty.");

        Cell nextUp = b.nextCell(cell, Direction.haut);
        assertNotNull(nextUp, "The cell immediately above must not be null.");
        assertEquals(2, nextUp.getX(), "The cell directly above it must have the correct X-coordinate.");
        assertEquals(1, nextUp.getY(), "The cell directly above it must have the correct Y-coordinate.");

        Cell nextDown = b.nextCell(cell, Direction.bas);
        assertNotNull(nextDown, "The cell immediately below must not be null.");
        assertEquals(2, nextDown.getX(), "The cell directly below it must have the correct X-coordinate.");
        assertEquals(3, nextDown.getY(), "The cell directly below it must have the correct Y-coordinate.");

        Cell nextRight = b.nextCell(cell, Direction.droite);
        assertNotNull(nextRight, "The cell to the right must not be null.");
        assertEquals(3, nextRight.getX(), "The cell to the right of this one must have the correct X-coordinate.");
        assertEquals(2, nextRight.getY(), "The cell to the right of this one must have the correct Y-coordinate.");

        Cell nextLeft = b.nextCell(cell, Direction.gauche);
        assertNotNull(nextLeft, "The cell to the left of this one must not be null.");
        assertEquals(1, nextLeft.getX(), "The next cell to the left must have the correct X-coordinate.");
        assertEquals(2, nextLeft.getY(), "The next cell to the left must have the correct Y-coordinate.");
        
        Cell edgeCell = b.getCell(0, 0);
        Cell beyondLeft = b.nextCell(edgeCell, Direction.gauche);
        assertNull(beyondLeft, "Should return null when the left boundary is exceeded");
    }

    @Test
    public void testCreateBoardRandom() {
        Board randomBoard = Board.createBoardRandom(5, 5);
        assertNotNull(randomBoard, "The random board must not be null.");
        assertNotNull(randomBoard.getPath(), "The path to the random board must not be null.");
        assertFalse(randomBoard.getPath().isEmpty(), "The path to the random board must not be empty.");
    }

    @Test
    public void testGetEndClassicPath() {
        Cell[][] board = Board.initBoard(5, 5);
        Board b = new Board(board, new ArrayList<>());
    
        Balloon balloonHaut = new Balloon(1, 1, 2, 2, Direction.haut);
        Cell endCellHaut = b.getEndClassicPath(balloonHaut);
        assertNotNull(endCellHaut, "The end cell for the up direction must not be null");
        assertEquals(2, endCellHaut.getX(), "For upward direction, X must remain the same");
        assertEquals(0, endCellHaut.getY(), "For upward direction, Y must be 0 (first line)");
    
        Balloon balloonBas = new Balloon(1, 1, 2, 2, Direction.bas);
        Cell endCellBas = b.getEndClassicPath(balloonBas);
        assertNotNull(endCellBas, "The end cell for the down direction must not be null");
        assertEquals(2, endCellBas.getX(), "For downward direction, X must remain the same");
        assertEquals(4, endCellBas.getY(), "For the down direction, Y must be 4 (the bottom row on a 5x5 grid)");
    
        Balloon balloonDroite = new Balloon(1, 1, 2, 2, Direction.droite);
        Cell endCellDroite = b.getEndClassicPath(balloonDroite);
        assertNotNull(endCellDroite, "The end cell for the right direction must not be null");
        assertEquals(4, endCellDroite.getX(), "For a right turn, X must be 4 (the last column on a 5x5 grid)");
        assertEquals(2, endCellDroite.getY(), "For right-hand drive, Y must remain the same");
    
        Balloon balloonGauche = new Balloon(1, 1, 2, 2, Direction.gauche);
        Cell endCellGauche = b.getEndClassicPath(balloonGauche);
        assertNotNull(endCellGauche, "The end cell for the left direction must not be null");
        assertEquals(0, endCellGauche.getX(), "For left-to-right direction, X must be 0 (first column)");
        assertEquals(2, endCellGauche.getY(), "For left-hand drive, Y must remain the same");
    }

    @Test
    public void testGetEndClassicPathEdgeCases() {
        Cell[][] board = Board.initBoard(3, 3);
        Board b = new Board(board, new ArrayList<>());
    
        Balloon balloonHautBord = new Balloon(1, 1, 1, 0, Direction.haut);
        Cell endCellHautBord = b.getEndClassicPath(balloonHautBord);
        assertNotNull(endCellHautBord, "The end cell must not be null, even on the edge");
        assertEquals(1, endCellHautBord.getX(), "X must remain the same");
        assertEquals(0, endCellHautBord.getY(), "Y must be 0 (already at the edge)");
    
        Balloon balloonBasBord = new Balloon(1, 1, 1, 2, Direction.bas);
        Cell endCellBasBord = b.getEndClassicPath(balloonBasBord);
        assertNotNull(endCellBasBord, "The end cell must not be null, even on the edge");
        assertEquals(1, endCellBasBord.getX(), "X must remain the same");
        assertEquals(2, endCellBasBord.getY(), "It must be 2 (already on the edge)");
    
        Balloon balloonDroiteBord = new Balloon(1, 1, 2, 1, Direction.droite);
        Cell endCellDroiteBord = b.getEndClassicPath(balloonDroiteBord);
        assertNotNull(endCellDroiteBord, "The end cell must not be null, even on the edge");
        assertEquals(2, endCellDroiteBord.getX(), "X must be 2 (already on the edge)");
        assertEquals(1, endCellDroiteBord.getY(), "Y must remain the same");
    
        Balloon balloonGaucheBord = new Balloon(1, 1, 0, 1, Direction.gauche);
        Cell endCellGaucheBord = b.getEndClassicPath(balloonGaucheBord);
        assertNotNull(endCellGaucheBord, "The end cell must not be null, even on the edge");
        assertEquals(0, endCellGaucheBord.getX(), "X must be 0 (already at the edge)");
        assertEquals(1, endCellGaucheBord.getY(), "Y must remain the same");
    }

    @Test
    public void testGetEndClassicPathCornerCases() {
        Cell[][] board = Board.initBoard(4, 4);
        Board b = new Board(board, new ArrayList<>());
    
        Balloon balloonCoinHG = new Balloon(1, 1, 0, 0, Direction.haut);
        Cell endCellHG = b.getEndClassicPath(balloonCoinHG);
        assertNotNull(endCellHG, "The last cell must not be null, even at the corner");
        assertEquals(0, endCellHG.getX(), "X must remain 0");
        assertEquals(0, endCellHG.getY(), "Y must remain 0");
    
        Balloon balloonCoinBD = new Balloon(1, 1, 3, 3, Direction.bas);
        Cell endCellBD = b.getEndClassicPath(balloonCoinBD);
        assertNotNull(endCellBD, "The end cell must not be null, even at the corner");
        assertEquals(3, endCellBD.getX(), "X must remain 3");
        assertEquals(3, endCellBD.getY(), "There must be 3 left");
    }

    @Test
    public void testChangeCell(){
        Board b = Board.createBoardClassic(5, 5);
        b.changeCell(1, 2, 4);
        assertTrue(b.getCell(1, 2) instanceof BalloonCell);
    }
}