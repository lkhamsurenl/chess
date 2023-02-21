package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.King;
import Pieces.Terrorize;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TerrorizeTest {
    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, new Position(0, 3), chessBoard);
    King black_king = new King(false, new Position(2, 4), chessBoard);

    Terrorize black_terrorize = new Terrorize(false, new Position(7, 0), 0, chessBoard);
    Terrorize white_terrorize = new Terrorize(true, new Position(0, 0), 0, chessBoard);


    @Before
    public void beforeAnyTest() {
        white_king.setPosition(new Position(0,3));
        black_king.setPosition(new Position(2,4));

        black_terrorize.setPosition(new Position(7,0));
        white_terrorize.setPosition(new Position(0,0));
    }

    @Test
    public void testIsAnyObstacle() throws Exception {
        white_terrorize.setPosition(new Position(2,0));
        // now check if any obstacle
        assertEquals(white_terrorize.isAnyObstacle(new Position(2,5)), true);
        assertEquals(white_terrorize.isAnyObstacle(new Position(2, 4)), false);
        // Since counter is 0 and it can make queen move
        assertEquals(black_terrorize.isAnyObstacle(new Position(0,0)), true);
    }

    @Test
    public void testIsReachable() throws Exception {
        // cannot make knight move
        assertFalse(white_terrorize.isReachable(new Position(1,2)));
        assertTrue(white_terrorize.isReachable(new Position(0,2)));
        assertFalse(white_terrorize.isReachable(new Position(0,3)));
    }

    @Test
    public void testPossibleMovements() throws Exception {
        System.out.println(white_terrorize.getRow() + ", " + white_terrorize.getCol());
        black_terrorize.moveTo(new Position(0,7));
        // it should print out only knight movements
        ArrayList<Position> positions = black_terrorize.possibleMovements();
        for (Position position : positions) {
            System.out.println("Row: " + position.getRow() + ", Col: " + position.getCol());
        }
        System.out.println("SECOND WAVE>>>");
        black_terrorize.moveTo(new Position(2,6));
        // Now it can move to any location without eating
        ArrayList<Position> positions1 = black_terrorize.possibleMovements();
        for (Position position : positions1) {
            System.out.println("Row: " + position.getRow() + ", Col: " + position.getCol());
        }
    }
}