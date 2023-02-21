package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.King;
import Pieces.Pawn;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class KingTest extends TestCase {

    ChessBoard chessBoard = new ChessBoard();

    King white_king = new King(true, new Position(0, 3), chessBoard);
    King black_king = new King(false, new Position(2, 4), chessBoard);

    Pawn white_pawn = new Pawn(true, new Position(1, 4), chessBoard);
    Pawn black_pawn = new Pawn(false, new Position(1, 2), chessBoard);

    /*
    * I have to set the locations to be initial before each tests
    * */
    @Before
    public void beforeEachTest() {
        white_king.setPosition(new Position(0, 3));
        black_king.setPosition(new Position(2, 4));

        white_pawn.setPosition(new Position(1, 4));
        black_pawn.setPosition(new Position(1, 2));

    }

    // Determine if given movement is valid by no obstacles, and wouldn't give up the king.
    @Test
    public void testIsValidMovement() throws Exception {

        assertEquals(white_king.isValidMovement(new Position(1, 2)), true);
        assertEquals(white_king.isValidMovement(new Position(2, 2)), false);

    }

    // Given position is reachable by valid boundary and no obstacles.
    @Test
    public void testIsReachable() throws Exception {
        assertEquals(white_king.isReachable(new Position(1, 2)), true);
        //since there is pawn
        assertEquals(white_king.isReachable(new Position(1, 4)), false);
        assertEquals(white_king.isReachable(new Position(0, 1)), false);
    }

    @Test
    public void testIsInCheckMate() throws Exception {
        assertEquals(white_king.isInCheck(), true);
        black_pawn.setPosition(new Position(1, 3));
        assertEquals(white_king.isInCheck(), false);
    }

    /*
    * Check different set of movements across the board
    * */
    @Test
    public void testMoveTo() throws Exception {
        white_king.moveTo(new Position(0, 2));
        TestCase.assertNotNull(ChessBoard.ChessBoard[0][2]);
    }

    /*
    * tests if the piece can directly eat the Pieces.King
    * */
    @Test
    public void testCanDirectlyEatKing() throws Exception {
        white_king.setPosition(new Position(1, 3));
        assertEquals(white_king.canDirectlyEatKing(), true);
        assertEquals(black_king.canDirectlyEatKing(), true);
    }

    /*
    * Enumerate all possible valid movements
    * */
    @Test
    public void testPossibleMovements() {
        ArrayList<Position> positions = white_king.possibleMovements();
        for (Position position : positions) {
            System.out.println("Row: " + position.getRow() + ", Col: " + position.getCol());
            System.out.println();
        }
    }
}