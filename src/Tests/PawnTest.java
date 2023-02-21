package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.King;
import Pieces.Pawn;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PawnTest {

    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, new Position(0, 3), chessBoard);
    King black_king = new King(false, new Position(2, 4), chessBoard);

    Pawn white_pawn = new Pawn(true, new Position(1, 3), chessBoard);
    Pawn black_pawn = new Pawn(false, new Position(6, 4), chessBoard);


    /*
    * I have to set the locations to be initial after each tests
    * */
    @Before
    public void beforeEachTest() {
        white_king.setPosition(new Position(0, 3));
        black_king.setPosition(new Position(2, 4));

        white_pawn.setPosition(new Position(1, 3));
        black_pawn.setPosition(new Position(6, 4));
    }

    // test if pawn is making its init 2 jumps
    @Test
    public void testIsJumpTwo() throws Exception {
        assertEquals(white_pawn.isJumpTwo(new Position(3, 3)), true);
        assertEquals(white_pawn.isJumpTwo(new Position(2, 3)), false);
    }


    /*
    * eating diagonally
    * */
    @Test
    public void testIsTryingEatingOther() throws Exception {
        assertEquals(white_pawn.isTryingEatingOther(new Position(2, 4)), true);
        //But there is none in the 2,2 side
        assertEquals(white_pawn.isTryingEatingOther(new Position(2, 2)), false);
        // cannot go back to eat
        black_pawn.setPosition(new Position(0, 4));
        assertEquals(white_pawn.isTryingEatingOther(new Position(0, 4)), false);
    }

    @Test
    public void testIsReachable() throws Exception {
        assertEquals(white_pawn.isReachable(new Position(2, 3)), true);
        //should be able to eat the king
        assertEquals(white_pawn.isReachable(new Position(2, 4)), true);
    }

    @Test
    public void testIsValidMovement() throws Exception {
        assertEquals(white_pawn.isValidMovement(new Position(3, 2)), false);
        assertEquals(white_pawn.isValidMovement(new Position(1, 2)), false);
        white_pawn.setPosition(new Position(2, 3));
        assertEquals(black_pawn.isValidMovement(new Position(5, 4)), true);
    }

    @Test
    public void testMoveTo() throws Exception {
        white_pawn.moveTo(new Position(2, 4));
        assertNotNull(chessBoard.ChessBoard[2][4]);
        assertNull(chessBoard.ChessBoard[1][3]);
    }

    /*
    * Enumerate all possible valid movements
    * */
    @Test
    public void testPossibleMovements() {
        white_pawn.setPosition(new Position(1, 2));
        ArrayList<Position> positions = black_pawn.possibleMovements();
        for (Position position : positions) {
            System.out.println("Row: " + position.getRow() + ", Col: " + position.getCol());
        }
    }
}