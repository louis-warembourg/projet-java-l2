package towerdefense;
import towerdefense.cell.MixedCell;
import towerdefense.util.Direction;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class ManageBalloonTest{
    private Board board;
    private Balloon balloon1;
    private Balloon balloon2;
    private ManageBalloon mb1;
    private ManageBalloon mb2;
    private Cell cell1;
    private Cell cell2;

    @BeforeEach
    public void before(){
        this.board = Board.createBoardClassic(10, 10);
        this.balloon1 = new Balloon(30, 1, 0, 1, Direction.droite);
        this.balloon2 = new Balloon(30, 1, 0, 9, Direction.droite);
        this.cell1 = new MixedCell(0, 9, null);
        this.cell2 = new MixedCell(0, 1, null);
        this.mb1 = new ManageBalloon(this.balloon1, this.board);
        this.mb2 = new ManageBalloon(this.balloon2, this.board);
    }

    @Test
    public void testIsClassicalBalloonAtEndReturnsFalse(){
        assertFalse(mb1.isClassicBalloonAtEnd());
    }

    @Test
    public void testGetBalloon(){
        assertEquals(this.balloon1, mb1.getBalloon());
        assertEquals(this.balloon2, mb2.getBalloon());
    }

    @Test
    public void testGetBoard(){
        assertEquals(this.board, mb1.getBoard());
        assertEquals(this.board, mb2.getBoard());
    }

    @Test
    public void testMoveBalloon(){
        //TODO
    }
}