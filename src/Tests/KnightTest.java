package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.Bishop;
import Pieces.King;
import Pieces.Knight;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertNotNull;

public class KnightTest extends TestCase {

    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, new Position(0, 3), chessBoard);
    King black_king = new King(false, new Position(2, 4), chessBoard);

    Knight white_knight = new Knight(true, new Position(3, 2), chessBoard);
    Knight black_knight = new Knight(false, new Position(6, 4), chessBoard);
    Bishop black_bishop = new Bishop(false, new Position(2, 5), chessBoard);

    /*
    * I have to set the locations to be initial after each tests
    * */
    @Before
    public void afterEachTest() {
        white_king.setPosition(new Position(0, 3));
        black_king.setPosition(new Position(2, 4));

        white_knight.setPosition(new Position(3, 2));
        black_knight.setPosition(new Position(6, 4));
    }

    /*
    * Tests if exactly knight move
    * */
    @Test
    public void testIsKnightMove() throws Exception {
        assertEquals(white_knight.isLegalKnightMove(new Position(2, 4)), true);
        assertEquals(white_knight.isLegalKnightMove(new Position(1, 1)), true);
        assertEquals(white_knight.isLegalKnightMove(new Position(4, 3)), false);

    }

    /*
    * no obstacles to get to the destination
    * */
    @Test
    public void testIsAnyObstacle() throws Exception {
        assertEquals(white_knight.isAnyObstacle(new Position(2, 4)), false);
        assertEquals(white_knight.isAnyObstacle(new Position(1, 1)), false);
        // move the king to one of the locations
        white_king.setPosition(new Position(1, 3));
        assertEquals(white_knight.isAnyObstacle(new Position(1, 3)), true);

    }

    /*
    * no obstacle and within the boundary
    * */
    @Test
    public void testIsReachable() throws Exception {
        assertEquals(white_knight.isReachable(new Position(2, 0)), true);
        assertEquals(white_knight.isReachable(new Position(-1, 0)), false);

    }

    /*
    * reachable and not giving up the king
    * */
    @Test
    public void testIsValidMovement() throws Exception {

        white_king.setPosition(new Position(0, 3));
        white_knight.setPosition(new Position(1, 4));
        //white_knight.moveTo(1, 4);
        // king is in danger
        TestCase.assertNotNull(chessBoard.ChessBoard[1][4]);
        //assertEquals(white_knight.isValidMovement(2, 2), false);
        black_bishop.setPosition(new Position(2, 3));
        System.out.println(black_bishop.getRow() + ", " + black_bishop.getCol());
        TestCase.assertTrue(white_knight.isValidMovement(new Position(3, 3)));
    }

    /*
    * Enumerate all possible valid movements
    * */
    @Test
    public void testPossibleMovements() {
        black_bishop.setPosition(new Position(7, 7));
        ArrayList<Position> positions = white_knight.possibleMovements();
        for (Position position : positions) {
            System.out.println("Row: " + position.getRow() + ", Col: " + position.getCol());
        }
    }
}