package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.King;
import Pieces.Pawn;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

public class ChessPieceTest extends TestCase {

    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, new Position(0,3), chessBoard);
    King black_king = new King(false, new Position(2,4), chessBoard);

    Pawn white_pawn = new Pawn(true, new Position(1,4), chessBoard);
    Pawn black_pawn = new Pawn(false, new Position(1,3), chessBoard);

    /*
    * I have to set the locations to be initial after each tests
    * */
    @Before
    public void afterEachTest() {
        white_king.setPosition(new Position(0,3));
        black_king.setPosition(new Position(2, 4));

        white_pawn.setPosition(new Position(1,4));
        black_pawn.setPosition(new Position(1,3));
    }

    /*
    * check the boundaries. It only needed to be checked once.
    * */
    @Test
    public void testIsValidLocation() throws Exception {
        assertEquals(white_king.isValidLocation(new Position(1,3)), true);
        assertEquals(white_king.isValidLocation(new Position(-1,4)), false);
        assertEquals(white_king.isValidLocation(new Position(8,3)), false);
    }

    @Test
    public void testIsInSameCol() throws Exception {
        assertEquals(white_king.isInSameCol(3), true);
        assertEquals(white_king.isInSameCol(2), false);
    }

    @Test
    public void testIsInSameRow() throws Exception {
        assertEquals(white_king.isInSameRow(0), true);
        assertEquals(white_king.isInSameRow(4), false);
    }

    @Test
    public void testIsInSameDiagonal() throws Exception {
        assertEquals(white_king.isInSameDiagonal(new Position(3,6)), true);
        assertEquals(white_king.isInSameDiagonal(new Position(3,4)), false);
    }

    @Test
    public void testIsAnyObstacleHorizontally() throws Exception {
        black_pawn.setPosition(new Position(0,4));
        // there is a black pawn in location, so remove it
        assertEquals(white_king.isAnyObstacleHorizontally(new Position(0,4)), false);
        // cannot step over black pawn
        assertEquals(white_king.isAnyObstacleHorizontally(new Position(0,5)), true);
        white_pawn.setPosition(new Position(0,4));
        //cannot eat white pawn
        assertEquals(white_king.isAnyObstacleHorizontally(new Position(0,4)), true);
        assertEquals(white_king.isAnyObstacleHorizontally(new Position(0,2)), false);
    }

    @Test
    public void testIsAnyObstacleVertically() throws Exception {
        black_pawn.setPosition(new Position(1,3));
        // there is a black pawn in location, so remove it
        assertEquals(white_king.isAnyObstacleVertically(new Position(1,3)), false);
        // cannot step over black pawn
        assertEquals(white_king.isAnyObstacleVertically(new Position(2,3)), true);
        white_pawn.setPosition(new Position(1,3));
        //cannot eat white pawn
        assertEquals(white_king.isAnyObstacleVertically(new Position(1,3)), true);
    }

    @Test
    public void testIsAnyObstacleDiagonally() throws Exception {
        black_pawn.setPosition(new Position(1,2));
        assertEquals(white_king.isAnyObstacleDiagonally(new Position(1,2)), false);
        assertEquals(white_king.isAnyObstacleDiagonally(new Position(2,1)), true);
    }
}