package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.King;
import Pieces.Rook;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RookTest {

    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, new Position(0, 3), chessBoard);
    King black_king = new King(false, new Position(2, 4), chessBoard);

    Rook white_rook = new Rook(true, new Position(0, 0), chessBoard);
    Rook black_rook = new Rook(false, new Position(7, 0), chessBoard);

    /*
    * I have to set the locations to be initial after each tests
    * */
    @Before
    public void afterEachTest() {
        white_king.setPosition(new Position(0, 3));
        black_king.setPosition(new Position(2, 4));

        white_rook.setPosition(new Position(0, 0));
        black_rook.setPosition(new Position(7, 0));

    }

    //No Obstacle in teh way and valid boundary
    @Test
    public void testIsReachable() throws Exception {
        assertEquals(white_rook.isReachable(new Position(0, 2)), true);
        // cannot step over the alias
        assertEquals(white_rook.isReachable(new Position(0, 3)), false);
        // can take opponent
        assertEquals(white_rook.isReachable(new Position(7, 0)), true);
    }

    /*
    * Reachable and not giving up the king
    * */
    @Test
    public void testIsValidMovement() throws Exception {
        black_rook.setPosition(new Position(2, 2));
        white_rook.setPosition(new Position(2, 0));
        assertEquals(black_rook.isValidMovement(new Position(3, 2)), false);
        // but can move while protecting the king
        assertEquals(black_rook.isValidMovement(new Position(2, 3)), true);
    }

    @Test
    public void testMoveTo() throws Exception {
        white_rook.moveTo(new Position(2, 0));
        assertNotNull(chessBoard.ChessBoard[2][0]);
        assertNull(chessBoard.ChessBoard[0][0]);
    }

    /*
    * Enumerate all possible valid movements
    * */
    @Test
    public void testPossibleMovements() {
        ArrayList<Position> positions = white_rook.possibleMovements();
        for (Position position : positions) {
            System.out.println("Row: " + position.getRow() + ", Col: " + position.getCol());
        }
    }
}