package Tests;

import Model.ChessBoard;
import Model.Position;
import Pieces.Bishop;
import Pieces.King;
import Pieces.Pawn;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BishopTest {

    ChessBoard chessBoard = new ChessBoard();
    King white_king = new King(true, new Position(0,3), chessBoard);
    King black_king = new King(false, new Position(2,4), chessBoard);

    Pawn white_pawn = new Pawn(true, new Position(1,4), chessBoard);
    Pawn black_pawn = new Pawn(false, new Position(1,2), chessBoard);

    Bishop white_bishop = new Bishop(true, new Position(4,2), chessBoard);
    Bishop black_bishop = new Bishop(false, new Position(2,0), chessBoard);

    /*
    * I have to set the locations to be initial after each tests
    * */
    @Before
    public void afterEachTest() {
        white_king.setPosition(new Position(0, 3));
        black_king.setPosition(new Position(2, 4));

        white_pawn.setPosition(new Position(1, 4));
        black_pawn.setPosition(new Position(1, 2));

        white_bishop.setPosition(new Position(4, 2));
        black_bishop.setPosition(new Position(2, 0));
    }

    // test if the piece can reach to the destination
    @Test
    public void testIsReachable() throws Exception {
        //It can reach there since can eat white Pieces.Bishop
        assertEquals(black_bishop.isReachable(new Position(4, 2)), true);
        assertEquals(black_bishop.isReachable(new Position(0, 2)), true);
        // Trying to go out of boundary
        assertEquals(black_bishop.isReachable(new Position(-1, 3)), false);
        // Go over the opponent's piece
        assertEquals(white_bishop.isReachable(new Position(1, 5)), false);

    }

    /*
    *
    * Determine if given movement is valid by no obstacles, and wouldn't give up the king
    *
    * */
    @Test
    public void testIsValidMovement() throws Exception {
        white_king.setPosition(new Position(7, 5));
        white_bishop.setPosition(new Position(6, 4));
        //now if we move bishop to (5,5), then king is in danger
        assertEquals(white_bishop.isValidMovement(new Position(5, 5)), false);
        // but can move it to the 5,3
        assertEquals(white_bishop.isValidMovement(new Position(5, 3)), true);
    }

    /*
     * Check different set of movements across the board
    * */
    @Test
    public void testMoveTo() throws Exception {
        // otherwise, it is threatening the king
        black_pawn.setPosition(new Position(2, 2));
        white_bishop.moveTo(new Position(2, 4));
        Assert.assertEquals(white_bishop, chessBoard.ChessBoard[2][4]);
    }

    @Test
    public void testCanDirectlyEatKing() throws Exception {
        assertEquals(white_bishop.canDirectlyEatKing(), true);
        // now there is an obstacle
        black_pawn.setPosition(new Position(3, 3));
        assertEquals(white_bishop.canDirectlyEatKing(), false);
    }

    @Test
    public void testPossibleMovements() {
        black_pawn.setPosition(new Position(7, 7));
        black_king.setPosition(new Position(2, 4));
        ArrayList<Position> positions = white_bishop.possibleMovements();
        //System.out.println(black_king.getRow() + ", " + black_king.getCol());

        for (Position position : positions) {
            System.out.println("Row: " + position.getRow() + " , Col: " + position.getCol());
        }
    }


}